package org.liris.mTrace;


public abstract class AbstractApplication {

	private String urlServer;
	
	private AbstractApplication(){
		
	}
	
	public AbstractApplication(String urlServer) {
		this();
		setUrlServer(urlServer);
	}

	public void setUrlServer(String urlServer) {
		this.urlServer = urlServer;
	}

	public String getUrlServer() {
		return urlServer;
	}
		
}
