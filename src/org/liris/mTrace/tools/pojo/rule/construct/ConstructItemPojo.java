/**.
 * 
 */
package org.liris.mTrace.tools.pojo.rule.construct;

import java.io.Serializable;
import java.util.List;

import org.liris.mTrace.tools.pojo.rule.util.AttributePojo;
import org.liris.mTrace.tools.pojo.rule.util.OperatorOrOperandeInterface;

/**.
 * @author Dino
 * @10 mai 2011
 */
public class ConstructItemPojo implements Serializable, Comparable<ConstructItemPojo> {

    /**.
     * 
     */
    private static final long serialVersionUID = 6316040537955441333L;

    private AttributePojo attribute;
    private List<OperatorOrOperandeInterface> operatorOrOperande;
    
    
    /**.
     * 
     */
    @Deprecated
    public ConstructItemPojo() {}

    public ConstructItemPojo(AttributePojo attributePojo,  List<OperatorOrOperandeInterface> operandePojo){
	this();
	this.attribute = attributePojo;
	this.operatorOrOperande = operandePojo;
    }
    
    public void setAttribute(AttributePojo attributePojo) {
	this.attribute = attributePojo;
    }

    public AttributePojo getAttribute() {
	return attribute;
    }

    public void setOperatorOrOperande(List<OperatorOrOperandeInterface> operatorOrOperande) {
	this.operatorOrOperande = operatorOrOperande;
    }

    public List<OperatorOrOperandeInterface> getOperatorOrOperande() {
	return operatorOrOperande;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(ConstructItemPojo o) {
	 return getAttribute().getLocalName().compareTo(o.getAttribute().getLocalName()); 
    }
    
    
}
