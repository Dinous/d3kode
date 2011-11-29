/**.
 * 
 */
package org.liris.mTrace.actions.trace;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.liris.ktbs.client.KtbsConstants;
import org.liris.ktbs.domain.interfaces.IComputedTrace;
import org.liris.ktbs.domain.interfaces.IMethod;
import org.liris.ktbs.domain.interfaces.IMethodParameter;
import org.liris.ktbs.domain.interfaces.IStoredTrace;
import org.liris.ktbs.domain.interfaces.ITrace;
import org.liris.mTrace.MTraceConst;
import org.liris.mTrace.actions.D3KODEAction;
import org.liris.mTrace.kTBS.SingleKtbs;
import org.liris.mTrace.tools.pojo.TransformationPojo;

/**.
 * @author Dino
 * @20 mai 2011
 */
//type="redirectAction", params = {"actionName" , "calculatedTrace"}
@ParentPackage(value = "mTrace")
@Results({
    @Result(name="input", location = "trace/management_calculated.jsp")
})
public class CalculatedTraceAction extends D3KODEAction{

    private static final long serialVersionUID = 2746881779912358166L;
    private String traceUri;
    private String transformationUri;
    private String computedTraceLabel;
    private String uriSvgFile;
    
    
    
    @Action(value = "/calculatedTrace",
	    results = {@Result(location = "trace/management_calculated.jsp", name = "success")}
    )
    public String trace()  {
	SingleKtbs.getInstance().refresh("");
        return SUCCESS;
    }  
    
    public List<ITrace> getListTraceToTransform(){
	List<ITrace> listTrace = new ArrayList<ITrace>();
	List<ITrace> traces = SingleKtbs.getInstance().getTraces();

	for (ITrace iTrace : traces) {
	    try {

		if (iTrace instanceof IStoredTrace || (
			((IComputedTrace) iTrace).getMethod() != null &&
			((IComputedTrace) iTrace).getMethod().getInherits() != null &&
			((IComputedTrace) iTrace).getMethod().getInherits()
				.equals(KtbsConstants.SUPER_METHOD)
				)
		) {
		    listTrace.add(iTrace);
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	return listTrace;
    }
    
    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.ActionSupport#validate()
     */
    @Override
    public void validate() {
        super.validate();
        if(!request.getRequestURI().endsWith("calculatedTrace.action")){
            if(StringUtils.isEmpty(traceUri)){
        	addFieldError("traceUri", getText("miss.sourceTrace"));
            }
            if(StringUtils.isEmpty(transformationUri)){
        	addFieldError("transformationUri", getText("miss.method"));
            }
            if(StringUtils.isEmpty(computedTraceLabel)){
        	addFieldError("computedTraceLabel", getText("miss.computedTrace.label"));
            }
        }
    }
    
    @Action(value = "/buildTransformedTrace",
	    results = {
	    @Result(location = "trace/management_calculated.jsp", name = "success")
	    }
    )

    /**
     * Execution d'une transformation sur une trace
     */
    public String buildTransformedTrace() {
	if(!StringUtils.isEmpty(traceUri)){
	try {
	    TransformationPojo transformationPojo = TransformationPojo.deserialize(new File(transformationUri));
	    Set<String> iTraces = new HashSet<String>();
	    iTraces.add(traceUri);
	    String localName = "_" + String.valueOf(new Date().getTime());
	    
	    //si jamais la personne qui veut utiliser une method n'est pas un expert 
	    //D3KODE doit copier cette method dans la base stagiaire
	    IMethod methodSearched = SingleKtbs.getInstance().getMethodWithLocalName(transformationPojo.getLocalName());
	    if(!getIsExpert()){
		methodSearched = SingleKtbs.getInstance().duplicateSuperMethodKtbs(methodSearched);
	    }
	    
	    IComputedTrace computedTrace = SingleKtbs.getInstance().createComputedTrace(
			SingleKtbs.getInstance().getCurrentBase().getUri(),
			localName,
			methodSearched.getUri(),
			iTraces,
			null,
			computedTraceLabel);
	    for(ITrace trace : computedTrace.getIntermediateSource()){
		IComputedTrace cT = SingleKtbs.getInstance().getRoot().getResourceService().getComputedTrace(trace.getUri());
		String[] arg = {cT.getMethod().getLabel()};
		cT.addLabel(getText("intermediate.trace.of", arg));
		SingleKtbs.getInstance().getRoot().getResourceService().saveResource(cT);
	    }
    	} catch (Exception e) {
    	    //e.printStackTrace();
    	    addActionError(e.getMessage());
    	}
	}else{
	    addActionError("Veuillez sélectionner une transformation");
	}
	return SUCCESS;
    }
    
    public List<TransformationPojo> getListTransformation(String traceUri) {
	String traceModelSourceUri = SingleKtbs.getInstance().getRoot()
		.getResourceService().getTrace(traceUri).getTraceModel()
		.getUri();
	List<TransformationPojo> listTransformation = new ArrayList<TransformationPojo>();
	File transformationFolder = new File(
		MTraceConst.WORK_TRANSFORMATION_PATH);
	for (File transformationFile : transformationFolder.listFiles()) {
	    if (transformationFile.isFile()
		    && !transformationFile.getName().contains(".")) {
		try {
		    IMethod superMethod = SingleKtbs.getInstance().getMethodWithLocalName(transformationFile.getName());
		    if (superMethod != null
			    && superMethod.getMethodParameters() != null) {
			for (IMethodParameter methodParameter : superMethod
				.getMethodParameters()) {
			    if (methodParameter.getName().equals("submethods")) {
				if (methodParameter.getValue() != null
					&& !methodParameter.getValue().equals(
						"null")) {
				    TransformationPojo transformationPojo = TransformationPojo
					    .deserialize(transformationFile);
				    if (transformationPojo.getTraceModelSourceSelected()
					    .equals(traceModelSourceUri)) {
					listTransformation.add(transformationPojo);
				    }
				}
			    }
			}
		    }

		} catch (Exception e) {
		    e.printStackTrace();
		    addActionError(e.getMessage());
		}
	    }
	}
	return listTransformation;
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

    public String getComputedTraceLabel() {
        return computedTraceLabel;
    }

    public void setComputedTraceLabel(String computedTraceLabel) {
        this.computedTraceLabel = computedTraceLabel;
    }
}
