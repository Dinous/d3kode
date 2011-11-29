package org.liris.mTrace.actions;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.liris.ktbs.domain.interfaces.IBase;
import org.liris.mTrace.kTBS.SingleKtbs;

import com.opensymphony.xwork2.ActionSupport;

public class RealAccueilAction extends D3KODEAction {
    /**
	 * 
	 */
    private static final long serialVersionUID = -14874469912477972L;

    @Override
    @Action(value = "/realAccueil", results = { @Result(location = "accueil.jsp", name = "success") })
    public String execute() {
	try {
	    SingleKtbs.getInstance().initialize(null);
	    if (SingleKtbs.getInstance().getCurrentBase() == null) {
		addActionError(getText("base.not.found"));
	    }
	} catch (Exception e) {
	    addActionError(getText("server.not.found"));
	}
	return SUCCESS;
    }

    public IBase getCurrentBase() {
	return SingleKtbs.getInstance().getCurrentBase();
    }
}
