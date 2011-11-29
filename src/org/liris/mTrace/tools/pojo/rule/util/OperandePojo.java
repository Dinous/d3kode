/**.
 * 
 */
package org.liris.mTrace.tools.pojo.rule.util;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;
import org.liris.ktbs.domain.interfaces.IObselType;
import org.liris.ktbs.domain.interfaces.IUriResource;
import org.liris.ktbs.utils.KtbsUtils;
import org.liris.mTrace.kTBS.SingleKtbs;
import org.liris.mTrace.tools.EnumXSDType;

/**.
 * @author Dino
 * @10 mai 2011
 */
public class OperandePojo implements OperatorOrOperandeInterface, Serializable {
    /**.
     * 
     */
    private static final long serialVersionUID = 8614521417355821517L;
    private String uri;
    private String label;
    private String localName;
    private EnumXSDType range;
    private AttributePojo attribute;
    
    /**.
     * 
     */
    @Deprecated
    public OperandePojo() {}
    
    public OperandePojo(String label, String uri){
	this();
	this.label = label;
	this.uri = uri;
    }
    public OperandePojo(String label, String operande, AttributePojo attribute){
	this(label, operande);
	this.attribute = attribute;
    }
    public OperandePojo(String label,String operandeUri,String attributeLabel, String attribute, String attributeRange, Boolean nullable){
	this(label,operandeUri, new AttributePojo(attributeLabel,attribute, attributeRange, nullable));
    }
    
    /**.
     * @param object
     * @param string
     * @param ranges
     */
    public OperandePojo(String label, Set<IUriResource> ranges) {
	this.label = label;
	if(ranges != null && ranges.size() > 0){
	    EnumXSDType enumXSDType = EnumXSDType.getEnumModelTraceObject(StringUtils.substringAfter(ranges.toArray()[0].toString(), "#"));
	    switch (enumXSDType) {
	    case DATETIME:
		this.uri = KtbsUtils.now();
		break;
	    case DECIMAL:
		this.uri = "0.0";
		break;
	    case INTEGER:
		this.uri = "0";
		break;
		
	    default:
		this.uri = "\"NC\"";
		break;
	    }
	}else{
	    this.uri = "\"NC\"";
	}
	
    }

    public void setUri(String operande) {
	this.uri = operande;
    }
    public String getUri() {
	return uri;
    }

    public void setAttribute(AttributePojo attributePojo) {
	this.attribute = attributePojo;
    }

    public AttributePojo getAttribute() {
	return attribute;
    }

    public void setLocalName(String localName) {
	this.localName = localName;
    }

    public String getLocalName() {
	if(uri != null && StringUtils.isEmpty(localName)){
	    if(uri.contains("/")){
		localName = StringUtils.substringAfterLast(uri, "/");
	    }else{
		localName = uri;
	    }
	}
	return localName;
    }
    
    @Override
    public String toString(){
	String ret = getLabel();
	if(attribute != null ){
	    if(!StringUtils.isEmpty(attribute.getLabel())){
		ret=ret.concat(".").concat(attribute.getLabel());
	    }else{
		if(!StringUtils.isEmpty(attribute.getLocalName())){
		    ret=ret.concat(".").concat(attribute.getLocalName());
		}else{
		    ret=ret.concat(".").concat(attribute.getUri());
		}
	    }
	}
	return ret;
    }

    public void setLabel(String label) {
	this.label = label;
    }

    public String getLabel() {
	if(StringUtils.isEmpty(label)){
	    if(!StringUtils.isEmpty(uri)){
		if(uri.contains("_")){
		String obselTypeUri = StringUtils.substringBeforeLast( uri, "_");
		IObselType obselType = SingleKtbs.getInstance().getRoot().getResourceService().getResource(obselTypeUri, IObselType.class);
		if(obselType != null && !StringUtils.isEmpty(obselType.getLabel())){
		    return obselType.getLabel()+"_"+StringUtils.substringAfterLast( uri, "_");
		}
		}else{
		    return uri;
		}
	    }
	    return getLocalName();
	}
	return label;
    }

    @Override
    public String toStringSparql() {
	String ret = getLocalName();
	if(attribute != null ){
		if(!StringUtils.isEmpty(attribute.getLocalName())){
		    ret=ret.concat(".").concat(attribute.getLocalName());
		}
	}
	return ret;
    }

    public void setRange(EnumXSDType range) {
	this.range = range;
    }

    public EnumXSDType getRange() {
	return range;
    }
}
