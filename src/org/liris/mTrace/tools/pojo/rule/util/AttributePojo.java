/**.
 * 
 */
package org.liris.mTrace.tools.pojo.rule.util;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.liris.ktbs.domain.interfaces.IAttributeType;
import org.liris.mTrace.kTBS.SingleKtbs;

/**.
 * @author Dino
 * @10 mai 2011
 */
public class AttributePojo implements Serializable, Comparable<AttributePojo> {

    /**.
     * 
     */
    private static final long serialVersionUID = -62571345179096075L;
    private String uri;
    private String localName;    
    private String range;
    private String label;
    private Boolean nullable;
    
    /**.
     * 
     */
    @Deprecated
    public AttributePojo() {}
    
    public AttributePojo(String label, String attributeUri, String attributeRange, Boolean nullable){
	this();
	this.label = label;
	this.uri = attributeUri;
	if(uri.contains("#")){
		this.localName = StringUtils.substringAfterLast(attributeUri, "#");
	    }else{
		this.localName = StringUtils.substringAfterLast(attributeUri, "/");
	    }
	this.range = attributeRange;
	this.nullable = nullable;
    }
    
    public void setUri(String attributeUri) {
	this.uri = attributeUri;
    }
    public String getUri() {
	return uri;
    }
    public void setNullable(Boolean nullable) {
	this.nullable = nullable;
    }
    public Boolean getNullable() {
	return nullable;
    }
    public void setRange(String attributeRange) {
	this.range = attributeRange;
    }
    public String getRange() {
	if(range == null){
	    if(uri.endsWith("label")){
		range = "http://www.w3.org/2001/xmlschema#string";
	    }else if(uri.endsWith("date")){
		range = "http://www.w3.org/2001/xmlschema#datetime";
		
	    }
	}
	return range;
    }

    public void setLocalName(String attributeLocalName) {
	this.localName = attributeLocalName;
    }

    public String getLocalName() {
	if(uri != null && StringUtils.isEmpty(localName)){
	    if(uri.contains("#")){
		localName = StringUtils.substringAfterLast(uri, "#");
	    }else{
		localName = StringUtils.substringAfterLast(uri, "/");
	    }
	}
	return localName;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(AttributePojo o) {
	return this.getLocalName().compareTo(o.getLocalName());
    }

    public void setLabel(String label) {
	this.label = label;
    }

    public String getLabel() {
	if(StringUtils.isEmpty(label)){
	    try{
		IAttributeType attributeType = SingleKtbs.getInstance().getRoot().getResourceService().getResource(uri, IAttributeType.class);
		if(attributeType != null){
		    return attributeType.getLabel();
		}
	    }catch(Exception e){
		return getLocalName();
	    }
	    
	    return getLocalName();
	}
	return label;
    }

}
