/**.
 * 
 */
package org.liris.mTrace.tools.pojo.rule.construct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.liris.ktbs.domain.interfaces.IAttributeType;
import org.liris.ktbs.domain.interfaces.ITraceModel;
import org.liris.mTrace.kTBS.SingleKtbs;
import org.liris.mTrace.tools.pojo.rule.util.AttributePojo;
import org.liris.mTrace.tools.pojo.rule.util.OperandePojo;
import org.liris.mTrace.tools.pojo.rule.util.OperatorOrOperandeInterface;

/**.
 * @author Dino
 * @10 mai 2011
 */
public class ConstructPojo implements Serializable {
    
    /**.
     * 
     */
    private static final long serialVersionUID = 3880800426634085151L;
    private String uri;
    private String localName;
    private String label;
    private String typeFromKtbs;
    private List<ConstructItemPojo> listConstructItem;
    
    
    public String getLocalName() {
	if(uri != null && StringUtils.isEmpty(localName)){
	    localName = StringUtils.substringAfterLast(uri, "/");
	}
        return localName;
    }
    public void setLocalName(String localName) {
        this.localName = localName;
    }
    public String getTypeFromKtbs() {
        return typeFromKtbs;
    }
    public void setTypeFromKtbs(ITraceModel targetModel, String typeFromKtbs) {
	List<IAttributeType> listAttribute = SingleKtbs.getInstance()
		.getListAttributeTypeOfObselType(targetModel, typeFromKtbs);
	if (listConstructItem == null) {
	    listConstructItem = new ArrayList<ConstructItemPojo>();
	}
	for (IAttributeType iAttributeType : listAttribute) {
	    List<OperatorOrOperandeInterface> defaultOperande = new ArrayList<OperatorOrOperandeInterface>();
	    defaultOperande.add(new OperandePojo(null,iAttributeType.getRanges()));
	    listConstructItem
		    .add(new ConstructItemPojo(new AttributePojo(
			    iAttributeType.getLabel(),
			    iAttributeType.getUri(),
			    iAttributeType.getRanges().toArray()[0].toString(), true), defaultOperande));
	}
	//Collections.sort(listConstructItem);
        this.typeFromKtbs = typeFromKtbs;
    }
    public List<ConstructItemPojo> getListConstructItem() {
        return listConstructItem;
    }
    public void setListConstructItem(List<ConstructItemPojo> listConstructItem) {
        this.listConstructItem = listConstructItem;
    }
    public void setUri(String uri) {
	this.uri = uri;
    }
    public String getUri() {
	return uri;
    }
    public void setLabel(String label) {
	this.label = label;
    }
    public String getLabel() {
	return label;
    }
    
    
}
