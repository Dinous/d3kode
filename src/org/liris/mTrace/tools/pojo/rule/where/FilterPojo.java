/**.
 * 
 */
package org.liris.mTrace.tools.pojo.rule.where;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.liris.mTrace.kTBS.SingleKtbs;
import org.liris.mTrace.tools.EnumOperator;
import org.liris.mTrace.tools.pojo.rule.util.OperandePojo;
import org.liris.mTrace.tools.pojo.rule.util.OperatorOrOperandeInterface;
import org.liris.mTrace.tools.pojo.rule.util.OperatorPojo;

/**.
 * @author Dino
 * @10 mai 2011
 */
public class FilterPojo implements Serializable{
    /**.
     * 
     */
    private static final long serialVersionUID = 8070525940289937372L;
    
    
    private List<OperatorOrOperandeInterface> operatorOrOperande;
    
    
    /**.
     * 
     */
    @Deprecated
    public FilterPojo() {}
    
    public String toKtbsString(){
	StringBuffer filter = new StringBuffer();
	Map<String, String> mapOperator = new HashMap<String, String>();
	
	for(int j=0;j<getOperatorOrOperande().size();j++){
	    OperatorOrOperandeInterface operatorOrOperande = getOperatorOrOperande().get(j);
	    if(operatorOrOperande instanceof OperatorPojo ){
		if( !EnumOperator.isSpecial(operatorOrOperande.toString()) &&
			!(getOperatorOrOperande().get(j-1) instanceof OperatorPojo)&& 
			j < getOperatorOrOperande().size()-1){
		OperandePojo firstOperande = (OperandePojo)getOperatorOrOperande().get(j-1);
		OperandePojo secondOperande = (OperandePojo)getOperatorOrOperande().get(j+1);
		OperatorPojo operator = (OperatorPojo)operatorOrOperande;
		if(operator.getValueStr().equals("=")){
		    String firstRange = null;
		    
		    if(firstOperande.getAttribute() != null){
			if(firstOperande.getAttribute().getRange() != null){
			    firstRange = firstOperande.getAttribute().getRange();   
			}else{
			    if(!firstOperande.getAttribute().getUri().contains("rdf-schema")
				    && !firstOperande.getAttribute().getUri().contains("ktbs#"))
			    firstRange = SingleKtbs.getInstance().getRangeOfAttributeType(firstOperande.getAttribute().getUri());
			}
		    }
		    
		    
		    if(firstRange != null && firstRange.toLowerCase().equals("http://www.w3.org/2001/xmlschema#string")){
			mapOperator.put(firstOperande.toStringSparql()+ " " + operatorOrOperande.toStringSparql()+ " " + secondOperande.toStringSparql(),
				"regex("+firstOperande.toStringSparql()+","+secondOperande.toStringSparql()+")");
		    }
		}
		if(j > 2 && j+1 <= getOperatorOrOperande().size()){
		    filter.append("&&");    
		}
		filter.append(" (");
		filter.append(firstOperande.toStringSparql()).append(" ");
		filter.append(operator.toStringSparql()).append(" ");
		filter.append(secondOperande.toStringSparql()).append(" ");
		filter.append(") ");
		j++;
		}else{
		    filter.append(operatorOrOperande.toString());    
		}
	    }else{
		if(operatorOrOperande instanceof OperatorPojo){
		    filter.append(operatorOrOperande.toStringSparql());
		}else{
		    if(j == getOperatorOrOperande().size() -1)
			filter.append(operatorOrOperande.toStringSparql());
		}
//		    else{
//		    if(operatorOrOperande instanceof OperandePojo && j!=0){
//			filter.append(operatorOrOperande.toStringSparql());
//		    }
//		}
	    }
	    
	    //filter.append(operatorOrOperande.toString());
	    
	}
	String ret = StringUtils.replaceEach(filter.toString(), mapOperator.keySet().toArray(new String[0]), mapOperator.values().toArray(new String[0]));
	return ret;
    }

    public void setOperatorOrOperande(List<OperatorOrOperandeInterface> operatorOrOperande) {
	this.operatorOrOperande = operatorOrOperande;
    }

    public List<OperatorOrOperandeInterface> getOperatorOrOperande() {
	return operatorOrOperande;
    }
}
