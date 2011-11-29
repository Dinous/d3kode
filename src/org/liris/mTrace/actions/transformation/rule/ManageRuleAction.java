/**.
 * 
 */
package org.liris.mTrace.actions.transformation.rule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.liris.ktbs.domain.interfaces.ITraceModel;
import org.liris.mTrace.MTraceConst;
import org.liris.mTrace.kTBS.SingleKtbs;
import org.liris.mTrace.tools.EnumOperator;
import org.liris.mTrace.tools.EnumXSDType;
import org.liris.mTrace.tools.pojo.TransformationPojo;
import org.liris.mTrace.tools.pojo.rule.RulePojo;
import org.liris.mTrace.tools.pojo.rule.construct.ConstructItemPojo;
import org.liris.mTrace.tools.pojo.rule.construct.ConstructPartPojo;
import org.liris.mTrace.tools.pojo.rule.construct.ConstructPojo;
import org.liris.mTrace.tools.pojo.rule.util.OperandePojo;
import org.liris.mTrace.tools.pojo.rule.util.OperatorOrOperandeInterface;
import org.liris.mTrace.tools.pojo.rule.util.OperatorPojo;
import org.liris.mTrace.tools.pojo.rule.where.FilterPojo;
import org.liris.mTrace.tools.pojo.rule.where.WherePartPojo;
import org.liris.mTrace.tools.pojo.rule.where.WherePojo;

import com.opensymphony.xwork2.ActionSupport;

/**
 * .
 * 
 * @author Dino
 * @2 mai 2011
 */
@ParentPackage(value = "mTrace")
public class ManageRuleAction extends ActionSupport implements SessionAware {

    /**.
     * 
     */
    private static final long serialVersionUID = 9137613713240767991L;
    private TransformationPojo transformation;
    private String obselTypeUri;
    private String obselTypeLabel;
    private RulePojo ruleSelected;
    private WherePojo where;
    private ConstructPojo construct;
    private ConstructItemPojo constructItem; 
    //private ConstructItemPojo newConstructItem; 
    private String criteriaPart;
    private Integer whereToDeleteId;
    private Integer filterToDeleteId;
    private FilterPojo filter;
    private Map<String, Object> session;
    private Integer varToDeleteIndice;
    private String ruleToCopyUri;
    
    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Action(value = "/transformation_rule_prepare", results = { 
	    @Result(location = "transformation/rule/management.jsp", name = "success") })
    @Override
    public String execute() throws Exception {
	setTransformationAndRule();
	return SUCCESS;
    }

    @Action(value = "/nice_rule", results = { 
	    @Result(location = "transformation/read/niceRule.jsp", name = "success") })
    public String niceRule() {
	try{
	setTransformationAndRule();
	}catch(Exception e){
	    addActionError(e.getMessage());
	    e.printStackTrace();
	}
	return SUCCESS;
    }
    
    @Action(value = "/update_rule_not_exists", 

	    results = { @Result(location = "transformation/rule/management.jsp", name = "success") })
	    public String update_rule_not_exists() {
	try {
	    setTransformationAndRule();
	    if(ruleSelected.getWherePart() != null && ruleSelected.getWherePart().getListFilter() != null){
		FilterPojo firstFilter = ruleSelected.getWherePart().getListFilter().get(0);
		OperandePojo firstOperande = (OperandePojo)firstFilter.getOperatorOrOperande().get(0);
		if(firstOperande != null &&
			!firstOperande.getLabel().contains("NOT EXISTS")){
		OperatorOrOperandeInterface notExistsBegin = new OperandePojo(MTraceConst.SPARQL_NOT_EXISTS_OPEN, MTraceConst.SPARQL_NOT_EXISTS_OPEN);
		OperatorOrOperandeInterface notExistsEnd = new OperandePojo(MTraceConst.SPARQL_NOT_EXISTS_CLOSE, MTraceConst.SPARQL_NOT_EXISTS_CLOSE);
		FilterPojo filterBegin = new FilterPojo();
		filterBegin.setOperatorOrOperande(new ArrayList<OperatorOrOperandeInterface>());
		filterBegin.getOperatorOrOperande().add(notExistsBegin);
		ruleSelected.getWherePart().getListFilter().add(0, filterBegin);
		
		FilterPojo filterEnd = new FilterPojo();
		filterEnd.setOperatorOrOperande(new ArrayList<OperatorOrOperandeInterface>());
		filterEnd.getOperatorOrOperande().add(notExistsEnd);
		ruleSelected.getWherePart().getListFilter().add(filterEnd);
		}else{
		    ruleSelected.getWherePart().getListFilter().remove(0);
		    ruleSelected.getWherePart().getListFilter().remove(ruleSelected.getWherePart().getListFilter().size()-1);
		}
	    }
	    transformation.serialize();
	} catch (Exception e) {
	    e.printStackTrace();
	    addActionError(e.getMessage());
	}
	return SUCCESS;
    }
    
    @Action(value = "/delete_var", results = { 
	    @Result(location = "transformation/rule/management.jsp", name = "success") })
    public String delete_var() {
	try {
	    	setTransformationAndRule();
		if(varToDeleteIndice != null){
		    if(criteriaPart.equals("where")){
			String localNameOfWhereCriteria = ruleSelected.getWherePart().getListWhere().get(varToDeleteIndice.intValue()).getLocalName();; 
			ruleSelected.getWherePart().getListWhere().remove(varToDeleteIndice.intValue());
			if(ruleSelected.getWherePart().getListFilter() != null){
			for (int i = 0; i < ruleSelected.getWherePart().getListFilter().size(); i++) {
			    FilterPojo filterPojo = ruleSelected.getWherePart().getListFilter().get(i);
			    if(filterPojo.getOperatorOrOperande() != null){
    			    for (int j = 0; j < filterPojo.getOperatorOrOperande().size(); j++) {
    				OperatorOrOperandeInterface operatorOrOperande = filterPojo.getOperatorOrOperande().get(j);
    				if(operatorOrOperande.toString().contains(localNameOfWhereCriteria)){
    				    filterPojo.setOperatorOrOperande(null);
    				    break;
    				}
    			    }
    			    if(filterPojo.getOperatorOrOperande() == null){
    				ruleSelected.getWherePart().getListFilter().remove(filterPojo);
    			    }
			    }
			}
			}
		    }else if(criteriaPart.equals("construct")){
			ruleSelected.getConstructPart().getListConstruct().remove(varToDeleteIndice.intValue());
		    }
		}
	    	 transformation.serialize();
	} catch (Exception e) {
	    addActionError(e.getMessage());
	} 
	return SUCCESS;
    }
    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Action(value = "/delete_filter_or_construct", results = { 
	    @Result(location = "transformation/rule/management.jsp", name = "success") })
    public String delete_filter_or_construct() {
	try {
	    if(whereToDeleteId != null || filterToDeleteId != null){
		setTransformationAndRule();
		if(criteriaPart.equals("where")){
        	    	ruleSelected.getWherePart().getListFilter().remove(filterToDeleteId.intValue());
		}else if(criteriaPart.equals("construct")){
		    
		}
	    	 transformation.serialize();
	    }
	} catch (Exception e) {
	    addActionError(e.getMessage());
	} 
	return SUCCESS;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Action(value = "/save_criteria_where", results = { 
	    @Result(location = "transformation/rule/management.jsp", name = "success") })
    public String save_criteria_where() {
	try {
	    setTransformationAndRule();
	    WherePartPojo wherePart = ruleSelected.getWherePart();
	    if(wherePart.getListFilter() == null){
		wherePart.setListFilter(new ArrayList<FilterPojo>());
	    }
	    
	    filter = (FilterPojo)getSession().get("filter");
	    ruleSelected.getWherePart().getListFilter().add(filter);
	    transformation.serialize();
	    getSession().remove(getSession().get("filter"));
	} catch (Exception e) {
	    addActionError(e.getMessage());
	}
	return SUCCESS;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Action(value = "/save_criteria_construct", results = { 
	    @Result(location = "transformation/rule/management.jsp", name = "success") })
    public String save_criteria_construct() {
	try {
	    setTransformationAndRule();
	    ConstructItemPojo newConstructItem = (ConstructItemPojo)getSession().get("constructItem");
	    Integer indexConstructItem = null;
	    for(int i = 0; i < construct.getListConstructItem().size();i++){
		ConstructItemPojo constructI = construct.getListConstructItem().get(i);
		if(newConstructItem.getAttribute().getUri() != null){
		    if(newConstructItem.getAttribute().getUri().equals(constructI.getAttribute().getUri())){
			indexConstructItem = i; 
			break;
		    }
		}
	    }
	    
	    construct.getListConstructItem().set(indexConstructItem, newConstructItem);
	   ruleSelected.getConstructPart().getListConstruct().set(
		   ruleSelected.getConstructPart().getListConstruct().indexOf(construct), construct);
	    transformation.serialize();
	    criteriaPart = "";
	    getSession().remove("constructItem");
	} catch (Exception e) {
	    addActionError(e.getMessage());
	}
	return SUCCESS;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Action(value = "/add_var_where", results = { 
	    @Result(location = "transformation/rule/management.jsp", name = "success") })
    public String add_var_where() {
	if (!StringUtils.isEmpty(obselTypeUri)) {
	    try {
		setTransformationAndRule();
		Integer numberOfCriteria = 0;
		if(ruleSelected.getWherePart() != null && ruleSelected.getWherePart().getListWhere() != null){
		    numberOfCriteria = ruleSelected.getWherePart().getListWhere().size();
		}else{
		    ruleSelected.setWherePart(new WherePartPojo(transformation.getTraceModelSourceSelected()));
		    ruleSelected.getWherePart().setListWhere(new ArrayList<WherePojo>());
		}
		where = new WherePojo();
		where.setUri(obselTypeUri + "_" + numberOfCriteria);
		where.setLabel(obselTypeLabel+ "_" + numberOfCriteria);
		where.setLocalName(StringUtils.substringAfterLast(where.getUri(), "/"));
		where.setTypeFromKtbs(obselTypeUri);
		where.setListAttributePojoFromKtbs(SingleKtbs.getInstance().getListAttributeTypeOfObselType(transformation.getTraceModelSource(),obselTypeUri));
		
		ruleSelected.getWherePart().getListWhere().add(where);
		transformation.getRules().set(
			transformation.getRules().indexOf(ruleSelected),
			ruleSelected);
		transformation.serialize();
	    } catch (Exception e) {
		addActionError(e.getMessage());
		e.printStackTrace();
	    }

	} else {
	    addActionError(getText("selectedRule.empty"));
	}
	return SUCCESS;
    }

    @Action(value = "/list_obseltype_*", results = { 
    	    @Result(location = "transformation/rule/add_obselType_{1}.jsp", name = "success") })
        public String list_obseltype() {
    	
    	try {
    		setTransformationAndRule();
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    	    }
    	
    	return SUCCESS;
    }
    
    
    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Action(value = "/add_var_construct", results = { 
	    @Result(location = "transformation/rule/management.jsp", name = "success") })
    public String add_var_construct() {
	if (!StringUtils.isEmpty(obselTypeUri)) {
	    try {
		setTransformationAndRule();
		Integer numberOfCriteria = 0;
		if(ruleSelected.getConstructPart() != null && ruleSelected.getConstructPart().getListConstruct() != null){
		    numberOfCriteria = ruleSelected.getConstructPart().getListConstruct().size();
		}else{
		    ruleSelected.setConstructPart(new ConstructPartPojo(transformation.getTraceModelTargetSelected()));
		    ruleSelected.getConstructPart().setListConstruct(new ArrayList<ConstructPojo>());
		}
		construct = new ConstructPojo();
		construct.setUri(obselTypeUri + "_" + numberOfCriteria);
		construct.setLabel(obselTypeLabel+ "_" + numberOfCriteria);
		construct.setTypeFromKtbs(transformation.getTraceModelTarget(), obselTypeUri);
		ruleSelected.getConstructPart().getListConstruct().add(construct);
		transformation.getRules().set(
			transformation.getRules().indexOf(ruleSelected),
			ruleSelected);
		transformation.serialize();
	    } catch (Exception e) {
		System.out.println(e.getMessage());
	    }

	} else {
	    addActionError(getText("selectedRule.empty"));
	}
	return SUCCESS;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Action(value = "/create_rule_prepare", results = { 
	    @Result(location = "transformation/rule/ihm.jsp", name = "success") })
    public String create_or_update_rule_prepare() {
//	if (ruleSelected != null && ruleSelected.getUri() != null) {
	    try {
		setTransformationAndRule();
	    }catch(Exception e){
		e.printStackTrace();
	    }
//	}
	return SUCCESS;
    }

    public List<RulePojo> getListRulePojo(String transformationLocalName, String traceModelSource, String traceModelDest){
	List<RulePojo> rulePojos = new ArrayList<RulePojo>();
	try {
	    rulePojos = TransformationPojo.deserializeAll(transformationLocalName, traceModelSource, traceModelDest);
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
	return rulePojos;
    }
    
    
    @Action(value = "/import_rule_in_transformation",
	    results = { @Result(location = "empty.jsp", name = "success") })
    public String import_rule_in_transformation(){
	try {
	    setTransformationAndRule();
	    int indiceRule = 0;
	    RulePojo rulePojo = TransformationPojo.findRulePojo(transformation.getLocalName(), ruleToCopyUri);
	    if(transformation.getRules() == null){
		transformation.setRules(new ArrayList<RulePojo>());
	    }else{
		indiceRule = transformation.getRules().size();
	    }
	    rulePojo.setId(indiceRule);
	    transformation.getRules().add(rulePojo);
	    transformation.serialize();
	} catch (Exception e) {
	    e.printStackTrace();
	    System.out.println(e.getMessage());
	}
	return SUCCESS;
    }
    
    public TransformationPojo getTransformationOfRuleUri(String ruleUri){
	try {
	    setTransformationAndRule();
	    TransformationPojo transformationPojoFound = TransformationPojo.findTransformationPojo(transformation.getLocalName(), ruleUri);
	    return transformationPojoFound;
	} catch (Exception e) {
	    e.printStackTrace();
	    System.out.println(e.getMessage());
	}
	return null;
    }
    
    
    @Action(value = "/update_rule_prepare_where", results = { 
	    @Result(location = "transformation/rule/where/where.jsp", name = "success") })
    public String update_rule_prepare_where() {
	try {
	    setTransformationAndRule();
	    getSession().put("filter", new FilterPojo());
	} catch (Exception e) {
	}
	return SUCCESS;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Action(value = "/update_rule_prepare_construct", results = { 
	    @Result(location = "transformation/rule/construct/construct.jsp", name = "success") })
    public String update_rule_prepare_construct() {
	try {
	    setTransformationAndRule();
	    getSession().put("constructItem", new ConstructItemPojo(constructItem.getAttribute(), new ArrayList<OperatorOrOperandeInterface>()));
	} catch (Exception e) {
	}
	return SUCCESS;
    }

    public List<EnumXSDType> getListXsdType(){
	List<EnumXSDType> listXsdType = new ArrayList<EnumXSDType>();
	for (EnumXSDType enumXSDType : EnumXSDType.values()) {
	    listXsdType.add(enumXSDType);
	} 
	return listXsdType;
    }
    
    private void setTransformationAndRule() throws Exception {
	if (!StringUtils.isEmpty(transformation.getUri())) {
	    transformation = TransformationPojo.deserialize(new File(
		    transformation.getUri()));
	    	if (ruleSelected != null && transformation.getRules() != null) {
	    	    if(ruleSelected.getId() == null){
        		for (RulePojo rule : transformation.getRules()) {
        		    if (rule.getUri().equals(ruleSelected.getUri())) {
        			ruleSelected = rule;
        			break;
        		    }
        		}
	    	    }else{
	    		ruleSelected = transformation.getRules().get(ruleSelected.getId());
	    	    }
	    	
		if (ruleSelected.getWherePart() != null
			&& ruleSelected.getWherePart().getListWhere() != null
			&& this.where != null) {
		    for (WherePojo wherePojo : ruleSelected.getWherePart()
			    .getListWhere()) {
			if (wherePojo.getUri().equals(
				this.where.getUri())) {
			    this.where = wherePojo;
			    break;
			}
		    }
		}
		if (ruleSelected.getConstructPart() != null
			&& ruleSelected.getConstructPart().getListConstruct() != null
			&& this.construct != null) {
		    for (ConstructPojo constructPojo : ruleSelected
			    .getConstructPart().getListConstruct()) {
			if (constructPojo.getUri().equals(
				this.construct.getUri())) {
			    this.construct = constructPojo;
			    if (this.constructItem != null) {
				for (ConstructItemPojo constructItem : constructPojo
					.getListConstructItem()) {
				    if (constructItem
					    .getAttribute()
					    .getUri()
					    .equals(this.constructItem
						    .getAttribute()
						    .getUri())) {
					this.constructItem = constructItem;
					break;
				    }
				}
			    }
			}
		    }
		}
	    }
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Action(value = "/create_or_update_rule",
	    results = { @Result(location = "empty.jsp", name = "success") })
    public String create_or_update_rule() {
	if (ruleSelected != null
		&& !StringUtils.isEmpty(ruleSelected.getLabel())) {
	    try {
		String description = ruleSelected.getDescription();
		String label = ruleSelected.getLabel();
		setTransformationAndRule();
		
		if (transformation.getRules() == null) {
		    transformation.setRules(new ArrayList<RulePojo>());
		}
		
		if(ruleSelected.getId() == null){
		    RulePojo rulePojo = new RulePojo(ruleSelected.getLabel());
    			rulePojo.setId(transformation.getRules().size());
    			rulePojo.setUri("rule_"
    			+ String.valueOf(rulePojo.getId()));
    			rulePojo.setDescription(ruleSelected.getDescription());
    			transformation.getRules().add(rulePojo);
		}else{
		    ruleSelected.setDescription(description);
		    ruleSelected.setLabel(label);
		}
		
		transformation.serialize(new File(transformation.getUri()));
	    } catch (Exception e) {
		addActionError(e.getMessage());
	    }
	}
	return SUCCESS;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Action(value = "/delete_rule")
    public String delete_rule() {
	if (ruleSelected != null
		&& !StringUtils.isEmpty(ruleSelected.getUri())) {
	    try {
		setTransformationAndRule();
		if(transformation.getRules() != null){
		    transformation.getRules().remove(
			transformation.getRules().indexOf(ruleSelected));
		}
		ruleSelected =null;
		transformation.serialize(new File(transformation.getUri()));
	    } catch (Exception e) {
		System.out.println("delete_rule"+e.getMessage());
	    }
	}
	return NONE;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Action(value = "/list_rule", results = { @Result(location = "transformation/rule/list.jsp", name = "success") })
    public String list_rule() {
	try {
	    setTransformationAndRule();
	} catch (Exception e) {
	}
	return SUCCESS;
    }

    public List<OperatorPojo> getOperatorList(){
	List<OperatorPojo> operatorList = new ArrayList<OperatorPojo>();
	for (int i = 0; i < EnumOperator.values().length; i++) {
	    operatorList.add(new OperatorPojo(EnumOperator.values()[i]));
	}
	return operatorList;
    }
    
    public void setTransformation(TransformationPojo transformation) {
	this.transformation = transformation;
    }

    public TransformationPojo getTransformation() {
	return transformation;
    }

    public void setObselTypeUri(String obselTypeUri) {
	this.obselTypeUri = obselTypeUri;
    }

    public String getObselTypeUri() {
	return obselTypeUri;
    }

    public void setRuleSelected(RulePojo ruleSelected) {
	this.ruleSelected = ruleSelected;
    }

    public RulePojo getRuleSelected() {
	return ruleSelected;
    }

    public void setCriteriaPart(String criteriaPart) {
	this.criteriaPart = criteriaPart;
    }

    public String getCriteriaPart() {
	return criteriaPart;
    }

    public WherePojo getWhere() {
        return where;
    }

    public void setWhere(WherePojo wherePojo) {
        this.where = wherePojo;
    }

    public ConstructPojo getConstruct() {
        return construct;
    }

    public void setConstruct(ConstructPojo constructPojo) {
        this.construct = constructPojo;
    }

    public void setFilter(FilterPojo filterPojo) {
	this.filter = filterPojo;
    }

    public FilterPojo getFilter() {
	return filter;
    }

    public void setConstructItem(ConstructItemPojo constructItem) {
	this.constructItem = constructItem;
    }

    public ConstructItemPojo getConstructItem() {
	return constructItem;
    }

    public void setWhereToDeleteId(Integer whereToDeleteId) {
	this.whereToDeleteId = whereToDeleteId;
    }

    public Integer getWhereToDeleteId() {
	return whereToDeleteId;
    }

    public void setFilterToDeleteId(Integer filterToDeleteId) {
	this.filterToDeleteId = filterToDeleteId;
    }

    public Integer getFilterToDeleteId() {
	return filterToDeleteId;
    }

    /* (non-Javadoc)
     * @see org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
     */
    @Override
    public void setSession(Map<String, Object> session) {
	this.session = session;
    }

    public Map<String, Object> getSession(){
	return this.session;
    }

    public void setVarToDeleteIndice(Integer varToDeleteIndice) {
	this.varToDeleteIndice = varToDeleteIndice;
    }

    public Integer getVarToDeleteIndice() {
	return varToDeleteIndice;
    }

    public String getObselTypeLabel() {
        return obselTypeLabel;
    }

    public void setObselTypeLabel(String obselTypeLabel) {
        this.obselTypeLabel = obselTypeLabel;
    }

    public void setRuleToCopyUri(String ruleToCopyUri) {
	this.ruleToCopyUri = ruleToCopyUri;
    }

    public String getRuleToCopyUri() {
	return ruleToCopyUri;
    }

}
