/**.
 * 
 */
package org.liris.mTrace.tools.pojo;

import java.io.Serializable;

import org.apache.commons.lang.xwork.StringUtils;

/**.
 * @author Dino
 * @2 mai 2011
 */
public class VarPojo implements Serializable{

    /**.
     * 
     */
    private static final long serialVersionUID = -7208941344147587428L;
    public enum VAR_TYPE{CONSTRUCT, WHERE}
    
    private String uri;
    private String uriLocalName;
    private VAR_TYPE varType;

    /**.
     * 
     */
    private VarPojo() {}
    
    public VarPojo(VAR_TYPE type,String mUri){
	this();
	this.setVarType(type);
	this.uri = mUri;
	this.uriLocalName = (StringUtils.substringAfterLast(uri, "/"));
    }
    
    public void setUri(String uri) {
	this.uri = uri;
    }

    public String getUri() {
	return uri;
    }
    
    public void setVarType(VAR_TYPE varType) {
	this.varType = varType;
    }

    public String getVarType() {
	return varType.toString();
    }
    public String getUriLocalName(){
	return uriLocalName;
    }

    public void setUriLocalName(String uriLocalName) {
	this.uriLocalName = uriLocalName;
    }
}
