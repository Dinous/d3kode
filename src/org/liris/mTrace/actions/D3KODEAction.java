/**.
 * 
 */
package org.liris.mTrace.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.realm.GenericPrincipal;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.liris.ktbs.domain.interfaces.IBase;
import org.liris.ktbs.domain.interfaces.ITraceModel;
import org.liris.mTrace.MTraceConst;
import org.liris.mTrace.kTBS.SingleKtbs;
import org.liris.mTrace.tools.pojo.UserPojo;

import com.opensymphony.xwork2.ActionSupport;

/**.
 * @author Dino
 * @12 oct. 2011
 */
public abstract class D3KODEAction extends ActionSupport implements ServletRequestAware,SessionAware{

    protected HttpServletRequest request;
    private Map<String, Object> session;
    protected static DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH'h'mm", Locale.FRANCE);
    protected static DateFormat longdf = new SimpleDateFormat("dd-MM-yyyy HH'h'mm:ss.SSS", Locale.FRANCE);
    
    public String getUserLogin(){
        return ServletActionContext.getRequest().getUserPrincipal().getName();
    }
	
    public List<String> getRoleLogin(){
        List<String> ret = new ArrayList<String>();
        String[] roles = ((GenericPrincipal)ServletActionContext.getRequest().getUserPrincipal()).getRoles();
        for (String role : roles) {
        	ret.add(role);
		}         	
        return ret;
    }
    
    private boolean isRole(String r){
	for (String role : getRoleLogin()) {
	    if(role.equals(r)){
		return true;
	    }
	}
	return false;
    }
    
    public Boolean getIsExpert(){
	return isRole("expert");
    }
    
    public Boolean getIsStagiaire(){
	return isRole("stagiaire");
    }
    
    public String getDateStr(String longStr){
	String localName = longStr;
	if(localName.contains("_")){
	    localName = StringUtils.substringAfterLast(localName, "_");
	}
	Date date = new Date(Long.valueOf(localName));
	
	return df.format(date);
    }
    
    public String getDateStrFromDT(String dateDT) throws Exception{
	Date date = MTraceConst.strDateFromCSV(dateDT);
	return longdf.format(date);
    }

    public String getCreationDate(String localName){
	return MTraceConst.strDate(localName);
    }
    
    public String strDateFromkTBSDateDT(String dateDT){
	return MTraceConst.strDateFromkTBSDateDT(dateDT);
    }
    
    public String getLabel(Set<String> labels){
	if(labels != null){
	    for (String label : labels) {
		if(!label.toLowerCase().contains("subject")){
		    return label;
		}
	    }
	}else{
	    return "No label";    
	}
	return labels.toString();
    }
    
    public List<IBase> getBases(){
	List<IBase> bases = new ArrayList<IBase>();
	if(getIsStagiaire() && !getIsExpert()){
	        Set<IBase> bs = SingleKtbs.getInstance().getRoot().getResourceService().getRoot().getBases();
	        for (IBase iBase : bs) {
		    if(!iBase.getLocalName().equals("stagiaire")){
			bases.add(iBase);
		    }
		}
	}else{
	    bases.add(SingleKtbs.getInstance().getCurrentBase());
	}
    	return bases;
    }
    
    public IBase getCurrentBase(){
	return SingleKtbs.getInstance().getCurrentBase();
    }
    
    public Set<ITraceModel> getTraceModels(){
	if(getIsStagiaire() && !getIsExpert()){
	    return SingleKtbs.getInstance().getListTraceModelFull();
	}else{
	    return SingleKtbs.getInstance().getListTraceModel();
	}
	
    }
    
    @Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public List<UserPojo> getUserPojos() {
		return (List<UserPojo>)this.session.get("usersRoles");
	}

	public void setUserPojos(List<UserPojo> userPojos) {
		this.session.put("usersRoles", userPojos);
	}
	 @Override
	    public void setSession(Map<String, Object> session) {
		this.session = session;
	    }

	    public Map<String, Object> getSession(){
		return this.session;
	    }
}
