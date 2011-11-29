/**.
 * 
 */
package org.liris.mTrace.actions.transformation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.liris.ktbs.domain.interfaces.IMethod;
import org.liris.ktbs.domain.interfaces.ITraceModel;
import org.liris.mTrace.MTraceConst;
import org.liris.mTrace.actions.D3KODEAction;
import org.liris.mTrace.kTBS.SingleKtbs;
import org.liris.mTrace.tools.pojo.TransformationPojo;
import org.liris.mTrace.tools.pojo.rule.RulePojo;

/**.
 * @author Dino
 * @27 avr. 2011
 */
@ParentPackage(value="mTrace")
public class TransformationAction extends D3KODEAction {

    /**.
     * 
     */
    private static final String TRANSFORMATION = "transformation";
    private TransformationPojo transformation;
    private String transformationLocalName;
    private ITraceModel transformationSelected;
    private List<TransformationPojo> transformations;

    /**.
     * 
     */
    private static final long serialVersionUID = -8380951588503007074L;
    
    @Action(value = "/create_or_update_transformation_in_ktbs", results = {
	    @Result(location = "transformation/management.jsp", name = "success")
	  })
    public String create_or_update_transformation_in_ktbs() throws Exception {
	if(transformation != null && transformation.getUri() != null){
	    transformation = TransformationPojo.deserialize(new File(transformation.getUri()));
	    if(transformation.saveIntoKtbs()){
		addActionMessage(getText("transformation.recorded"));
	    }else{
		addActionError(getText("transformation.not.recorded"));
	    }
	}
        return SUCCESS;
    }
    
    
    @Action(value = "/create_or_update_transformation_prepare", results = {
	    @Result(location = "transformation/create/ihm.jsp", name = "success")
	  })
    public String create_update_preprare() throws Exception {
	if(transformation != null && !StringUtils.isEmpty(transformation.getUri())){
	    setTransformation(TransformationPojo.deserialize(new File(transformation.getUri())));
	}else{
	    	File folderTransformation = new File(MTraceConst.WORK_TRANSFORMATION_PATH);
	    	IOFileFilter fileFilter = FileFilterUtils.prefixFileFilter(TRANSFORMATION);
	    	Collection<File> transformationFiles = FileUtils.listFiles(folderTransformation, fileFilter, null);
	    	String transformationFileName = TRANSFORMATION+transformationFiles.size();
	    	if (transformation != null)
	    	    transformation.serialize(new File(MTraceConst.WORK_TRANSFORMATION_PATH+transformationFileName));
	}
        return SUCCESS;
    }
    
    @Action(value = "/create_or_update_transformation",
	    results = { @Result(location = "empty.jsp", name = "success") })
    public String update_or_create() {
	try {
	    if(transformation != null && transformation.getUri() != null){
		String newLabel = transformation.getLabel();
		String description = transformation.getDescription();
		String newSourceTraceModel = transformation.getTraceModelSourceSelected();
		String newTargetTraceModel = transformation.getTraceModelTargetSelected();
		setTransformation(TransformationPojo.deserialize(new File(transformation.getUri())));
	        transformation.setLabel(newLabel);
	        transformation.setDescription(description);
	        transformation.setTraceModelSourceSelected(newSourceTraceModel);
	        transformation.setTraceModelTargetSelected(newTargetTraceModel);
	        transformation.serialize(new File(transformation.getUri()));
	    }else{
	    	IMethod superMethod = SingleKtbs.getInstance().createOrUpdateSuperMethodKtbs(null,null, transformation.getLabel(), transformation.getTraceModelTarget(), transformation.getDescription());
	    	transformation.setLocalName(superMethod.getLocalName());
	    	if (transformation != null)
	    	    transformation.serialize(new File(MTraceConst.WORK_TRANSFORMATION_PATH+superMethod.getLocalName()));
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
        return SUCCESS;
    }
    
    @Action(value = "/delete_transformation", results = {
	    @Result(location = "transformation/management.jsp", name = "success")
	  })
    public String delete() throws Exception {
	if(transformation != null && transformation.getUri() != null ){
	    List<Object> deletedField = new ArrayList<Object>();
	    setTransformation(TransformationPojo.deserialize(new File(transformation.getUri())));
	    if(transformation.getRules() != null){
        	    for(RulePojo rulePojo : transformation.getRules()){
        		boolean delMethod = SingleKtbs.getInstance().getRoot().getResourceService()
        		.deleteResource(SingleKtbs.getInstance().getCurrentBase() + rulePojo.getUri());
        		if(delMethod){
        		    deletedField.add(rulePojo.getLabel());
        		    addActionMessage(getText("method.deleted", deletedField));
        		    deletedField.remove(0);
        		}
        	    }
	    }
	    boolean delSuperMethod = SingleKtbs.getInstance().getRoot().getResourceService()
		.deleteResource(SingleKtbs.getInstance().getCurrentBase() + transformation.getLocalName());
	    
	    if(delSuperMethod){
		deletedField.add(transformation.getLabel());
		addActionMessage(getText("transformation.deleted", deletedField));
		SingleKtbs.getInstance().refresh(SingleKtbs.getInstance().getCurrentBase());
	    }
	    FileUtils.deleteQuietly(new File(transformation.getUri()));
	}
        return SUCCESS;
    }

    public void setTransformation(TransformationPojo transformation) {
	this.transformation = transformation;
    }
    
    public TransformationPojo getTransformation() {
	return transformation;
    }
    
    @Action(value = "/management_transformation",
	    results = {
		    @Result(location = "transformation/management.jsp", name = "success")
		  }
    )
    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }
    
    
    @Action(value = "/list_transformation",
	    results = {
		    @Result(location = "transformation/read/list.jsp", name = "success")
		  }
    )
    public String read() throws Exception {
	List<TransformationPojo> list = new ArrayList<TransformationPojo>();
	File folderTransformation = new File(MTraceConst.WORK_TRANSFORMATION_PATH);
	File[] transformationFiles = folderTransformation.listFiles();
	for (File transformationFile : transformationFiles) {
	    if(!transformationFile.getName().startsWith(".")){
		TransformationPojo transformationPojo = TransformationPojo.deserialize(transformationFile);
		if(transformationPojo != null){
		    list.add(transformationPojo);
		}
	    }
	}
	setTransformations(list);
       return SUCCESS;
    }
    
    @Action(value = "/select_transformation",
	    results = {
		    @Result(location = "transformation/create/ihm.jsp", name = "success")
		  }
    )
    public String selectModel() throws Exception {
	
       return SUCCESS;
    }   

    public String getTransformationLocalName() {
        return transformationLocalName;
    }


    public void setTransformationLocalName(String transformationLocalName) {
        this.transformationLocalName = transformationLocalName;
    }


    public ITraceModel getTransformationSelected() {
        return transformationSelected;
    }


    public void setTransformationSelected(ITraceModel transformationSelected) {
        this.transformationSelected = transformationSelected;
    }


    public List<TransformationPojo> getTransformations() {
        return transformations;
    }


    public void setTransformations(List<TransformationPojo> transformations) {
        this.transformations = transformations;
    }
}
