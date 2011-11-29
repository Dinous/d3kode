package org.liris.mTrace.actions.json;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.liris.ktbs.domain.interfaces.IAttributeType;
import org.liris.ktbs.domain.interfaces.IUriResource;
import org.liris.mTrace.tools.DefaultAttributeType;
import org.liris.mTrace.tools.pojo.TransformationPojo;
import org.liris.mTrace.tools.pojo.rule.RulePojo;
import org.liris.mTrace.tools.pojo.rule.construct.ConstructItemPojo;
import org.liris.mTrace.tools.pojo.rule.util.AttributePojo;
import org.liris.mTrace.tools.pojo.rule.where.WherePojo;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="mTrace")
public class AttributeTypeAction extends ActionSupport  {

	private static final long serialVersionUID = -2503286960672697603L;
	private String localName;
	private RulePojo ruleSelected;
	private TransformationPojo transformation;
	private WherePojo where;
	private ConstructItemPojo newConstructItem;
	
	private List<AttributePojo> listAttribute;
	
	@Actions( {
	    @Action(value = "/attributeList", results = {
	    		@Result(name = "success", type = "json")
	    })
	  })
	@Override
	public String execute(){
		try {
		    if(where != null && !StringUtils.isEmpty(this.where.getUri())){
			setTransformationAndRule();
			setListAttribute(where.getListAttribute());
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
			for (WherePojo wherePojo : rule.getWherePart().getListWhere()) {
			    if(wherePojo.getUri().equals(this.where.getUri())){
				this.where = wherePojo;
				break;
			    }
			    
			}
		    }
		}
	    }
	}
    }
    private List<ListValue> makeRange(List<IUriResource> attributes) {
	
	List<ListValue> ret = new ArrayList<ListValue>();
	List<String> tmpList = new ArrayList<String>();
	for (IUriResource attribute : attributes) {
	    ListValue listValue = null;
	    // IUriResource attribute =
	    // SingleKtbs.getInstance().getRoot().getResourceService().getResource(attributeStr,
	    // IAttributeType.class);
	    if (attribute instanceof IAttributeType) {
		IAttributeType attributeType = (IAttributeType) attribute;
		if (!attributeType.getRanges().isEmpty())
		    listValue = new ListValue(attributeType.getRanges().toArray()[0].toString(), attributeType.getRanges().toArray()[0].toString());

	    } else if (attribute instanceof DefaultAttributeType) {
		DefaultAttributeType attributeType = (DefaultAttributeType) attribute;
		if (!attributeType.getRanges().isEmpty())
		    listValue = new ListValue(attributeType.getRanges().toArray()[0].toString(), attributeType.getRanges().toArray()[0].toString());
	    }
	    if(!tmpList.contains(listValue.getMyKey())){
		tmpList.add(listValue.getMyKey());	    
	    	ret.add(listValue);
	    }
	}
	return ret;
    }
	
	public String getJSON(){
	   return execute();
	}

	public void setLocalName(String localName) {
	    this.localName = localName;
	}

	public String getLocalName() {
	    return localName;
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

	public void setWhere(WherePojo wherePojo) {
	    this.where = wherePojo;
	}

	public WherePojo getWhere() {
	    return where;
	}

	public void setListAttribute(List<AttributePojo> listAttributePojo) {
	    this.listAttribute = listAttributePojo;
	}

	public List<AttributePojo> getListAttribute() {
	    return listAttribute;
	}

	public void setNewConstructItem(ConstructItemPojo newConstructItem) {
	    this.newConstructItem = newConstructItem;
	}

	public ConstructItemPojo getNewConstructItem() {
	    return newConstructItem;
	}
		
	
}
