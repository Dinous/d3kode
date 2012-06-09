package org.liris.mTrace.tools.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserPojo implements Comparable<UserPojo>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2360789063339784430L;
	private Integer id;
	private String login;
	private String password;
	private List<String> roles = new ArrayList<String>();
	
	private UserPojo() {
	}
	
	public UserPojo(String login, String password) {
		this();
		this.login = login;
		this.password = password;
	}
	
	public void addRole(String role){
		roles.add(role);
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	@Override
	public int compareTo(UserPojo o) {
		return getLogin().toLowerCase().compareTo(o.getLogin().toLowerCase());
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
