/**.
 * 
 */
package org.liris.mTrace.tools;

/**.
 * @author Dino
 * @27 avr. 2011
 */
public enum EnumModelTraceObject {
    OBSEL_TYPE("obseltype"),METADATA("metadata"), RELATION_TYPE("relationtype"), UNDEFINED("undefined");

    protected String label;

    EnumModelTraceObject(String pLabel) {
       this.label = pLabel;
    }

    public String getLabel() {
       return this.label;
    }
    
    public static EnumModelTraceObject getEnumModelTraceObject(String objectName){
	if(OBSEL_TYPE.getLabel().equals(objectName.toLowerCase())){
	    return OBSEL_TYPE;
	}else if(METADATA.getLabel().equals(objectName.toLowerCase())){
	    return METADATA;
	}else if(RELATION_TYPE.getLabel().equals(objectName.toLowerCase())){
	    return RELATION_TYPE;
	}else{
	    return UNDEFINED;
	}
    }
}
