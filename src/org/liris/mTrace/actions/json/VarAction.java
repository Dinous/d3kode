package org.liris.mTrace.actions.json;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.liris.mTrace.tools.pojo.TransformationPojo;
import org.liris.mTrace.tools.pojo.rule.RulePojo;
import org.liris.mTrace.tools.pojo.rule.construct.ConstructPojo;
import org.liris.mTrace.tools.pojo.rule.where.WherePojo;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="mTrace")
public class VarAction extends ActionSupport  {

    /**.
     * 
     */
    private static final long serialVersionUID = 4569983821550542650L;
    private RulePojo ruleSelected;
    private TransformationPojo transformation;
    private String criteriaPart;
    private List<WherePojo> listWhere;
    private List<ConstructPojo> listConstruct;
    
    @Actions({ @Action(value = "/list_var_json", results = { 
	    @Result(name = "success", type = "json") }) })
    @Override
    public String execute() {
	
	if (ruleSelected != null && transformation.getUri() != null) {
	    try{
		setTransformationAndRule();
		if(ruleSelected.getWherePart() != null){
		    listWhere = ruleSelected.getWherePart().getListWhere();
		}
	    }catch (Exception e) {
		e.printStackTrace();
	    }
	    
	}
	return SUCCESS;
    }
    
    private void setTransformationAndRule() throws IOException, ClassNotFoundException{
	if(!StringUtils.isEmpty(transformation.getUri())){
	    transformation = TransformationPojo.deserialize(new File(transformation.getUri()));
        	if(ruleSelected != null && ruleSelected.getUri() != null){
        	    for(RulePojo rule : transformation.getRules()){
            	    	if (rule.getUri().equals(ruleSelected.getUri())){
            	    	    ruleSelected = rule;
            	    	    break;
            	    	}
            	    }
        	}
	}
    }
    
    public String getJSON() {
	return execute();
    }

    public RulePojo getRuleSelected() {
	return ruleSelected;
    }

    public void setRuleSelected(RulePojo ruleSelected) {
	this.ruleSelected = ruleSelected;
    }

    public TransformationPojo getTransformation() {
	return transformation;
    }

    public void setTransformation(TransformationPojo transformation) {
	this.transformation = transformation;
    }

    public void setCriteriaPart(String criteriaPart) {
	this.criteriaPart = criteriaPart;
    }

    public String getCriteriaPart() {
	return criteriaPart;
    }

    public void setListWhere(List<WherePojo> listWherePojo) {
	this.listWhere = listWherePojo;
    }

    public List<WherePojo> getListWhere() {
	return listWhere;
    }

    public void setListConstruct(List<ConstructPojo> listConstructPojo) {
	this.listConstruct = listConstructPojo;
    }

    public List<ConstructPojo> getListConstruct() {
	return listConstruct;
    }
}
