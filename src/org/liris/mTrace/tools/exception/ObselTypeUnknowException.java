/**.
 * 
 */
package org.liris.mTrace.tools.exception;


/**.
 * @author Dino
 * @23 mai 2011
 */
public class ObselTypeUnknowException extends Exception {

    /**.
     * 
     */
    private static final long serialVersionUID = -2519854044991980563L;
    private String traceModel;
    private String obselType;

    /**.
     * 
     */
    public ObselTypeUnknowException(String obselType, String traceModel) {
	this.setTraceModel(traceModel);
	this.setObselType(obselType);
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
}
