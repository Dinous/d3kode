package org.liris.mTrace.actions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class LogoutAction extends ActionSupport implements ServletRequestAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7096891524951378688L;
	private HttpServletRequest request;

	@Override
	public String execute(){
		try {
			request.logout();
		} catch (ServletException e) {
		}
		return LOGIN;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}
