/**.
 * 
 */
package org.liris.mTrace.tools;


/**.
 * @author Dino
 * @13 mai 2011
 */
public enum EnumOperator {
    
    OPERATOR_OPEN_PARENTHESIS("("),OPERATOR_SUPER(">"),OPERATOR_INFER("<"),
    OPERATOR_SUPER_OR_EQUAL(">="), OPERATOR_INFER_OR_EQUAL("<="),
    OPERATOR_EQUAL("="), OPERATOR_NOTEQUAL("!="),
    OPERATOR_PLUS("+"), OPERATOR_MINUS("-"),
    OPERATOR_DIV("/"), OPERATOR_TIMES("*"),
    OPERATOR_MIN("Min("),OPERATOR_MAX("Max("),OPERATOR_COMMA(","),
    OPERATOR_CLOSING_PARENTHESIS(")");
    

    protected String label;

    EnumOperator(String pLabel) {
       this.label = pLabel;
    }

    public String getLabel() {
       return this.label;
    }

    public static boolean isSpecial(String objectName){
	EnumOperator enumO = getEnumOperator(objectName);
	switch (enumO) {
		case OPERATOR_CLOSING_PARENTHESIS:
		    return true;
		case OPERATOR_COMMA:
		    return true;
		case OPERATOR_MAX:
		    return true;
		case OPERATOR_MIN:
		    return true;
		case OPERATOR_OPEN_PARENTHESIS:
		    return true;
		default:
		    return false;
	}
    }
    
    public static EnumOperator getEnumOperator(String objectName){
	if(OPERATOR_PLUS.getLabel().equals(objectName.toLowerCase())){
	    return OPERATOR_PLUS;
	}else if(OPERATOR_MINUS.getLabel().equals(objectName.toLowerCase())){
	    return OPERATOR_MINUS;
	}else if(OPERATOR_DIV.getLabel().equals(objectName.toLowerCase())){
	    return OPERATOR_DIV;
	}else if(OPERATOR_TIMES.getLabel().equals(objectName.toLowerCase())){
	    return OPERATOR_TIMES;
	}else if(OPERATOR_SUPER.getLabel().equals(objectName.toLowerCase())){
	    return OPERATOR_SUPER;
	}else if(OPERATOR_INFER.getLabel().equals(objectName.toLowerCase())){
	    return OPERATOR_INFER;
	}else if(OPERATOR_SUPER_OR_EQUAL.getLabel().equals(objectName.toLowerCase())){
	    return OPERATOR_SUPER_OR_EQUAL;
	}else if(OPERATOR_INFER_OR_EQUAL.getLabel().equals(objectName.toLowerCase())){
	    return OPERATOR_INFER_OR_EQUAL;
	}else if(OPERATOR_EQUAL.getLabel().equals(objectName.toLowerCase())){
	    return OPERATOR_EQUAL;
	}else if(OPERATOR_NOTEQUAL.getLabel().equals(objectName.toLowerCase())){
	    return OPERATOR_NOTEQUAL;
	}else if(OPERATOR_OPEN_PARENTHESIS.getLabel().equals(objectName.toLowerCase())){
	    return OPERATOR_OPEN_PARENTHESIS;
	}else if(OPERATOR_MIN.getLabel().equals(objectName.toLowerCase())){
	    return OPERATOR_MIN;
	}else if(OPERATOR_MAX.getLabel().equals(objectName.toLowerCase())){
	    return OPERATOR_MAX;
	}else if(OPERATOR_COMMA.getLabel().equals(objectName.toLowerCase())){
	    return OPERATOR_COMMA;
	}else{
	    return OPERATOR_CLOSING_PARENTHESIS;
	}
    }
}
