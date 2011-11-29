package org.liris.mTrace.actions.json;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="mTrace")
public class TypeSecondOperandeAction extends ActionSupport  {

	private static final long serialVersionUID = -7510499604219132729L;
	public static final String SECOND_OPERANDE_TEXT = "text";
	public static final String SECOND_OPERANDE_OBSELTYPE = "obselType";
	private List<ListValue> typeSecondOperandeList;
	
	@Actions( {
	    @Action(value = "/typeSecondOperandeList", results = {
	    		@Result(name = "success", type = "json")
	    })
	  })
	@Override
	public String execute(){
		if(CollectionUtils.isEmpty(typeSecondOperandeList)){
			typeSecondOperandeList = new ArrayList<ListValue>();
			typeSecondOperandeList.add(new ListValue(SECOND_OPERANDE_TEXT, SECOND_OPERANDE_TEXT));
			typeSecondOperandeList.add(new ListValue(SECOND_OPERANDE_OBSELTYPE, SECOND_OPERANDE_OBSELTYPE));
		}
		return SUCCESS;
	}

	 public String getJSON(){
		    return execute();
	 }

	public void setTypeSecondOperandeList(List<ListValue> typeSecondOperandeList) {
		this.typeSecondOperandeList = typeSecondOperandeList;
	}

	public List<ListValue> getTypeSecondOperandeList() {
		return typeSecondOperandeList;
	}
}
