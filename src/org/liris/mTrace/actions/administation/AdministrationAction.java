/**.
 * 
 */
package org.liris.mTrace.actions.administation;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.liris.ktbs.domain.interfaces.IAttributeType;
import org.liris.ktbs.domain.interfaces.IBase;
import org.liris.ktbs.domain.interfaces.IKtbsResource;
import org.liris.ktbs.domain.interfaces.IMethod;
import org.liris.ktbs.domain.interfaces.IObselType;
import org.liris.ktbs.domain.interfaces.ITrace;
import org.liris.ktbs.domain.interfaces.ITraceModel;
import org.liris.mTrace.MTraceConst;
import org.liris.mTrace.actions.D3KODEAction;
import org.liris.mTrace.kTBS.SingleKtbs;
import org.liris.mTrace.tools.pojo.UserPojo;

/**.
 * @author Dino
 * @14 juin 2011
 */
public class AdministrationAction extends D3KODEAction {

    private List<IKtbsResource> resources;
    private String resourceUri;
    
    @Action(value = "/administration",
		results = {@Result(location = "administration/manage.jsp", name = "success")}/*,
		interceptorRefs ={@InterceptorRef(value ="roles",params={"allowedRoles", "admin"}),
		@InterceptorRef("defaultStack")}*/)
    public String manage()  {
	setResources(SingleKtbs.getInstance().getResources(getIsExpert()));
        return SUCCESS;
    }

    /**
     * <result name="login" type="redirectAction">
           <param name="actionName">accueil</param>
          </result>.
     * @return
     */
    
    @Action(value = "/delete_resource",
		results = {@Result(name = "success", type = "redirectAction", params={"actionName","administration"} )},
		interceptorRefs ={@InterceptorRef(value ="roles",params={"allowedRoles", "admin"}),
		@InterceptorRef("defaultStack")})
    public String delete_resource()  {
    	
    	MTraceConst.deleteResource(resourceUri);
	
        return SUCCESS;
    }
    
    public List<IKtbsResource> getResources() {
        return resources;
    }

    public void setResources(List<IKtbsResource> resources) {
        this.resources = resources;
    }

    public void setResourceUri(String resourceUri) {
	this.resourceUri = resourceUri;
    }

    public String getResourceUri() {
	return resourceUri;
    }
    
    public String getEditRules(){
    	return "{multiple:true,value:'admin:Administrateur;tomcat:Tomcat;expert:Expert;stagiaire:Stagiaire;manager-gui:Manager-Gui;manager-script:Manager-Script'}";
    }
    
}
