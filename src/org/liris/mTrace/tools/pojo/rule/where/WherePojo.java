/**.
 * 
 */
package org.liris.mTrace.tools.pojo.rule.where;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.liris.ktbs.domain.interfaces.IAttributeType;
import org.liris.mTrace.tools.pojo.rule.util.AttributePojo;

/**.
 * @author Dino
 * @10 mai 2011
 */
public class WherePojo implements Serializable{

    /**.
     * 
     */
    private static final long serialVersionUID = -613192060667143287L;
    private String localName;
    private String uri;
    private String label;
    private String typeFromKtbs;
    private List<AttributePojo> listAttribute;
    
    public String getLocalName() {
        return localName;
    }
    public void setLocalName(String localName) {
        this.localName = localName;
    }
    public String getTypeFromKtbs() {
        return typeFromKtbs;
    }
    public void setTypeFromKtbs(String typeFromKtbs) {
        this.typeFromKtbs = typeFromKtbs;
    }
    public List<AttributePojo> getListAttribute() {
    	Collections.sort(listAttribute, new Comparator<AttributePojo>() {

			@Override
			public int compare(AttributePojo o1, AttributePojo o2) {
				if(o1.getLabel() != null && o1.getLabel() != null){
					return o1.getLabel().toUpperCase().compareTo(o2.getLabel().toUpperCase());	
				}else{
					return 1;
				}
				
			}
		} );
        return listAttribute;
    }
    
    public void setListAttributePojoFromKtbs(List<IAttributeType> listIAttributeType) {
	List<AttributePojo> listAttributePojo = new ArrayList<AttributePojo>();
        for (IAttributeType iAttributeType : listIAttributeType) {
            AttributePojo attributePojo = new AttributePojo(
        	    iAttributeType.getLabel(), 
        	    iAttributeType.getUri(), 
        	    iAttributeType.getRanges().toArray()[0].toString(),
        	    true);
            listAttributePojo.add(attributePojo);
	}
        Collections.sort(listAttributePojo);
        setListAttribute(listAttributePojo);
    }
    
    public void setListAttribute(List<AttributePojo> listAttributePojo) {
        this.listAttribute = listAttributePojo;
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
