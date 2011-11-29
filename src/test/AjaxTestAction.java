package test;

import com.opensymphony.xwork2.ActionSupport;

public class AjaxTestAction extends ActionSupport {
	
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -5259698853096111918L;
	private static int counter = 0;
	    private String data;

	    public long getServerTime() {
	        return System.currentTimeMillis();
	    }

	    public int getCount() {
	        return ++counter;
	    }

	    public String getData() {
	        return data;
	    }

	    public void setData(String data) {
	        this.data = data;
	    }

	    @Override
		public String execute() throws Exception {
	        return SUCCESS;
	    }
}
