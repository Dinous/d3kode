/**.
 * 
 */
package org.liris.mTrace.actions.json;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
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

/**
 * .
 * 
 * @author Dino
 * @9 juin 2011
 */
@ParentPackage(value = "mTrace")
public class JsonTraceAction extends D3KODEAction {

    private String traceUri;
    private List<ListValue> listTrace;
    private List<ListValue> listTransformation;

    @Actions({ @Action(value = "/listTrace", results = { @Result(name = "success", type = "json") }) })
    @Override
    public String execute() {
	if (listTrace == null) {
	    listTrace = new ArrayList<ListValue>();
	    List<ITrace> traces = SingleKtbs.getInstance().getTraces();

	    for (ITrace iTrace : traces) {
		try {
		    if (iTrace instanceof IStoredTrace || (((IComputedTrace) iTrace).getMethod() != null &&
				((IComputedTrace) iTrace).getMethod().getInherits()
					.equals(KtbsConstants.SUPER_METHOD))) {
			
			if(iTrace.getTransformedTraces() == null || iTrace.getTransformedTraces().size() == 0){
			    ListValue listValue = new ListValue(iTrace.getUri(),
				    MTraceConst.strDate(iTrace.getLocalName()) + " "
				    + getLabel(iTrace.getLabels()));
			    listTrace.add(listValue);
			}
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	}
	if (!StringUtils.isEmpty(traceUri)) {
	    String traceModelSourceUri = SingleKtbs.getInstance().getRoot()
		    .getResourceService().getTrace(traceUri).getTraceModel()
		    .getUri();
	    listTransformation = new ArrayList<ListValue>();
	    File transformationFolder = new File(MTraceConst.WORK_TRANSFORMATION_PATH);
	    for (File transformationFile : transformationFolder.listFiles()) {
		if (transformationFile.isFile()
			&& !transformationFile.getName().contains(".")) {
		    try {
			IMethod superMethod = SingleKtbs.getInstance().getMethodWithLocalName(transformationFile.getName());
			if(superMethod!=null && superMethod.getMethodParameters() != null){
			    for (IMethodParameter methodParameter : superMethod.getMethodParameters()) {
			    	if(methodParameter.getName().equals("submethods")){
			    		if(methodParameter.getValue() != null &&   !methodParameter.getValue().equals("null")){
			    			TransformationPojo transformationPojo = TransformationPojo.deserialize(transformationFile);
			    			if (transformationPojo.getTraceModelSourceSelected().equals(traceModelSourceUri)) {
			    				listTransformation.add(new ListValue(transformationPojo.getUri(), transformationPojo.getLabel()));
			    			}
			    		}
			    		break;
			    	}
			    }
			}
			
		    } catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		    }
		}
	    }
	}
	return SUCCESS;
    }    
    
    public String getJSON() {
	return execute();
    }

    public void setListTrace(List<ListValue> listTrace) {
	this.listTrace = listTrace;
    }

    public List<ListValue> getListTrace() {
	return listTrace;
    }

    public List<ListValue> getListTransformation() {
        return listTransformation;
    }

    public void setListTransformation(List<ListValue> listTransformation) {
        this.listTransformation = listTransformation;
    }

    public String getTraceUri() {
        return traceUri;
    }

    public void setTraceUri(String traceUri) {
        this.traceUri = traceUri;
    }
}
