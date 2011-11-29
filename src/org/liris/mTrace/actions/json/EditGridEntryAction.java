package org.liris.mTrace.actions.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.liris.mTrace.actions.D3KODEAction;
import org.liris.mTrace.hqldb.ManageDB;
import org.liris.mTrace.tools.pojo.UserPojo;

@ParentPackage(value="mTrace")
public class EditGridEntryAction extends D3KODEAction {

	private String              oper             = "";
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String              id;
	private String              login;
	private String              password;
	private String              roles;
	private Map<String, Object> session;
	private List<UserPojo>      myCustomers;

	  
	@Action(value = "/editGridEntry",
		    results = { @Result(location = "empty.jsp", name = "success") })
	@Override
	  public String execute() throws Exception{
	    

	    Object list = session.get("usersRoles");
	    if (list != null){
	    	myCustomers = (List<UserPojo>) list;
	    }else{
	    	myCustomers = ManageDB.buildList();
	    }

	    UserPojo user;

	    if (oper.equalsIgnoreCase("add")){
	      user = new UserPojo(login, password);
	      user.setRoles(Arrays.asList(roles));
	      user.setId(ManageDB.addUser(user));
	      myCustomers.add(user);
	    }
	    else if (oper.equalsIgnoreCase("edit")){
	    	StringTokenizer ids = new StringTokenizer(id, ",");
	    	int editId = Integer.parseInt(ids.nextToken());
	        user = searchUser(myCustomers,editId);
	        myCustomers.remove(user);
	        user = new UserPojo(login, password);
	        user.setRoles(Arrays.asList(roles.split(",")));
	        user.setId(editId);
	        ManageDB.editUser(user);
	        myCustomers.add(user);
	      
	    }
	    else if (oper.equalsIgnoreCase("del")){
	      StringTokenizer ids = new StringTokenizer(id, ",");
	      while (ids.hasMoreTokens())
	      {
	        int removeId = Integer.parseInt(ids.nextToken());
	        user = searchUser(myCustomers,removeId);
	        ManageDB.delUser(user);
	        myCustomers.remove(user);
	      }
	    }

	    session.put("usersRoles", myCustomers);

	    return SUCCESS;
	  }

	private UserPojo searchUser(List<UserPojo> users, Integer id){
		UserPojo userPojo = null;
		for (UserPojo user : users) {
			if(user.getId() == id){
				userPojo = user;
			}
		}
		return userPojo;
	}
	
	  public String getId(){
	    return id;
	  }

	  public void setId(String id){
	    this.id = id;
	  }

	  public void setSession(Map<String, Object> session){
	    this.session = session;
	  }

	  public void setOper(String oper){
	    this.oper = oper;
	  }

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
