/**.
 * 
 */
package org.liris.mTrace.tools.exception;

/**.
 * @author Dino
 * @23 mai 2011
 */
public class DateWrongFormatException extends Exception {

    /**.
     * 
     */
    private static final long serialVersionUID = -4786646098765865489L;
    private String dateStr;
    private String format;
    private String fieldname;
    
    /**.
     * 
     */
    public DateWrongFormatException(String dateStr, String format, String fieldName) {
	this.dateStr = dateStr;
	this.format = format;
	this.setFieldname(fieldName);
    }

    public void setFormat(String format) {
	this.format = format;
    }

    public String getFormat() {
	return format;
    }

    public void setDateStr(String dateStr) {
	this.dateStr = dateStr;
    }

    public String getDateStr() {
	return dateStr;
    }

    public void setFieldname(String fieldname) {
	this.fieldname = fieldname;
    }

    public String getFieldname() {
	return fieldname;
    }
}
