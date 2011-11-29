/**.
 * 
 */
package org.liris.mTrace.tools.exception;


/**.
 * @author Dino
 * @23 mai 2011
 */
public class AttributeTypeUnknowException extends Exception {

    /**.
     * 
     */
    private static final long serialVersionUID = -2982430323844393906L;
    private String traceModel;
    private String obselType;
    private String attributeType;
    
    /**.
     * 
     */
    public AttributeTypeUnknowException(String traceModel,String obselType,String attributeType) {
	this.setTraceModel(traceModel);
	this.setObselType(obselType);
	this.setAttributeType(attributeType);
    }

    public void setObselType(String obselType) {
	this.obselType = obselType;
    }

    public String getObselType() {
	return obselType;
    }

    public void setTraceModel(String traceModel) {
	this.traceModel = traceModel;
    }

    public String getTraceModel() {
	return traceModel;
    }

    public void setAttributeType(String attributeType) {
	this.attributeType = attributeType;
    }

    public String getAttributeType() {
	return attributeType;
    }
}
