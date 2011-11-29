/**.
 * 
 */
package org.liris.mTrace.tools;

/**.
 * @author Dino
 * @27 avr. 2011
 */
public enum EnumXSDType {
    STRING("string"),DATETIME("datetime"), INTEGER("integer"), DECIMAL("decimal"),
    UNDEFINED("undefined");

    protected String label;

    EnumXSDType(String pLabel) {
       this.label = pLabel;
    }

    public String getLabel() {
       return this.label;
    }
    
    public static EnumXSDType getEnumModelTraceObject(String typeName){
	if(STRING.getLabel().equals(typeName.toLowerCase())){
	    return STRING;
	}else if(DATETIME.getLabel().equals(typeName.toLowerCase())){
	    return DATETIME;
	}else if(INTEGER.getLabel().equals(typeName.toLowerCase())){
	    return INTEGER;
	}else if(DECIMAL.getLabel().equals(typeName.toLowerCase())){
	    return DECIMAL;
	}else{
	    return UNDEFINED;
	}
    }
}
