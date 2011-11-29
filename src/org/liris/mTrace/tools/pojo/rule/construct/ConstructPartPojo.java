/**.
 * 
 */
package org.liris.mTrace.tools.pojo.rule.construct;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.liris.mTrace.tools.EnumOperator;
import org.liris.mTrace.tools.pojo.rule.util.OperandePojo;
import org.liris.mTrace.tools.pojo.rule.util.OperatorOrOperandeInterface;
import org.liris.mTrace.tools.pojo.rule.util.OperatorPojo;
import org.liris.mTrace.tools.pojo.rule.where.WherePojo;


/**.
 * @author Dino
 * @10 mai 2011
 */
public class ConstructPartPojo implements Serializable {

    /**.
     * 
     */
    private static final long serialVersionUID = -1738679680953138074L;
    private List<ConstructPojo> listConstruct;
    private Boolean isInKtbs = false;
    private String traceModelUri;
    private transient Map<String, String> letMap;
    private static Pattern motifMin = Pattern.compile("Min\\([a-z A-Z 0-9 \\. , _]*\\)");
    private static Pattern motifMax = Pattern.compile("Max\\([a-z A-Z 0-9 \\. , _]*\\)");

    public ConstructPartPojo(String traceModelUri){
	this.traceModelUri = traceModelUri;
    }
    
    public void setListConstruct(List<ConstructPojo> listConstructPojo) {
	this.listConstruct = listConstructPojo;
    }

    public List<ConstructPojo> getListConstruct() {
	return listConstruct;
    }

    public void setIsInKtbs(Boolean isInKtbs) {
	this.isInKtbs = isInKtbs;
    }

    public Boolean getIsInKtbs() {
	return isInKtbs;
    }

    /**.
     * @param mapObselName
     * @return
     */
    public String toKtbsString(Map<String, String> mapObselName, List<WherePojo> wherePojos) {
	StringBuffer stringBuffer = new StringBuffer(" CONSTRUCT { [");
	letMap = new HashMap<String, String>();
//	" a :ActionInstructeur ;" 
//	" ktbs:hasSourceObsel ?Alarme_1,?Alarme_2, ?Telephonie_1; " 
//	" ktbs:hasTrace <http://localhost:8001/BASE_DE_TRACES_EDF/SparqlTrace18/>; " 
//	" ktbs:hasBegin ?hasBegin1;" 
//	" ktbs:hasEnd ?hasEnd2" 
	
	for (int i =0;i<getListConstruct().size(); i++) {
	    ConstructPojo construct = getListConstruct().get(i);
	    if(i==0){
		stringBuffer.append("http://liris.cnrs.fr/silex/2009/ktbs#hasSourceObsel");
		for (int j = 0; j < wherePojos.size(); j++) {
		    WherePojo wherePojo = wherePojos.get(j);
		    stringBuffer.append(" ?").append(wherePojo.getLocalName());
		    if(j +1 < wherePojos.size()){
			stringBuffer.append(",");
		    }
		    
		}
		stringBuffer.append(";");
		stringBuffer.append("http://liris.cnrs.fr/silex/2009/ktbs#hasTrace");
		stringBuffer.append(" <%(__destination__)s> ;");
	    }
	    stringBuffer.append("a ").append(construct.getTypeFromKtbs()).append(";");
	    for (ConstructItemPojo constructItem : construct.getListConstructItem()) {
		stringBuffer.append(" ").append(constructItem.getAttribute().getUri()).append(" ");
		if(constructItem.getOperatorOrOperande().size() > 0){
		    
		    for (int j =0;j<constructItem.getOperatorOrOperande().size();j++) {
			OperatorOrOperandeInterface operatorOrOperande = constructItem.getOperatorOrOperande().get(j);
		    	if(operatorOrOperande instanceof OperatorPojo){
		    	    if( !EnumOperator.isSpecial(operatorOrOperande.toString())){
		    		OperandePojo firstOperande = (OperandePojo)constructItem.getOperatorOrOperande().get(j-1);
		    		OperandePojo secondOperande = (OperandePojo)constructItem.getOperatorOrOperande().get(j+1);
		    	    
		    		letMap.put(firstOperande.toString()+operatorOrOperande+secondOperande.toStringSparql(),
		    			"?"+constructItem.getAttribute().getLocalName()+i+""+i);
		    	    }else{
		    		stringBuffer.append(operatorOrOperande.toString());
		    		if(EnumOperator.OPERATOR_CLOSING_PARENTHESIS.getLabel().equals(operatorOrOperande.toString())){
		    		    Matcher corespMin = motifMin.matcher(stringBuffer.toString());
		    		    while(corespMin.find()){
		    			letMap.put(corespMin.group(),"?Mi"+i);
		    			System.out.println(corespMin.group());    
		    		    }
		    		    Matcher corespMax = motifMax.matcher(stringBuffer.toString());
		    		    while(corespMax.find()){
		    			letMap.put(corespMax.group(),"?Ma"+i);
		    			System.out.println(corespMax.group());    
		    		    }
		    		}
		    	    }
		    	}else{
				//if(operatorOrOperande instanceof OperatorPojo &&  j == constructItem.getOperatorOrOperande().size()-1){
				    stringBuffer.append(operatorOrOperande.toStringSparql());
				//}
			    }
			//stringBuffer.append(operatorOrOperande.toStringSparql());
		    }
		}else{
		    stringBuffer.append("?").append(constructItem.getAttribute().getLocalName()+i);
		    //stringBuffer.append("\"NC\"");
		}
		stringBuffer.append(";");
	    }
	}
	
	stringBuffer.append("] }");
	
	return stringBuffer.toString();
    }

    public void setLetMap(Map<String, String> letMap) {
	this.letMap = letMap;
    }

    public Map<String, String> getLetMap() {
	return letMap;
    }

    public void setTraceModelUri(String traceModelUri) {
	this.traceModelUri = traceModelUri;
    }

    public String getTraceModelUri() {
	return traceModelUri;
    }
}
