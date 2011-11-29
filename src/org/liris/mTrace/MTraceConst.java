package org.liris.mTrace;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.liris.ktbs.domain.interfaces.IAttributeType;
import org.liris.ktbs.domain.interfaces.IBase;
import org.liris.ktbs.domain.interfaces.IKtbsResource;
import org.liris.ktbs.domain.interfaces.IMethod;
import org.liris.ktbs.domain.interfaces.IObselType;
import org.liris.ktbs.domain.interfaces.ITrace;
import org.liris.ktbs.domain.interfaces.ITraceModel;
import org.liris.mTrace.kTBS.SingleKtbs;

public final class MTraceConst {

	private static final String WORK_PATH = FileUtils.getUserDirectoryPath()+File.separator + "work"+ File.separator;
	
	public static final String WORK_TRANSFORMATION_PATH = WORK_PATH+"transformation"+File.separator;
	private static final String WORK_METHOD_PATH = WORK_PATH+"methods"+File.separator;
	public static final String WORK_METHOD_CONSTRUCTION_PATH = WORK_METHOD_PATH+"construct"+File.separator;
	public static final String WORK_METHOD_CONDITION_PATH = WORK_METHOD_PATH+"where"+File.separator;
	
	public static final String WORK_CSV_PATH = WORK_PATH+"csv"+File.separator;
	
	public static final String WORK_XML_PATH = WORK_PATH+"xml"+File.separator;

	public static final String WORK_SVG_PATH = WORK_PATH+"svg"+File.separator;
	
	public static final String WORK_XSL_PATH = WORK_PATH+"xsl"+File.separator;

	private static final String SPARQL_NOT_EXISTS = "NOT EXISTS";
	public static final String SPARQL_NOT_EXISTS_OPEN = SPARQL_NOT_EXISTS + "[";
	public static final String SPARQL_NOT_EXISTS_CLOSE = "]"+SPARQL_NOT_EXISTS;
	
	
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
	    "EEE, d MMM yyyy HH:mm:ss,SSS");
	public static SimpleDateFormat simpleDateFormatFromkTBS = new SimpleDateFormat(
	    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	public static SimpleDateFormat simpleDateFormatFromCSV = new SimpleDateFormat(
	"yyyy/dd/MM HH:mm:ss.SSS'Z'");

	/**.
	 * Transformation d'un localName en date de création au format String
	 * @param localName
	 * @return
	 */
	public static String strDate(String localName) {
	    Date date = date(localName);
	    return simpleDateFormat.format(date);
	}

	/**.
	 * Transformation d'un localName en date de création
	 * @param localName
	 * @return
	 */
	public static Date date(String localName) {
	    Date date = null;
	    if(!localName.contains("_")){
		date = new Date(Long.valueOf(localName));
	    }else{
		date = new Date(Long.valueOf(StringUtils.substringAfterLast(localName, "_")));
	    }
	    return date;
	}
	
	/**.
	 * Transformation d'une date DT du ktbs en date EEE, d MMM yyyy HH:mm:ss
	 * @param dateDT
	 * @return
	 */
	public static String dateFromkTBSDateDT(Date date) {
	    String dateRet = "";
//	    if(!dateDT.contains(".")){
//		dateDT = dateDT.replaceFirst("Z", ".000Z");
//	    }
	    try{
	    	dateRet = simpleDateFormat.format(date);
	    }catch (Exception e) {
	    	System.out.println(e.getMessage());
	    }
	    
	    return dateRet;
	}
	
	/**.
	 * Transformation d'une date DT du ktbs en date EEE, d MMM yyyy HH:mm:ss
	 * @param dateDT
	 * @return
	 */
	public static String strDateFromkTBSDateDT(String dateDT) {
	    String dateRet = "";
//	    if(!dateDT.contains(".")){
//		dateDT = dateDT.replaceFirst("Z", ".000Z");
//	    }
	    try{
		Date date = simpleDateFormatFromkTBS.parse(dateDT);
		dateRet = simpleDateFormat.format(date);
	    }catch (Exception e) {
		System.out.println(e.getMessage());
	    }
	    
	    return dateRet;
	}
	
	/**.
	 * Transformation d'une date DT du ktbs en date EEE, d MMM yyyy HH:mm:ss
	 * @param dateDT
	 * @return
	 */
	public static Date dateFromkTBSDateDT(String dateDT) {
		Date date = null;
//	    if(!dateDT.contains(".")){
//		dateDT = dateDT.replaceFirst("Z", ".000Z");
//	    }
	    try{
	    	date = simpleDateFormatFromkTBS.parse(dateDT);
	    }catch (Exception e) {
	    	System.err.println("dateFromkTBSDateDT "+dateDT);
	    	System.out.println(e.getMessage());
	    }
	    
	    return date;
	}
	
	/**.
	 * Transformation d'une date CSV yyyy/dd/MM HH:mm:ss'Z' au format kTBS yyyy-MM-dd'T'HH:mm:ss'Z'
	 * @param beginDT
	 * @return
	 * @throws ParseException 
	 */
	public static Date strDateFromCSV(String dateDT) throws ParseException {
//	    if(dateDT.contains(".")){
//		dateDT = StringUtils.substringBefore(dateDT, ".");
//	    }
	    if(!dateDT.contains("Z")){
		dateDT = dateDT + "Z";
	    }
	    if(!dateDT.contains("T")){
		dateDT = new GregorianCalendar().get(Calendar.YEAR) + "/"+dateDT;
		dateDT = simpleDateFormatFromkTBS.format(simpleDateFormatFromCSV.parse(dateDT));
	    }
	    //System.out.println(dateDT);
	    return simpleDateFormatFromkTBS.parse(dateDT);
	}
	
	public static String getLabel(Set<String> labels){
		if(labels != null){
		    for (String label : labels) {
			if(!label.toLowerCase().contains("subject")){
			    return label;
			}
		    }
		}else{
		    return "No label";    
		}
		return labels.toString();
	    }
	
	public static void deleteResource(String uri){
    	if (!StringUtils.isEmpty(uri)) {
    	    
    	    IKtbsResource ktbsResource;
    	    try {
    		ktbsResource = SingleKtbs.getInstance().getRoot()
    		    .getResourceService().getResource(uri);
    		if (ktbsResource instanceof IBase
    		    || ktbsResource instanceof ITraceModel
    		    || ktbsResource instanceof ITrace
    		    || ktbsResource instanceof IMethod) {
    		SingleKtbs.getInstance().getRoot().getResourceService()
    			.deleteResource(ktbsResource);
    		
    		if(ktbsResource instanceof IMethod){
    		    File transformation = new File(MTraceConst.WORK_TRANSFORMATION_PATH+ktbsResource.getLocalName()); 
    		    if(transformation.exists()){
    			transformation.delete();
    		    }
    		}
    		
    		} else if (ktbsResource instanceof IAttributeType
    		    || ktbsResource instanceof IObselType) {
    		ITraceModel traceModel = ((ITraceModel) ktbsResource.getParentResource());
    		traceModel.delete(ktbsResource.getUri());
    		SingleKtbs.getInstance().getRoot().getTraceModelService().save(traceModel);
    		}
    	    } catch (Exception e) {
    		SingleKtbs.getInstance().getRoot().getResourceService().deleteResource(uri);
    		e.printStackTrace();
    	    }

    	    SingleKtbs.getInstance().refresh(uri);
    	}
    }
}
