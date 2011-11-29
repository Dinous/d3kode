/**.
 * 
 */
package org.liris.mTrace.tools.pojo.rule.util;

import java.io.Serializable;

import org.liris.mTrace.tools.EnumOperator;

/**.
 * @author Dino
 * @15 mai 2011
 */
public class OperatorPojo implements OperatorOrOperandeInterface, Serializable{

    /**.
     * 
     */
    private static final long serialVersionUID = 3838448133227397851L;
    private EnumOperator value;
    private String valueStr;
    
    /**.
     * 
     */
    @Deprecated
    public OperatorPojo() {}

    public OperatorPojo(EnumOperator enumOperator){
	this.value = enumOperator;
    }
    
    protected void setValue(EnumOperator value) {
	this.value = value;
    }

    protected EnumOperator getValue() {
	return value;
    }

    public void setValueStr(String valueStr) {
	value = EnumOperator.getEnumOperator(valueStr);
	this.valueStr = valueStr;
    }

    public String getValueStr() {
	if(valueStr == null && value != null){
	    return value.getLabel();
	}
	return valueStr;
    }
    @Override
    public String toString(){
	return getValueStr();
    }

    /* (non-Javadoc)
     * @see org.liris.mTrace.tools.pojo.rule.util.OperatorOrOperandeInterface#toStringSparql()
     */
    @Override
    public String toStringSparql() {
	return toString();
    }
    
}
