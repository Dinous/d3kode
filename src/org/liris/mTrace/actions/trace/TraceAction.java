/**.
 * 
 */
package org.liris.mTrace.actions.trace;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.liris.ktbs.domain.interfaces.IAttributeType;
import org.liris.ktbs.domain.interfaces.IBase;
import org.liris.ktbs.domain.interfaces.IComputedTrace;
import org.liris.ktbs.domain.interfaces.IObsel;
import org.liris.ktbs.domain.interfaces.IStoredTrace;
import org.liris.ktbs.domain.interfaces.ITrace;
import org.liris.ktbs.domain.interfaces.ITraceModel;
import org.liris.mTrace.MTraceConst;
import org.liris.mTrace.actions.D3KODEAction;
import org.liris.mTrace.actions.json.ListValue;
import org.liris.mTrace.kTBS.SingleKtbs;
import org.liris.mTrace.kTBS.XmlBaseGenerator;
import org.liris.mTrace.tools.TraceManagement;
import org.liris.mTrace.tools.exception.AttributeTypeUnknowException;
import org.liris.mTrace.tools.exception.ObselTypeNumberOfAttributesException;
import org.liris.mTrace.tools.exception.ObselTypeUnknowException;
import org.liris.mTrace.xml.XslExecutor;

/**.
 * @author Dino
 * @20 mai 2011
 */
@ParentPackage(value = "mTrace")
public class TraceAction extends D3KODEAction {

    private String modelTraceSelectedUri;
    private ITraceModel traceModelSelected;
    private String traceUri;
    private String transformationUri;
    
    private String uriSvgFile;
    private List<String> urisSvgFile;  
    
    //Double scale = 1.0;
	
    private String scale;
    private Integer beginStone;
    private Integer endStone;
    private Integer temporalScale;
    
    
    private TraceManagement traceBuilder; 
    /**.
     * 
     */
    private static final long serialVersionUID = 1508404545980528579L;

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Action(value = "/firstTrace",
	    results = {@Result(location = "trace/management.jsp", name = "success")}
    )
    public String trace()  {
        return SUCCESS;
    }
    
    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Action(value = "/listTraceModel",
	    results = {@Result(location = "trace/traceModelList.jsp", name = "success")}
    )
    public String listTraceModel()  {
	return SUCCESS;
    }
    
    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Action(value = "/prototype_data",
	    results = {@Result(location = "trace/prototypeData.jsp", name = "success")}
    )
    public String prototype_data()  {
	setTraceModelSelected(SingleKtbs.getInstance().getTraceModel(modelTraceSelectedUri));
        return SUCCESS;
    }
    
    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Action(value = "/upload_obsel_create_trace",
	    results = {@Result(location = "trace/management.jsp", name = "success")}
    )
    public String upload_obsel_create_trace()  {
	try {
	if(!FilenameUtils.getExtension(traceBuilder.getObselFileFileName()).equals("csv")){
		List<Object> ext = new ArrayList<Object>();
		ext.add(FilenameUtils.getExtension(traceBuilder.getObselFileFileName()));
		addActionError(getText("bad.extention",ext));
	}
	List<Exception> exceptions = traceBuilder.checkTrace(); 
	    if(!exceptions.isEmpty()){
		for (Exception exception : exceptions) {
		    if(exception instanceof ObselTypeUnknowException){
			ObselTypeUnknowException obselTypeUnknowException = ((ObselTypeUnknowException)exception);
			List<Object> objects = new ArrayList<Object>();
			objects.add(obselTypeUnknowException.getTraceModel());
			objects.add(obselTypeUnknowException.getObselType());
			addActionError(getText("obseltype.exception", objects));
		    }else if(exception instanceof AttributeTypeUnknowException){
			addActionError("Attribute " + ((AttributeTypeUnknowException)exception).getAttributeType()+
				" declared in file but not in model for the obselType : "+((AttributeTypeUnknowException)exception).getObselType());
			
		    }else if(exception instanceof ObselTypeNumberOfAttributesException){
			addActionError(((ObselTypeNumberOfAttributesException)exception).getObselType()+
				" "+((ObselTypeNumberOfAttributesException)exception).getOverAttribute());
			
		    }
		       
		}
	    }else{
		traceBuilder.saveTraceWithObsel();
	    }
	} catch (Exception e) {
	    addActionError(e.getMessage());
	    e.printStackTrace();
	}
        return SUCCESS;
    }
    
//    @Action(value = "/buildSvgForOneTrace",
//	    results = {@Result(location = "svg/svg.jsp", name = "success")}
//    )
//    public String buildSvgForOneTrace(){
//	try {
//	String svgName = StringUtils.substringAfterLast(StringUtils.substringBeforeLast(traceUri,"/"), "/")+".svg";
//	File destFile = new File(servletRequest.getServletContext().getRealPath("work/svg/"+svgName));
//	String destFilePath = MTraceConst.WORK_SVG_PATH + svgName;
//	String xslFilePath = MTraceConst.WORK_XSL_PATH + "trace.xsl";
//	String pathDes =  MTraceConst.WORK_XML_PATH + "baseXml.xml";
//	
//	if(!destFile.exists()){
////	IBase currentBase = SingleKtbs.getInstance().getCurrentBase();
////	ITrace trace = SingleKtbs.getInstance().getRoot().getResourceService().getTrace(traceUri);
//	//XmlBaseGenerator xmlBaseGenerator = new XmlBaseGenerator(currentBase.getUri(), XmlBaseGenerator.TypeGenerateSvg.BASE, getSecondInterval());
//	    XmlBaseGenerator xmlBaseGenerator = new XmlBaseGenerator(null,traceUri, XmlBaseGenerator.TypeGenerateSvg.TRACE);
//	    xmlBaseGenerator.createFileXml(pathDes, beginStone, endStone);
//	}
//	    XslExecutor xslExecutor = new XslExecutor(pathDes,destFilePath, xslFilePath);
//	    xslExecutor.createSvg(getSecondInterval(), 0, Double.valueOf(scale));
//	    FileUtils.copyFile(new File(destFilePath), destFile);
//	    destFilePath = MTraceConst.WORK_XML_PATH + "obsels.xml";
//	    xslFilePath = MTraceConst.WORK_XSL_PATH + "obsels.xsl";
//	    xslExecutor = new XslExecutor(destFilePath, destFilePath, xslFilePath);
//	
//	setUriSvgFile("./work/svg/"+svgName);
//	} catch (Exception e) {
//	    e.printStackTrace();
//	    addActionError(e.getMessage());
//	}
//	return SUCCESS;
//    }
    
    private Integer getNbTransformation(ITrace trace){
    	for(ITrace computedTrace : trace.getTransformedTraces()){
    		if(!((IComputedTrace)computedTrace).getIsIntermediateSource()){
    			return 1 + getNbTransformation(computedTrace);
    		}
    	}
    	return 0;
    }
    
    @Action(value = "/buildSvgForOneTraceWithMethod",
	    results = {@Result(location = "svg/svg.jsp", name = "success")}
    )
    public String buildSvgForOneTraceWithMethod(){
	IBase base = SingleKtbs.getInstance().getCurrentBase(); 
	XmlBaseGenerator xmlBaseGenerator = new XmlBaseGenerator(base.getUri(),null, XmlBaseGenerator.TypeGenerateSvg.BASE);

	ITrace storedTrace = (ITrace)base.get(traceUri);
	Integer nbTransformation = getNbTransformation(storedTrace);
	Date originDate = MTraceConst.dateFromkTBSDateDT(storedTrace.getOrigin());
	Date beginDate = MTraceConst.dateFromkTBSDateDT(storedTrace.getTraceBeginDT());
	Date endDate = MTraceConst.dateFromkTBSDateDT(storedTrace.getTraceEndDT());
	
	
//	String sourceFilePath =  MTraceConst.WORK_XML_PATH + traceLocalName + "_WITH_METHOD_";
//	String svgName =  traceLocalName + "_WITH_METHOD_";
	if(beginStone == null){
	    beginStone = Integer.valueOf(String.valueOf((beginDate.getTime() - originDate.getTime()) / 1000)); 
	}
	if(endStone == null){
	    endStone = Integer.valueOf(String.valueOf((endDate.getTime() - originDate.getTime()) / 1000)); 
	}
//	sourceFilePath += ".xml";
//	File fileDes = new File(sourceFilePath);
//	svgName = svgName + "_" + scale +".svg";
//	String destFilePath = MTraceConst.WORK_SVG_PATH + svgName;
	
	
	String xslFilePath = MTraceConst.WORK_XSL_PATH + "trace.xsl";
	File svgFile = null;
    	try {
    			File xmlForSvgFile = xmlBaseGenerator.createOrUpdateFileXml(getUserLogin()+File.separator+storedTrace.getLocalName(),beginStone, endStone, nbTransformation, scale);

    	//if(!destFile.exists()){
    	    XslExecutor xslExecutor = new XslExecutor(xmlForSvgFile, xslFilePath);
    	    int nbSuperMethod = xmlBaseGenerator.getNbSuperMethod();
//    	    if(getSecondInterval() == null){
//    		ITrace trace = SingleKtbs.getInstance().getRoot().getResourceService().getTrace(traceUri);
//    		beginStone = 0;
//    		endStone = Long.valueOf(
//    			(
//    				MTraceConst.strDateFromCSV(trace.getTraceEndDT()).getTime() 
//    				- 
//    				MTraceConst.strDateFromCSV(trace.getTraceBeginDT()).getTime()
//    			) / 1000 ).intValue();
//    	    }
    	    String svgFilePath = xslExecutor.createSvg(beginStone, endStone, Double.valueOf(scale),nbSuperMethod, temporalScale );
    	    svgFile = new File(svgFilePath);
    	    File destFile = new File(request.getServletContext().getRealPath("work/svg/"+svgFile.getName()));
    	    FileUtils.copyFile(svgFile, destFile);
    	    //FileUtils.deleteQuietly(new File(destFilePath));
    	    //destFilePath = MTraceConst.WORK_XML_PATH + "obsels.xml";
    	    //xslFilePath = MTraceConst.WORK_XSL_PATH + "obsels.xsl";
    	//}    
    	} catch (Exception e) {
    	    e.printStackTrace();
    	    addActionError(e.getMessage());
    	}
	if(svgFile != null){
	 setUriSvgFile("./work/svg/"+svgFile.getName());
	}else{
		System.err.println("svgFile null "+beginStone+" "+endStone);
	}
	return SUCCESS;
    }
    
    
    
    public Set<IStoredTrace> getStoredTraces(){
	return SingleKtbs.getInstance().getCurrentBase().getStoredTraces();
    }
    
    public String labelOfResource(Set<String> labels){
	if(labels != null){    		
	    for (String label : labels) {
		if(!label.toLowerCase().contains("subject")){
		    return label;
		}
	    }
	}
	return "-";
    }

    public Map<String, Integer> listObselInTrace(Set<IObsel> ObselTypes){
	Map<String, Integer> lstObsel = new HashMap<String, Integer>();
	for (IObsel obsel : ObselTypes) {
	    Integer nbObseltype = lstObsel.get(obsel.getObselType().getLabel());
	    if(nbObseltype != null){
		nbObseltype = nbObseltype +1;
		lstObsel.put(obsel.getObselType().getLabel(), nbObseltype);
	    }else{
		lstObsel.put(obsel.getObselType().getLabel(), 1);
	    }
	}
	return lstObsel;
    }
    
    
    
    public List<IAttributeType> getAttributeFromObselType(ITraceModel model, String obselType){
	return SingleKtbs.getInstance().getListAttributeTypeOfObselType(model, obselType);
    }
    
    public void setModelTraceSelectedUri(String modelTraceSelectedUri) {
	this.modelTraceSelectedUri = modelTraceSelectedUri;
    }

    public String getModelTraceSelectedUri() {
	return modelTraceSelectedUri;
    }

    public ITraceModel getTraceModelSelected() {
        return traceModelSelected;
    }

    public void setTraceModelSelected(ITraceModel traceModelSelected) {
        this.traceModelSelected = traceModelSelected;
    }   

    public TraceManagement getTraceBuilder() {
        return traceBuilder;
    }

    public void setTraceBuilder(TraceManagement traceBuilder) {
        this.traceBuilder = traceBuilder;
    }

    public void setTraceUri(String traceUri) {
	this.traceUri = traceUri;
    }

    public String getTraceUri() {
	return traceUri;
    }   

    public void setUriSvgFile(String uriSvgFile) {
	this.uriSvgFile = uriSvgFile;
    }

    public String getUriSvgFile() {
	return uriSvgFile;
    }

    public void setTransformationUri(String transformationUri) {
	this.transformationUri = transformationUri;
    }

    public String getTransformationUri() {
	return transformationUri;
    }

    public void setUrisSvgFile(List<String> urisSvgFile) {
	this.urisSvgFile = urisSvgFile;
    }

    public List<String> getUrisSvgFile() {
	return urisSvgFile;
    }

    public void setBeginStone(Integer beginStone) {
	this.beginStone = beginStone;
    }

    public Integer getBeginStone() {
	return beginStone;
    }

    public void setEndStone(Integer endStone) {
	this.endStone = endStone;
    }

    public Integer getEndStone() {
	return endStone;
    }
    
    public Integer getSecondInterval(){
	if(endStone == null || beginStone == null){
	    return null;
	}
	return endStone - beginStone;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public void setTemporalScale(Integer temporalScale) {
	this.temporalScale = temporalScale;
    }

    public Integer getTemporalScale() {
	return temporalScale;
    }

    public List<ListValue> getListTraceModel() {
	Set<ITraceModel> tms= super.getTraceModels();
	List<ListValue> lv = new ArrayList<ListValue>();
	
	for (ITraceModel tm : tms) {
	    ListValue listValue = new ListValue(tm.getUri(), tm.getParentResource().getLocalName() + " " + tm.getLabel());
	    lv.add(listValue);
	}
	
	return lv;
    }
}
