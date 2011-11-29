package org.liris.mTrace.tools.pojo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.liris.ktbs.domain.interfaces.IKtbsResource;
import org.liris.mTrace.MTraceConst;

public class RessourceKtbsPojo {

	protected static DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH'h'mm", Locale.FRANCE);
	
	private String uri;
	private String type;
	private String creationDate;
	private String localName;
	private String label;
	
	
	public RessourceKtbsPojo(IKtbsResource ktbsResource) {
		this.uri = ktbsResource.getUri();
		this.type = StringUtils.substringAfter(ktbsResource.getTypeUri(), "#");
		try{
			this.creationDate = MTraceConst.strDate(ktbsResource.getLocalName());
		}
		catch (Exception e) {}
		this.localName = ktbsResource.getLocalName();
		this.label = MTraceConst.getLabel(ktbsResource.getLabels());
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
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
	public String getLocalName() {
		return localName;
	}
	public void setLocalName(String localName) {
		this.localName = localName;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
}
