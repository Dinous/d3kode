package org.liris.mTrace.tag;

import java.io.IOException;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class TimeTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2827110715420491938L;
	public static final String START_PARAM = "startPageTime";
	 
	@Override
	public int doStartTag() throws JspException {
		long end = new Date().getTime();
		long start = 0;
		
		try {
			Date startParam = (Date) this.pageContext.getRequest().getAttribute(START_PARAM);
			if (startParam != null) {
				start = startParam.getTime();
				this.pageContext.getOut().print((end - start) + " ms");
			} else {
				// au choix...
				this.pageContext.getOut().print("erreur");
			}
		} catch (ClassCastException e) {
			// au choix
			// => prévenir l'utilisateur : throw new JspException(e);
			// => ne pas prévenir l'utilisateur : ne rien faire.
		} catch (IOException e) {
			throw new JspException(e);
		}
		
		return SKIP_BODY;
	}
}