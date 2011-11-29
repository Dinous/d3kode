package org.liris.mTrace.actions.json;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.JSONUtil;
import org.springframework.util.CollectionUtils;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="mTrace")
public class ObselAction extends ActionSupport  {

	private static final long serialVersionUID = 3964277692301540672L;
	private String obselListWithId;
	private List<ListValue> obselList;
	private List<ListValue> secondObselList;
	private String firstOperande;
	private String firstOperandeSelected;
	
	@Actions( {
	    @Action(value = "/obselList", results = {
	    		@Result(name = "success", type = "json")
	    })
	  })
	@Override
	public String execute(){
		
		if(!StringUtils.isEmpty(obselListWithId)){
			if(CollectionUtils.isEmpty(obselList)){
				List<String> obselListTmp = JSONUtil.asList(obselListWithId.substring(1,obselListWithId.length()-1));
				obselList = new ArrayList<ListValue>();
				for (String obsel : obselListTmp) {
					String obselName = StringUtils.substringAfterLast(obsel, "/");
					obselList.add(new ListValue(obsel, obselName));
				}
			}
			if(CollectionUtils.isEmpty(secondObselList)){
				List<String> obselListTmp = JSONUtil.asList(obselListWithId.substring(1,obselListWithId.length()-1));
				secondObselList = new ArrayList<ListValue>();
				for (String obsel : obselListTmp) {
					String obselName = StringUtils.substringAfterLast(obsel, "/");
					secondObselList.add(new ListValue(obsel, obselName));
				}
			}
		}
		return SUCCESS;
	}

	 public String getJSON(){
		    return execute();
	 }

	public void setObselList(List<ListValue> obselList) {
		this.obselList = obselList;
	}

	public List<ListValue> getObselList() {
		return obselList;
	}

	public String getObselListWithId() {
		return obselListWithId;
	}

	public void setObselListWithId(String obselListWithId) {
		this.obselListWithId = obselListWithId;
	}

	public List<ListValue> getSecondObselList() {
		return secondObselList;
	}

	public void setSecondObselList(List<ListValue> secondObselList) {
		this.secondObselList = secondObselList;
	}

	public void setFirstOperande(String firstOperande) {
		this.firstOperande = firstOperande;
	}

	public String getFirstOperande() {
		return firstOperande;
	}

	public void setFirstOperandeSelected(String firstOperandeSelected) {
		this.firstOperandeSelected = firstOperandeSelected;
	}

	public String getFirstOperandeSelected() {
		return firstOperandeSelected;
	}
}
