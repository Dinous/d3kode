/**.
 * 
 */
package org.liris.mTrace.actions.transformation.rule;

import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.liris.mTrace.tools.EnumXSDType;
import org.liris.mTrace.tools.pojo.rule.construct.ConstructItemPojo;
import org.liris.mTrace.tools.pojo.rule.util.OperandePojo;
import org.liris.mTrace.tools.pojo.rule.util.OperatorOrOperandeInterface;
import org.liris.mTrace.tools.pojo.rule.util.OperatorPojo;

import com.opensymphony.xwork2.ActionSupport;

/**.
 * @author Dino
 * @16 mai 2011
 */
@ParentPackage(value = "mTrace")
public class ConstructAction extends ActionSupport implements SessionAware {

    /**.
     * 
     */
    private static final long serialVersionUID = 8177968631515600950L;
    private ConstructItemPojo constructItem; 
    private OperandePojo operande;
    private OperatorPojo operator;
    private Map<String, Object> session;
    
    @Action(value = "/construct", 
	    results = { @Result(location = "transformation/rule/construct.jsp", name = "success") })
    @Override
    public String execute() {
	try {
	    if(constructItem == null){
		constructItem = (ConstructItemPojo)getSession().get("constructItem");
		if(constructItem.getOperatorOrOperande() == null){
		    constructItem.setOperatorOrOperande(new ArrayList<OperatorOrOperandeInterface>());
		}
	    }
	    if(operator != null && operator.getValueStr() != null){
		constructItem.getOperatorOrOperande().add(operator);
	    }else if(operande !=null && operande.getUri() != null){
		
		if(operande.getRange() != null && operande.getRange().equals(EnumXSDType.STRING)){
		    if(!operande.getUri().startsWith("\"")){
			operande.setUri("\"" + operande.getUri());
		    }
		    if(!operande.getUri().endsWith("\"")){
			operande.setUri(operande.getUri()+"\"");
		    }
		}
		
		constructItem.getOperatorOrOperande().add(operande);
	    }
	    getSession().put("constructItem", constructItem);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return SUCCESS;
    }

    @Action(value = "/remove_operator_or_operande", 
	    results = { @Result(location = "transformation/rule/construct.jsp", name = "success") })
    public String remove_operator_or_operande() {
	try {
	    if(constructItem == null){
		constructItem = (ConstructItemPojo)getSession().get("constructItem");
		if(constructItem.getOperatorOrOperande().size() > 0){
		    constructItem.getOperatorOrOperande().remove(constructItem.getOperatorOrOperande().size()-1);
			getSession().put("constructItem", constructItem);
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return SUCCESS;
    }
    
    public void setOperande(OperandePojo operande) {
	this.operande = operande;
    }

    public OperandePojo getOperande() {
	return operande;
    }
    @Override
    public void setSession(Map<String, Object> session) {
	this.session = session;
    }

    public Map<String, Object> getSession(){
	return this.session;
    }

    public OperatorPojo getOperator() {
        return operator;
    }

    public void setOperator(OperatorPojo operator) {
        this.operator = operator;
    }

    public ConstructItemPojo getConstructItem() {
        return constructItem;
    }

    public void setConstructItem(ConstructItemPojo constructItem) {
        this.constructItem = constructItem;
    }

}
