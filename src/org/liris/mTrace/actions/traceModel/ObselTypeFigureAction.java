/**.
 * 
 */
package org.liris.mTrace.actions.traceModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.liris.ktbs.domain.interfaces.ITraceModel;
import org.liris.mTrace.actions.D3KODEAction;
import org.liris.mTrace.jaxb.base.ObselTypeFigure;
import org.liris.mTrace.jaxb.base.ObselTypes;
import org.liris.mTrace.kTBS.SingleKtbs;
import org.liris.mTrace.kTBS.XmlObseltypeFigureGenerator;

/**.
 * @author Dino
 * @1 sept. 2011
 */
@ParentPackage(value="mTrace")
public class ObselTypeFigureAction extends D3KODEAction{

    private String traceModelUri;
    private String traceModelLocalName;
    
    private XmlObseltypeFigureGenerator xmlObseltypeFigureGenerator;
    private Map<Double , Integer> listTraceLevel;
    private String[] traceLevel;
    private List<String> listxLinkHref;
    private String[] xlinkHref;
    
    private ObselTypes obselTypes;
    private List<ITraceModel> listTraceModel;
    
    private File icone;
    private String iconeContentType;
    private String iconeFileName;
    
    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Action(value = "/obselTypeFigure_list",
	    results = {@Result(location = "visualization/figure_list.jsp", name = "success")}
    )
    @Override
    public String execute(){
	setListTraceModel(new ArrayList<ITraceModel>(getTraceModels()));
        return SUCCESS;
    }

    
    @Action(value = "/pictureUpload",
	    results = {
	    @Result(location = "obselTypeFigure_list", name = "success", type="redirect")
	  },
		interceptorRefs ={@InterceptorRef(value ="roles",params={"allowedRoles", "expert"}),
		@InterceptorRef("defaultStack")})
    public String pictureUpload() {
	
	try {
	    if(icone != null){
	        String urlImageFolder = request.getServletContext().getRealPath("work/image/");
	        FileUtils.copyFile(icone, new File(urlImageFolder + File.separator + iconeFileName));
	    }
	} catch (Exception e) {
	    addActionError(e.getMessage());
	}
	
	
	return SUCCESS;
    }
    
    
    @Action(value = "/obselTypeFigure",
	    results = {@Result(location = "visualization/figure.jsp", name = "success")}
    )
    public String obselTypeFigure(){
	File folderImage = new File(request.getServletContext().getRealPath("work/image"));
	setXmlObseltypeFigureGenerator(new XmlObseltypeFigureGenerator(folderImage, getTraceModels()));
	obselTypes = xmlObseltypeFigureGenerator.getObselTypeFigure(traceModelUri, getTraceModels());
	listTraceLevel = new TreeMap<Double, Integer>();
	Double step = 200D / (obselTypes.getObselTypeFigure().size()+1);
	for (int i =0; i< obselTypes.getObselTypeFigure().size();i++) {
	    listTraceLevel.put(i*step + 30, i);
	}
	listxLinkHref = new ArrayList<String>();
	
	for (File image : folderImage.listFiles()) {
	    listxLinkHref.add(image.getName());
	}
	
        return SUCCESS;
    }
    
    
     @Action(value = "/obselTypeFigure_save",
	    results = {@Result(location = "visualization/figure_list.jsp", name = "success")}
    )
    public String save()  {
	xmlObseltypeFigureGenerator = new XmlObseltypeFigureGenerator();
	obselTypes = xmlObseltypeFigureGenerator.getObselTypeFigure(traceModelUri, getTraceModels());
	for (int i =0;i<getObselTypes().getObselTypeFigure().size();i++) {
	    ObselTypeFigure obselTypeFig = getObselTypes().getObselTypeFigure().get(i);
	    obselTypeFig.setTraceLevel(Double.valueOf(getTraceLevel()[i]));
	    obselTypeFig.setXlinkHref(getXlinkHref()[i]);
	}
	xmlObseltypeFigureGenerator.save();
	addActionMessage("succes");
        return execute();
    }
    
    public void setTraceModelUri(String traceModelUri) {
	this.traceModelUri = traceModelUri;
    }

    public String getTraceModelUri() {
	return traceModelUri;
    }

    public Map<Double, Integer> getListTraceLevel() {
        return listTraceLevel;
    }

    public void setListTraceLevel(Map<Double, Integer> listTraceLevel) {
        this.listTraceLevel = listTraceLevel;
    }

    public List<String> getListxLinkHref() {
        return listxLinkHref;
    }

    public void setListxLinkHref(List<String> listxLinkHref) {
        this.listxLinkHref = listxLinkHref;
    }

    public void setTraceLevel(String[] traceLevels) {
	this.traceLevel = traceLevels;
    }

    public String[] getTraceLevel() {
	return traceLevel;
    }

    public void setXlinkHref(String[] xlinkHrefs) {
	this.xlinkHref = xlinkHrefs;
    }

    public String[] getXlinkHref() {
	return xlinkHref;
    }

    public void setListTraceModel(List<ITraceModel> listTraceModel) {
	this.listTraceModel = listTraceModel;
    }

    public List<ITraceModel> getListTraceModel() {
	return listTraceModel;
    }

    public void setXmlObseltypeFigureGenerator(
	    XmlObseltypeFigureGenerator xmlObseltypeFigureGenerator) {
	this.xmlObseltypeFigureGenerator = xmlObseltypeFigureGenerator;
    }

    public XmlObseltypeFigureGenerator getXmlObseltypeFigureGenerator() {
	return xmlObseltypeFigureGenerator;
    }

    public ObselTypes getObselTypes() {
        return obselTypes;
    }

    public void setObselTypes(ObselTypes obselTypes) {
        this.obselTypes = obselTypes;
    }

    public void setTraceModelLocalName(String traceModelLocalName) {
	this.traceModelLocalName = traceModelLocalName;
    }

    public String getTraceModelLocalName() {
	return traceModelLocalName;
    }


    public void setIconeContentType(String iconeContentType) {
	this.iconeContentType = iconeContentType;
    }


    public String getIconeContentType() {
	return iconeContentType;
    }


    public void setIconeFileName(String iconeFileName) {
	this.iconeFileName = iconeFileName;
    }


    public String getIconeFileName() {
	return iconeFileName;
    }

    public File getIcone() {
        return icone;
    }

    public void setIcone(File icone) {
        this.icone = icone;
    }
}
