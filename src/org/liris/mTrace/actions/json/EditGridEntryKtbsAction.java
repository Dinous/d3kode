package org.liris.mTrace.actions.json;

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

@ParentPackage(value="mTrace")
public class EditGridEntryKtbsAction extends D3KODEAction {

	private String              oper             = "";
	private String 				id;

	private String              type;
	private String              creationDate;
	private String              localName;
	private String              label;	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<RessourceKtbsPojo> getResources() {
		return resources;
	}

	public void setResources(List<RessourceKtbsPojo> resources) {
		this.resources = resources;
	}

	private List<String>		roles;
	private List<RessourceKtbsPojo>      resources;

	  
	@Action(value = "/editGridEntryKtbs",
		    results = { @Result(location = "empty.jsp", name = "success") })
	@Override
	  public String execute() throws Exception{
	    

		 List<IKtbsResource> ressourcesKtbs = SingleKtbs.getInstance().getResources(true);
		 resources = new ArrayList<RessourceKtbsPojo>();
	    for (IKtbsResource ktbsResource : ressourcesKtbs) {
			RessourceKtbsPojo ressourceKtbsPojo = new RessourceKtbsPojo(ktbsResource);
			resources.add(ressourceKtbsPojo);
		}

	    UserPojo user;

	    if (oper.equalsIgnoreCase("del")){
	      StringTokenizer ids = new StringTokenizer(id, ",");
	      while (ids.hasMoreTokens())
	      {
	        int removeId = Integer.parseInt(ids.nextToken());
	        String uri = ((List<String>)getSession().get("resourceKtbs")).get(removeId-1);
	        
	        MTraceConst.deleteResource(uri);
	        
//	        ManageDB.delUser(user);
	        ((List<String>)getSession().get("resourceKtbs")).remove(removeId-1);
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

}
