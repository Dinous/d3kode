package org.liris.mTrace.actions.json;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.liris.ktbs.domain.interfaces.IObselType;
import org.liris.ktbs.domain.interfaces.ITraceModel;
import org.liris.mTrace.kTBS.SingleKtbs;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="mTrace")
public class ListTraceModelAction extends ActionSupport  {

	private static final long serialVersionUID = -2503286960672697603L;
	private List<ListValue> listTraceModel;
	private List<ListValue> listObselType;
	private String traceModelSelected;

	@Actions( {
	    @Action(value = "/traceModel", results = {
	    		@Result(name = "success", type = "json")
	    })
	  })
	@Override
	public String execute(){
		if(listTraceModel == null){
			listTraceModel = new ArrayList<ListValue>();
			List<ITraceModel> traceModels = new ArrayList<ITraceModel>(SingleKtbs.getInstance().getCurrentBase().getTraceModels());
			for (ITraceModel iTraceModel : traceModels) {
				listTraceModel.add(new ListValue(iTraceModel.getUri(), iTraceModel.getLabel()));
			}
		}
			if(!StringUtils.isEmpty(traceModelSelected)){
				listObselType = new ArrayList<ListValue>();
				for (ITraceModel traceModel : SingleKtbs.getInstance().getCurrentBase().getTraceModels()) {
					if(traceModel.getUri().equals(traceModelSelected)){
						for (IObselType obselType : traceModel.getObselTypes()) {
							listObselType.add(new ListValue(obselType.getUri(), obselType.getLocalName()));
						}
					}
				}
				
				traceModelSelected += "";
			}
		
		return SUCCESS;
	}
	
	 public String getJSON(){
	    return execute();
	 }
	
	public void setListTraceModel(List<ListValue> listTraceModel) {
		this.listTraceModel = listTraceModel;
	}

	public List<ListValue> getListTraceModel() {
		return listTraceModel;
	}

	public void setTraceModelSelected(String traceModelSelected) {
		this.traceModelSelected = traceModelSelected;
	}

	public String getTraceModelSelected() {
		return traceModelSelected;
	}

	public List<ListValue> getListObselType() {
		return listObselType;
	}

	public void setListObselType(List<ListValue> listObselType) {
		this.listObselType = listObselType;
	}
}
