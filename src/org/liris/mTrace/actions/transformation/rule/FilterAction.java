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
import org.liris.mTrace.tools.pojo.rule.where.FilterPojo;

import com.opensymphony.xwork2.ActionSupport;

/**.
 * @author Dino
 * @13 mai 2011
 */
@ParentPackage(value = "mTrace")
public class FilterAction extends ActionSupport implements SessionAware{

    /**.
     * 
     */
    private static final long serialVersionUID = -6137597012704310664L;
    private FilterPojo filter;
    private OperandePojo operande;
    private OperatorPojo operator;
    private Map<String, Object> session;
    
    @Action(value = "/filter", 
	    results = { @Result(location = "transformation/rule/filter.jsp", name = "success") })
    @Override
    public String execute() {
	try {
	    if(filter == null){
		filter = (FilterPojo)getSession().get("filter");
		if(filter.getOperatorOrOperande() == null){
		    filter.setOperatorOrOperande(new ArrayList<OperatorOrOperandeInterface>());
		}
	    }
	    if(operator != null && operator.getValueStr() != null){
		filter.getOperatorOrOperande().add(operator);
	    }else if(operande !=null && operande.getUri() != null){
		if(operande.getRange() != null && operande.getRange().equals(EnumXSDType.STRING)){
		    if(!operande.getUri().startsWith("\"")){
			operande.setUri("\"" + operande.getUri());
		    }
		    if(!operande.getUri().endsWith("\"")){
			operande.setUri(operande.getUri()+"\"");
		    }
		}
		filter.getOperatorOrOperande().add(operande);
	    }
	    getSession().put("filter", filter);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return SUCCESS;
    }
    
    @Action(value = "/remove_operator_or_operande_from_filter", 
	    results = { @Result(location = "transformation/rule/filter.jsp", name = "success") })
    public String remove_operator_or_operande() {
	try {
	    if(filter == null){
		filter = (FilterPojo)getSession().get("filter");
		if(filter.getOperatorOrOperande().size() > 0){
		    filter.getOperatorOrOperande().remove(filter.getOperatorOrOperande().size()-1);
			getSession().put("filter", filter);
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return SUCCESS;
    }
    
    public FilterPojo getFilter() {
        return filter;
    }

    public void setFilter(FilterPojo filter) {
        this.filter = filter;
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

    public void setOperator(OperatorPojo operator) {
	this.operator = operator;
    }

    public OperatorPojo getOperator() {
	return operator;
    }
}
