package org.liris.mTrace.actions.json;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.liris.ktbs.domain.interfaces.IKtbsResource;
import org.liris.mTrace.MTraceConst;
import org.liris.mTrace.actions.D3KODEAction;
import org.liris.mTrace.kTBS.SingleKtbs;
import org.liris.mTrace.tools.pojo.RessourceKtbsPojo;
import org.liris.mTrace.tools.pojo.UserPojo;
import org.liris.mTrace.tools.pojo.visualization.VisualizationPojo;

@ParentPackage(value="mTrace")
public class EditGridVisualizationAction extends D3KODEAction {

	private String              oper             = "";
	private String 				id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<VisualizationPojo> getResources() {
		return visualisationPojos;
	}

	public void setResources(List<VisualizationPojo> resources) {
		this.visualisationPojos = resources;
	}

	private List<String>		roles;
	private List<VisualizationPojo>      visualisationPojos;

	  
	@Action(value = "/editGridEntryVisualization",
		    results = { @Result(location = "empty.jsp", name = "success") })
	@Override
	  public String execute() throws Exception{
	    

		File svgFolder = new File(MTraceConst.WORK_SVG_PATH + getUserLogin());
		File xmlFolder = new File(MTraceConst.WORK_XML_PATH + getUserLogin());
		

	    if (oper.equalsIgnoreCase("del")){
	      StringTokenizer ids = new StringTokenizer(id, ",");
	      while (ids.hasMoreTokens()){
	        int removeId = Integer.parseInt(ids.nextToken());
	        VisualizationPojo visualizationPojo = ((List<VisualizationPojo>)getSession().get("visualization_history")).get(removeId-1);
	        
	        File fileToDelete = new File(svgFolder+File.separator+visualizationPojo.getLocalName());
	        fileToDelete.delete();
	        fileToDelete = new File(xmlFolder+File.separator+visualizationPojo.getLocalName().replace(".svg", ".xml"));
	        fileToDelete.delete();
	        
//	        ManageDB.delUser(user);
	        ((List<VisualizationPojo>)getSession().get("visualization_history")).remove(removeId-1);
	      }
	    }

	    return SUCCESS;
	  }

	private RessourceKtbsPojo searchResourceKtbs(List<RessourceKtbsPojo> resources, String uri){
		RessourceKtbsPojo ressourceKtbsPojo = null;
		for (RessourceKtbsPojo ressourceKtbs : resources) {
			if(ressourceKtbs.getUri().equals(uri)){
				ressourceKtbsPojo = ressourceKtbs;
			}
		}
		return ressourceKtbsPojo;
	}
	

	  public void setOper(String oper){
	    this.oper = oper;
	  }

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}	

}
