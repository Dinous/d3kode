package org.liris.mTrace.actions.json;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.liris.mTrace.tools.EnumOperator;
import org.liris.mTrace.tools.pojo.TransformationPojo;
import org.liris.mTrace.tools.pojo.rule.RulePojo;
import org.liris.mTrace.tools.pojo.rule.util.AttributePojo;
import org.liris.mTrace.tools.pojo.rule.where.FilterPojo;
import org.liris.mTrace.tools.pojo.rule.where.WherePojo;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "mTrace")
public class OperatorAction extends ActionSupport {

    private static final long serialVersionUID = 2425406305290372120L;
//    public static final String SECOND_OPERATOR_SUPER = ">";
//    public static final String SECOND_OPERATOR_INFER = "&#60;";
//    public static final String SECOND_OPERATOR_SUPER_OR_EQUAL = ">=";
//    public static final String SECOND_OPERATOR_INFER_OR_EQUAL = "&#60;=";
//    public static final String SECOND_OPERATOR_EQUAL = "=";
//    public static final String SECOND_OPERATOR_NOTEQUAL = "!=";
    private List<ListValue> operatorList;
    private RulePojo ruleSelected;
    private TransformationPojo transformation;
    private WherePojo where;
    private List<AttributePojo> listAttribute;
    private FilterPojo filter;
    

    @Actions({ @Action(value = "/operatorList", results = { @Result(name = "success", type = "json") }) })
    @Override
    public String execute() {
	try {
	    setTransformationAndRule();
//	    for (AttributePojo attributePojo: where.getListAttribute()) {
//		if(attributePojo.getUri().equals(filter.getFirstOperande().getAttribute().getUri())){
//		    filter.getFirstOperande().setAttribute(attributePojo);
//		}
//		/*if(attributePojo.getUri().equals(filter.getSecondOperande().getAttribute().getUri())){
//		    filter.getSecondOperande().setAttribute(attributePojo);
//		}*/
//	    }
			
	    operatorList = new ArrayList<ListValue>();
//	    if (filter.getFirstOperande().getAttribute().getRange().equals(
//		    "http://www.w3.org/2001/XMLSchema#INTEGER")
//		    || filter.getFirstOperande().getAttribute().getRange().equals(
//			    "http://www.w3.org/2001/XMLSchema#DATETIME")) {
//		operatorList.add(new ListValue(SECOND_OPERATOR_SUPER,
//			SECOND_OPERATOR_SUPER));
//		operatorList.add(new ListValue(SECOND_OPERATOR_SUPER_OR_EQUAL,
//			SECOND_OPERATOR_SUPER_OR_EQUAL));
//		operatorList.add(new ListValue(SECOND_OPERATOR_INFER,
//			SECOND_OPERATOR_INFER));
//		operatorList.add(new ListValue(SECOND_OPERATOR_INFER_OR_EQUAL,
//			SECOND_OPERATOR_INFER_OR_EQUAL));
//	    }
//
//	    operatorList.add(new ListValue(SECOND_OPERATOR_EQUAL,
//		    SECOND_OPERATOR_EQUAL));
//	    operatorList.add(new ListValue(SECOND_OPERATOR_NOTEQUAL,
//		    SECOND_OPERATOR_NOTEQUAL));
	    
	    for (int i = 0; i < EnumOperator.values().length; i++) {
		operatorList.add(new ListValue(EnumOperator.values()[i].getLabel(), EnumOperator.values()[i].getLabel()));
	    }
			
		
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return SUCCESS;
    }

    private void setTransformationAndRule() throws Exception{
	if (!StringUtils.isEmpty(transformation.getUri())) {
	    transformation = TransformationPojo.deserialize(new File(
		    transformation.getUri()));
	    if (ruleSelected != null && ruleSelected.getUri() != null) {
		for (RulePojo rule : transformation.getRules()) {
		    if (rule.getUri().equals(ruleSelected.getUri())) {
			if(this.where != null){
    			for (WherePojo wherePojo : rule.getWherePart().getListWhere()) {
    			    if(wherePojo.getUri().equals(this.where.getUri())){
    				this.where = wherePojo;
    			    }
    			    break;
    			}
			}
			break;
		    }
		}
	    }
	}
    }

    public String getJSON() {
	return execute();
    }

    public void setOperatorList(List<ListValue> operatorList) {
	this.operatorList = operatorList;
    }

    public List<ListValue> getOperatorList() {
	return operatorList;
    }

    public void setWhere(WherePojo wherePojo) {
	this.where = wherePojo;
    }

    public WherePojo getWhere() {
	return where;
    }

    public void setRuleSelected(RulePojo ruleSelected) {
	this.ruleSelected = ruleSelected;
    }

    public RulePojo getRuleSelected() {
	return ruleSelected;
    }

    public void setTransformation(TransformationPojo transformation) {
	this.transformation = transformation;
    }

    public TransformationPojo getTransformation() {
	return transformation;
    }

    public void setListAttribute(List<AttributePojo> listAttributePojo) {
	this.listAttribute = listAttributePojo;
    }

    public List<AttributePojo> getListAttribute() {
	return listAttribute;
    }

    public void setFilter(FilterPojo filterPojo) {
	this.filter = filterPojo;
    }

    public FilterPojo getFilter() {
	return filter;
    }
}
