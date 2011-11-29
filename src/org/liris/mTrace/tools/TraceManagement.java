/**.
 * 
 */
package org.liris.mTrace.tools;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.liris.ktbs.domain.AttributeType;
import org.liris.ktbs.domain.interfaces.IAttributeType;
import org.liris.ktbs.domain.interfaces.IObselType;
import org.liris.ktbs.domain.interfaces.IStoredTrace;
import org.liris.ktbs.domain.interfaces.ITraceModel;
import org.liris.ktbs.domain.interfaces.IUriResource;
import org.liris.ktbs.service.impl.ObselBuilder;
import org.liris.ktbs.utils.KtbsUtils;
import org.liris.mTrace.MTraceConst;
import org.liris.mTrace.kTBS.SingleKtbs;
import org.liris.mTrace.tools.exception.AttributeTypeUnknowException;
import org.liris.mTrace.tools.exception.DateWrongFormatException;
import org.liris.mTrace.tools.exception.ObselTypeNumberOfAttributesException;
import org.liris.mTrace.tools.exception.ObselTypeUnknowException;

/**.
 * @author Dino
 * @23 mai 2011
 */
public class TraceManagement {

    private File obselFile;
    private String obselFileContentType;
    private String obselFileFileName;
    private Date beginDT;
    private Date endDT;
    private String label;
    private String traceModelUri;
    private Date beginDate;
    private Date endDate;
    
    @Deprecated
    public TraceManagement(){}
    
    public TraceManagement(File obselFile, String label, Date beginDT, Date endDT, String traceModelUri){
	this();
	this.obselFile = obselFile;
	this.beginDT = beginDT;
	this.endDT = endDT;
	this.label = label;
	this.traceModelUri = traceModelUri;
    }

    /**.
     * 	@param string
     * 	@param string2
     */
    private void checkdate(String strDateDebut, String strDateFin) {
	try{
	Date beginDate = MTraceConst.strDateFromCSV(strDateDebut);
	Date endDate = MTraceConst.strDateFromCSV(strDateFin);
	
	if(endDate.equals(beginDate) || endDate.after(beginDate)){
	    if(this.beginDate == null || beginDate.before(this.beginDate)){
		setBeginDate(beginDate);
	    }
	    if(this.endDate == null || endDate.after(this.endDate)){
		setEndDate(endDate);
	    }
	}else{
	    System.err.println("DateEnd >= DateBegin");
	}
	
	}catch(Exception ex){
	    ex.printStackTrace();
	}
}

    
    public List<Exception> checkTrace() throws IOException{
	List<Exception> exception = new ArrayList<Exception>();
	List<String> lines = FileUtils.readLines(getObselFile(), "ISO-8859-1");
	for (String string : lines) {
	    String[] cols = StringUtils.splitByWholeSeparatorPreserveAllTokens(string, ";");
	    
	    for (int j = cols.length -1; j > 0 && StringUtils.isEmpty(cols[j]); j--) {
		    if(StringUtils.isEmpty(cols[j])){
			cols = (String[])ArrayUtils.remove(cols, j);
		    }
		}
	    
	    if(cols.length > 0){
		//check template traceModel
		if(cols[0].equals(cols[1]) && cols[1].equals("")){
		    
		    //System.out.println("Obseltype detected :"+cols[2]);
		    //check obselType in model
		    if(!checkObselType(cols[2], traceModelUri)){
			exception.add(new ObselTypeUnknowException(cols[2],traceModelUri));
		    }else{
		    
			//check number of attribute
			String obselTypeUri = SingleKtbs.getInstance().getObselTypeUriByLabel(traceModelUri, cols[2]).getUri();
			ITraceModel traceModel = SingleKtbs.getInstance().getTraceModel(traceModelUri) ;
			List<IAttributeType> attributeList = SingleKtbs.getInstance().getListAttributeTypeOfObselType(traceModel, obselTypeUri); 
			if(cols.length -1 > attributeList.size()){
			    exception.add(new ObselTypeNumberOfAttributesException(cols[2],cols, attributeList));
			}
			
        		    //check attributeType in obselType
        		    for (int i = 4; i < cols.length; i++) {
        			if(!StringUtils.isEmpty(cols[i])){
        			    //System.out.println("       Attribute detected :"+cols[i]);
        			    if(!checkAttributeType(cols[i], cols[2], traceModelUri)){
        				exception.add(new AttributeTypeUnknowException(traceModelUri, cols[2], cols[i]));
        			    }
        			}
        		    }
		    }
		}else{
		    //check obsel
		    if(!checkDate(cols[0])){
			exception.add(new DateWrongFormatException(cols[0], "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "dateDebut"));
		    }
		    if(!checkDate(cols[1])){
			exception.add(new DateWrongFormatException(cols[1], "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "dateFin"));
		    }
		    
		    //Vérification que les 2 première cellules sont bien des dates
		    //Calcul de la date début la plus petite et de la date de fin la plus grande
		    checkdate(cols[0], cols[1]);
		    
		    if(!checkObselType(cols[2], traceModelUri)){
			exception.add(new ObselTypeUnknowException(cols[2],traceModelUri));
		    }
//		    for (int i = 3; i < cols.length; i++) {
//			if(!checkRangeAttributeType(cols[i], cols[2], traceModelUri)){
//				exception.add(new AttributeTypeUnknowException(traceModelUri, cols[2], cols[i]));
//			    }
//		    }
		}
	    }
	}
	return exception;
    }

    /**.
     * @throws Exception 
     * 
     */
    public String saveTraceWithObsel() throws Exception {
	
	if(getBeginDate() == null){
	    setBeginDate(getBeginDT());
	}
	if(getEndDate() == null){
	    setEndDate(getEndDT());
	}
	
	IStoredTrace storedTrace = SingleKtbs.getInstance().createStoredTrace(
		null,
		getTraceModelUri(),
		KtbsUtils.xsdDate(getBeginDate()),
		getLabel(),
		"0",
		KtbsUtils.xsdDate(getBeginDate()),
		KtbsUtils.xsdDate( getEndDate()),
		" ");
//	storedTrace.addProperty("hasTraceBeginDT", getBeginDT());
//	storedTrace.addProperty("hasTraceEndDT", getEndDT());
//	SingleKtbs.getInstance().getRoot().getStoredTraceService().saveDescription(storedTrace);
	if(storedTrace != null){
	   // List<ObselBuilder> builders = new ArrayList<ObselBuilder>();
	    SingleKtbs.getInstance().getRoot().getStoredTraceService().startBufferedCollect(storedTrace);
	    
	    List<String> lines = FileUtils.readLines(getObselFile(), "ISO-8859-1");
	    Map<String, IAttributeType[]> obselTypeFromKtbs = new HashMap<String, IAttributeType[]>();
		for (String string : lines) {
		    ObselBuilder obselBuilder = SingleKtbs.getInstance().getRoot().getStoredTraceService().newObselBuilder(storedTrace) ;
		    String[] cols = StringUtils.splitByWholeSeparatorPreserveAllTokens(string, ";");
		    for (int j = cols.length -1; j > 0 && StringUtils.isEmpty(cols[j]); j--) {
			    if(StringUtils.isEmpty(cols[j])){
				cols = (String[])ArrayUtils.remove(cols, j);
			    }
			}
		    if(cols.length > 0){
			//check template traceModel
			if(cols[0].equals(cols[1]) && cols[1].equals("")){
			    //traceModel declaration
			    //String[] attributeTypes = new String[cols.length];
			    IAttributeType[] attributeTypes = new AttributeType[cols.length];
//			    attributeTypes[0] = traceModelUri + "ktbs#hasBeginDT";
//			    attributeTypes[1] = traceModelUri + "ktbs#hasEndDT";
//			    attributeTypes[2] = "http://www.w3.org/2000/01/rdf-schema#label";
			    for (int i=4;i<cols.length;i++) {
				if(!StringUtils.isEmpty(cols[i])){
				    IAttributeType attribute = SingleKtbs.getInstance().getAttributeTypeUriByLabel(traceModelUri, cols[2], cols[i]);
				    if(attribute != null){
					//attributeTypes[i] = attributeUri.getUri();
					AttributeType attributeT = new AttributeType();
					attributeT.setUri(attribute.getUri());
					attributeT.setRanges(attribute.getRanges());
					attributeT.setLabels(attribute.getLabels());
					attributeT.setDomains(attribute.getDomains());
					attributeT.setProperties(attribute.getProperties());
					attributeT.setParentResource(attribute.getParentResource());
					
					attributeTypes[i] = attributeT;
				    }
				}
//				else{
//				    attributeTypes[i-1] = null;
//				}
			    }
			    //obselTypeFromKtbs.put(cols[2], attributeTypes);
			    obselTypeFromKtbs.put(cols[2],attributeTypes);
			}else{
			    //obsel
			    String beginDT = cols[0];
			    String endDT = cols[1];
			    String obselLabel = cols[2];
			    String label = cols[3];
			    
			    obselBuilder.addLabel(label);
			    			    
			    obselBuilder.setBeginDT(KtbsUtils.xsdDate(MTraceConst.strDateFromCSV(beginDT)));
			    obselBuilder.setEndDT(KtbsUtils.xsdDate(MTraceConst.strDateFromCSV(endDT)));
			    
			    
			    
			    obselBuilder.setType(SingleKtbs.getInstance().getObselTypeUriByLabel(traceModelUri, obselLabel));
			    obselBuilder.setSubject("");
			    
			    IAttributeType[] attributeType = obselTypeFromKtbs.get(obselLabel);
			    
			    for (int i = 4; i < attributeType.length; i++) {
				Object value = null;
				IAttributeType attType = attributeType[i];
				if(i < cols.length){
				    if(attType == null){
					//System.out.println(i);
				    }else{
				    if(attType.getRanges() != null && attType.getRanges().size() > 0 && !cols[i].equals("-")){ 
					    if(new ArrayList<IUriResource>(attType.getRanges()).get(0).getUri().endsWith("#string") ){
						value = cols[i];
					    }else if(new ArrayList<IUriResource>(attType.getRanges()).get(0).getUri().endsWith("#integer")){
						value = BigInteger.valueOf(Long.valueOf(cols[i]));
					    }else if(new ArrayList<IUriResource>(attType.getRanges()).get(0).getUri().endsWith("#dateTime")){
						Calendar c = Calendar.getInstance(Locale.FRANCE);
						c.setTime(MTraceConst.strDateFromCSV(cols[i]));
						value = c;
					    }
				    }else{
					value = cols[i];
				    }
				    obselBuilder.addAttribute(attType.getUri(), value);				    
				    }
				}else{
				    obselBuilder.addAttribute(attType.getUri(), "-");
				}
			    }
			    
//			    for (int i = 4; i < cols.length; i++) {
//				if(i < obselTypeFromKtbs.get(obselLabel).length){
//				if(!StringUtils.isEmpty(obselTypeFromKtbs.get(obselLabel)[i])){
//				    obselBuilder.addAttribute(obselTypeFromKtbs.get(obselLabel)[i], cols[i]);
//				    System.out.println(obselTypeFromKtbs.get(obselLabel)[i]+"   "+ cols[i]);
//				}
//				}else{
//				    obselBuilder.addAttribute(obselTypeFromKtbs.get(obselLabel)[i], "");
//				}
//			    }
			    //builders.add(obselBuilder);
			    obselBuilder.create();
			}
		    }
		}
		SingleKtbs.getInstance().getRoot().getStoredTraceService().postBufferedObsels(storedTrace);
		SingleKtbs.getInstance().refresh("");
		//((CachingResourceService)SingleKtbs.getInstance().getRoot().getResourceService()).removeFromCache(SingleKtbs.getInstance().getCurrentBase());
	}
	return storedTrace.getUri();
    }
    
//    private class ObselBuilderDecorator {
//	private ObselBuilder builder;
//
//	public ObselBuilderDecorator(ObselBuilder builder) {
//	    super();
//	    this.builder = builder;
//	}
//
//	private String endDT;
//	public void setEndDT(String endDT) {
//	    builder.setEndDT(endDT);
//	    this.endDT = endDT;
//	}
//
//	public String getEndDT() {
//	    return endDT;
//	}
//	
//	public ObselBuilder getBuilder() {
//	    return builder;
//	}
//    }
    
    private boolean checkDate(String dateStr){
	String[] formats = new String[] {"yyyy-MM-dd'T'HH:mm:ss.SSSZ","dd/MM HH:mm:ss.SSS" };
	try {
	    DateUtils.parseDate(dateStr, formats);
	} catch (Exception e) {
	   return false;
	}
	return true;
    }
    
    private boolean checkAttributeType(String attributeTypeLabel, String obselTypeLabel, String traceModelUri){
	boolean check = false;
	//System.out.println(attributeTypeLabel);
	List<IAttributeType> attributeTypes = new ArrayList<IAttributeType>(SingleKtbs.getInstance().getTraceModel(traceModelUri).getAttributeTypes());
	for (IAttributeType attributeType : attributeTypes) {
	    if(attributeType.getLabel().equals(attributeTypeLabel)){
		for (IObselType domain : attributeType.getDomains()) {
		    if(domain.getLabel().equals(obselTypeLabel)){
			return true;
		    }
		}
	    }
	}
	return check;
    }
    
    private boolean checkObselType(String label, String traceModelUri){
	boolean check = false;
	List<IObselType> obselTypes = new ArrayList<IObselType>(SingleKtbs.getInstance().getTraceModel(traceModelUri).getObselTypes());
	for (IObselType iObselType : obselTypes) {
	    if(iObselType.getLabel().equals(label)){
		return true;
	    }
	}
	return check;
    }
    
    public File getObselFile() {
        return obselFile;
    }
    public void setObselFile(File obselFile) {
        this.obselFile = obselFile;
    }
    public Date getBeginDT() {
        return beginDT;
    }
    public void setBeginDT(Date beginDT) {
        this.beginDT = beginDT;
    }
    public Date getEndDT() {
        return endDT;
    }
    public void setEndDT(Date endDT) {
        this.endDT = endDT;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getTraceModelUri() {
        return traceModelUri;
    }
    public void setTraceModel(String traceModelUri) {
        this.traceModelUri = traceModelUri;
    }

    public void setObselFileContentType(String obselFileContentType) {
	this.obselFileContentType = obselFileContentType;
    }

    public String getObselFileContentType() {
	return obselFileContentType;
    }

    public void setObselFileFileName(String obselFileFileName) {
	this.obselFileFileName = obselFileFileName;
    }

    public String getObselFileFileName() {
	return obselFileFileName;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
