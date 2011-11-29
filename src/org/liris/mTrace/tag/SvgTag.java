package org.liris.mTrace.tag;

import javax.servlet.jsp.JspException;

import org.apache.struts2.views.jsp.StrutsBodyTagSupport;
import org.liris.mTrace.xml.Utils;

public class SvgTag extends StrutsBodyTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2827110715420491938L;
	 
	private String path;
	
	
	@Override
	public int doStartTag() throws JspException {
		
		
		
		try {
			String ret = Utils.xmlFileToStringWithoutXmlDeclaration(findValue(path).toString());
			if (ret != null) {
				this.pageContext.getOut().print(ret);
			} else {
				// au choix...
				this.pageContext.getOut().print("erreur");
			}
		} catch (ClassCastException e) {
			// au choix
			// => prévenir l'utilisateur : throw new JspException(e);
			// => ne pas prévenir l'utilisateur : ne rien faire.
		} catch (Exception e) {
			throw new JspException(e);
		} 
		
		return SKIP_BODY;
	}
	
	public void setPath(String path){
		this.path = path;
	}
}