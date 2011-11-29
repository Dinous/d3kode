package org.liris.mTrace.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class XpathExecutor {

	
	public static String execute(String pathFileSource, String xpathRequete) throws Exception{
		Document document = Utils.fileToDom(pathFileSource);
		Node obsel = XPathProcessor.getInstance().EvaluateNode(xpathRequete, document);
		String result = Utils.nodeToString(obsel); 
		return result.substring(result.indexOf(">")+1);
		
	}
}
