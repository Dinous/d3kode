package org.liris.mTrace.actions;

import java.util.Comparator;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.liris.ktbs.client.KtbsClient;
import org.liris.ktbs.domain.interfaces.IBase;
import org.liris.ktbs.domain.interfaces.ITraceModel;
import org.liris.mTrace.kTBS.SingleKtbs;
import org.liris.mTrace.tools.ComparatorTraceModel;
import org.liris.mTrace.tools.StringOperation;

@ParentPackage(value = "mTrace")
public class BaseAction extends D3KODEAction {
	private static final long serialVersionUID = -623211718866324677L;

	//private String baseChosen;
	private KtbsClient ktbsRootClient = SingleKtbs.getInstance().getRoot();
	private String modelToCreateLocalName;
	private String lvlTraceModelSelected;
	private String baseToCreateLocalName;
	
	@Override
	@Action(value = "/base_*", results = {
		    @Result(location = "base.jsp", name = "success")
		  })
	public String execute(){
		return SUCCESS;
	}
	
//	@Action(value = "/base_baseChosen", results = {
//		    @Result(location = "base.jsp", name = "success")
//		  })
//	public String baseChosen(){
//		//SingleKtbs.getInstance().setCurrentBase(SingleKtbs.getInstance().getBase(getBaseChosen()));
//		return SUCCESS;
//	}
	
	public HashSet<IBase> getKtbsBases(){
		return SingleKtbs.getInstance().getListBase();
	}
	
	public Comparator<ITraceModel> getTraceModelComparator(){
		return new ComparatorTraceModel<ITraceModel>();
	}
	
	@Action(value = "/base_baseToCreate", results = {
		    @Result(location = "base.jsp", name = "success")
		  })
	public String baseToCreate(){
		if(StringUtils.isEmpty(getBaseToCreateLocalName())){
			addActionError(getText("error.base.label.empty"));
		}else{
			String baseLocalName = getBaseToCreateLocalName().replace(" ", "_");
			baseLocalName = baseLocalName.replace("'", "_");
			String baseUri = StringOperation.sansAccent(baseLocalName).toUpperCase() + "/";
			SingleKtbs.getInstance().getBase(getKtbsRootClient().getRootUri()+ baseUri,request.getUserPrincipal().getName(), getBaseToCreateLocalName(), true);
		}
		return SUCCESS;
	}
//	@Action(value = "/base_modelToCreate", results = {
//		    @Result(location = "base.jsp", name = "success")
//		  })
//	public String modelToCreate(){
//		if(StringUtils.isEmpty(getModelToCreateLocalName())){
//			addActionError(getText("error.model.label.empty"));
//		}else{
//			String modelLocalName = getModelToCreateLocalName().replace(" ", "_");
//			modelLocalName = modelLocalName.replace("'", "_");
//			modelLocalName = StringOperation.sansAccent(modelLocalName).toUpperCase();
//			SingleKtbs.getInstance().getTraceModel(getBaseCurrent(), modelLocalName, getModelToCreateLocalName(),getLvlTraceModelSelected(), true);
//			//SingleKtbs.getInstance().setCurrentBase(SingleKtbs.getInstance().getBase(SingleKtbs.getInstance().getCurrentBase().getUri()));
//			return SUCCESS;
//		}
//		return SUCCESS;
//	}
	
//	public void setBaseChosen(String baseChosen) {
//		this.baseChosen = baseChosen;
//	}
//
//	public String getBaseChosen() {
//		return baseChosen;
//	}

	public void setBaseToCreateLocalName(String baseLocalName) {
		this.baseToCreateLocalName = baseLocalName;
	}

	public String getBaseToCreateLocalName() {
		return baseToCreateLocalName;
	}

	public KtbsClient getKtbsRootClient() {
		return ktbsRootClient;
	}

	public void setModelToCreateLocalName(String modelToCreateLocalName) {
		this.modelToCreateLocalName = modelToCreateLocalName;
	}

	public String getModelToCreateLocalName() {
		return modelToCreateLocalName;
	}

	public void setLvlTraceModelSelected(String lvlTraceModelSelected) {
		this.lvlTraceModelSelected = lvlTraceModelSelected;
	}

	public String getLvlTraceModelSelected() {
		return lvlTraceModelSelected;
	}
}
