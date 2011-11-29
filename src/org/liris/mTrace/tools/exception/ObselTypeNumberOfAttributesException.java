/**.
 * 
 */
package org.liris.mTrace.tools.exception;

import java.util.List;

import org.liris.ktbs.domain.interfaces.IAttributeType;


/**.
 * @author Dino
 * @23 mai 2011
 */
public class ObselTypeNumberOfAttributesException extends Exception {

    /**.
     * 
     */
    private static final long serialVersionUID = -2519854044991980563L;
    private String[] attributeInFile;
    private List<IAttributeType> attributeInModel;
    private String obselType;

    /**.
     * 
     */
    public ObselTypeNumberOfAttributesException(String obselType, String[] cols, List<IAttributeType> list) {
	this.setAttributeInFile(cols);
	this.setAttributeInModel(list);
	this.setObselType(obselType);
    }

    public void setObselType(String obselType) {
	this.obselType = obselType;
    }

    public String getObselType() {
	return obselType;
    }

    public void setAttributeInFile(String[] attributeInFile) {
	this.attributeInFile = attributeInFile;
    }

    public String[] getAttributeInFile() {
	return attributeInFile;
    }

    public void setAttributeInModel(List<IAttributeType> attributeInModel) {
	this.attributeInModel = attributeInModel;
    }

    public List<IAttributeType> getAttributeInModel() {
	return attributeInModel;
    }

    /**.
     * @return
     */
    public String getOverAttribute() {
	String ret = "";
	boolean found = false;
	for (int i = 0; i < attributeInFile.length; i++) {
	    String attributeNameInFile = attributeInFile[i];
	    if(!attributeNameInFile.equals("") && !attributeNameInFile.equals(obselType)){
	    for (IAttributeType attribute : attributeInModel) {
		if(attribute.getLabel() != null && attribute.getLabel().equals(attributeNameInFile)){
		    found = true;
		    break;
		}
	    }
	    if(!found){
		ret = ret + attributeNameInFile + " ";
	    }else{
		found = false;
	    }
	    }
	}
	
	
	return ret;
    }

}
