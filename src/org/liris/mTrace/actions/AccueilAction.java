package org.liris.mTrace.actions;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Role;
import org.apache.catalina.users.MemoryUser;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.liris.mTrace.MTraceConst;
import org.liris.mTrace.WebApplication;
import org.liris.mTrace.hqldb.ManageDB;
import org.liris.mTrace.kTBS.SingleKtbs;
import org.liris.mTrace.tools.pojo.UserPojo;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "mTrace")
public class AccueilAction extends D3KODEAction implements ServletRequestAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = -14874469912477972L;
	private HttpServletRequest request;
	
	
	
	/*<action name="accueil" class="org.liris.mTrace.actions.AccueilAction">
        <result name="success" type="tiles">accueil</result>
  </action>*/
	@Action(value = "/accueil", results = {
		    @Result(location="layout/baseLayout.jsp",name = "success")
		  })
	@Override
	public String execute() {
	    //System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");
	    File transformationFile = new File(MTraceConst.WORK_TRANSFORMATION_PATH);
		transformationFile.mkdirs();
		File methodFile = new File(MTraceConst.WORK_METHOD_CONDITION_PATH);
		methodFile.mkdirs();
		methodFile = new File(MTraceConst.WORK_METHOD_CONSTRUCTION_PATH);
		methodFile.mkdirs();
		File folderCsv = new File(MTraceConst.WORK_CSV_PATH);
		folderCsv.mkdirs();
		File folderXml = new File(MTraceConst.WORK_XML_PATH + request.getUserPrincipal().getName());
		folderXml.mkdirs();
		File folderSvg = new File(MTraceConst.WORK_SVG_PATH + request.getUserPrincipal().getName());
		folderSvg.mkdirs();
		
		File folderXsl = new File(MTraceConst.WORK_XSL_PATH);
		folderXsl.mkdirs();
		try {
			
			FileUtils.copyDirectory(new File(request.getServletContext().getRealPath("work/xsl")), folderXsl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SingleKtbs.getInstance().initialize(new WebApplication("http://localhost:8001/"));
		
		ManageDB manageDB = new ManageDB();
		manageDB.connexionDB();
		
		String userTmp = null;
		UserPojo userPojo = null;
		List<UserPojo> userPojos = new ArrayList<UserPojo>();
		try {
			ResultSet resultSet = manageDB.userRoles();
			while (resultSet.next()) {
				String idUser = resultSet.getString("IDUSER");
				String currentUser = resultSet.getString("LOGIN");
				String currentPassword = resultSet.getString("PASSWORD");
				String currentRole = resultSet.getString("ROLE_NAME");
				
				if(userTmp == null){
					userPojo = new UserPojo(currentUser, currentPassword);
					userPojo.setId(Integer.parseInt(idUser));
					userTmp = currentUser;
					userPojos.add(userPojo);
				}else if(!userTmp.equals(currentUser) || resultSet.isLast()){
					userPojo = new UserPojo(currentUser, currentPassword);
					userPojo.setId(Integer.parseInt(idUser));
					userTmp = currentUser;
					userPojos.add(userPojo);
				}
				userPojo.addRole(currentRole);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		setUserPojos(userPojos);
		
//		try {
//			manageDB.arretDB();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return SUCCESS;
	}        
	
	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void setServletRequest(HttpServletRequest request) {
	    this.request = request;
	}
}
