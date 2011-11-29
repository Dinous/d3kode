/**.
 * 
 */
package org.liris.mTrace.actions.traceModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.liris.ktbs.client.KtbsConstants;
import org.liris.ktbs.domain.KtbsResource;
import org.liris.ktbs.domain.UriResource;
import org.liris.ktbs.domain.interfaces.IAttributeType;
import org.liris.ktbs.domain.interfaces.IBase;
import org.liris.ktbs.domain.interfaces.IKtbsResource;
import org.liris.ktbs.domain.interfaces.IObselType;
import org.liris.ktbs.domain.interfaces.ITraceModel;
import org.liris.ktbs.domain.interfaces.IUriResource;
import org.liris.mTrace.MTraceConst;
import org.liris.mTrace.actions.D3KODEAction;
import org.liris.mTrace.kTBS.SingleKtbs;
import org.liris.mTrace.tools.EnumXSDType;
import org.liris.mTrace.tools.TraceModelManagement;

import com.jgeppert.struts2.jquery.tree.result.TreeNode;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.AttributeType;

/**.
 * @author Dino
 * @26 avr. 2011
 */
@ParentPackage(value = "mTrace")
public class ManageTraceModelAction extends D3KODEAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6338985248410709631L;
    private String uri;
    private TreeNode nodes;
    private KtbsResource ktbsResource;  
    private String traceModelLocalName;
    private String ktbsResourceUri;  
    private String ktbsResourceLabel;  
    private String obselTypeLocalName;
    private String obselTypeLabel;
    private AttributeType attributeType;
    private String attributeTypeLocalName;
    private String attributeTypeRange;
    private String typeAttributeType;
    
    private File upload;// The actual file
    private String uploadContentType; // The content type of the file
    private String uploadFileName; // The uploaded file name
    private String traceModelLabel;// The caption of the file entered by user
    
    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Action(value = "/management_model",
	    results = {
		    @Result(location = "model/management.jsp", name = "success")
		  }
    )
    @Override
    public String execute() throws Exception {
        // TODO Auto-generated method stub
        return super.execute();
    }
    
    @Action(value = "/trace_model_csv",
	    results = {
		    @Result(location = "model/create/csv.jsp", name = "success")
		  }
    )
    public String trace_model_csv() {
        return SUCCESS;
    }
    
    public List<String> getListXsdType(){
	List<String> listXsdType = new ArrayList<String>();
	for (EnumXSDType enumXSDType : EnumXSDType.values()) {
	    listXsdType.add(enumXSDType.getLabel());
	} 
	return listXsdType;
    }
    
    @Action(value = "/updateResource",
	    results = {
		    @Result(location = "empty.jsp", name = "success")
		  },
		interceptorRefs ={@InterceptorRef(value ="roles",params={"allowedRoles", "expert"}),
		@InterceptorRef("defaultStack")})
    public String updateResource()  {
	setKtbsResource(searchResource(ktbsResourceUri));
	Set<String> set = new HashSet<String>();
	set.add(ktbsResourceLabel);
	getKtbsResource().setLabels(set);
	if(getKtbsResource() instanceof IObselType){
	    ITraceModel traceModel = (ITraceModel)getKtbsResource().getParentResource();
	   traceModel.get(getKtbsResource().getUri()).setLabels(set);
	   SingleKtbs.getInstance().getRoot().getResourceService().saveResource(traceModel);
	}else if(getKtbsResource() instanceof IAttributeType){
	    ITraceModel traceModel = (ITraceModel)getKtbsResource().getParentResource();
	    IAttributeType updateAttributeType = (IAttributeType)getKtbsResource();
	    updateAttributeType.setLabels(set);
	    Set<IUriResource> ranges = new HashSet<IUriResource>();
	    ranges.add(new UriResource(KtbsConstants.NAMESPACE_XSD +attributeTypeRange));
	    updateAttributeType.setRanges(ranges);
	    SingleKtbs.getInstance().getRoot().getResourceService().saveResource(traceModel);
	}else{
	    getKtbsResource().addProperty("http://liris/labelTrace", "Un label par défaut");
	    SingleKtbs.getInstance().getRoot().getResourceService().saveResource(getKtbsResource());
	    
	}
	SingleKtbs.getInstance().refresh(getKtbsResource());
	return SUCCESS;
    }
    @Action(value = "/deleteResource",
	    results = {
	    @Result(location = "empty.jsp", name = "success")
    },
	interceptorRefs ={@InterceptorRef(value ="roles",params={"allowedRoles", "expert"}),
		@InterceptorRef("defaultStack")})
    public String deleteResource()  {
	setKtbsResource(searchResource(ktbsResourceUri));
	
	if(getKtbsResource() instanceof IObselType){
	    ITraceModel traceModel = (ITraceModel)getKtbsResource().getParentResource();
	    removeFromListUriRessource(traceModel.getObselTypes(),getKtbsResource());
	    List<IAttributeType> attributeTypes = SingleKtbs.getInstance()
	    	.getListAttributeTypeOfObselType(traceModel, (IObselType)getKtbsResource());
	    for(int i=0;i<attributeTypes.size();i++){
		if(attributeTypes.get(i).getDomains() != null){
		    attributeTypes.get(i).getDomains().remove(getKtbsResource());
		    removeFromListUriRessource(traceModel.getAttributeTypes(),attributeTypes.get(i));
		}
	    }
	    SingleKtbs.getInstance().getRoot().getResourceService().saveResource(traceModel);
	}else if(getKtbsResource() instanceof IAttributeType){
	    ITraceModel traceModel = (ITraceModel)getKtbsResource().getParentResource();
	    removeFromListUriRessource(traceModel.getAttributeTypes(), getKtbsResource());
	    SingleKtbs.getInstance().getRoot().getResourceService().saveResource(traceModel);
	}else{
	    SingleKtbs.getInstance().getRoot().getResourceService().deleteResource(getKtbsResource());
	}
	ktbsResourceUri = null;
	SingleKtbs.getInstance().refresh(SingleKtbs.getInstance().getCurrentBase());
	return SUCCESS;
    }
    
    private void removeFromListUriRessource( Set<? extends IUriResource> uriResources, IUriResource uriResource){
	List<IUriResource> uriResourcesList = new ArrayList<IUriResource>(uriResources);
	for(int i =0;i<uriResourcesList.size();i++){
	    IUriResource iUriResource = uriResourcesList.get(i);
	    if(iUriResource.getUri().equals(uriResource.getUri())){
		uriResources.remove(iUriResource);
	    }
	}
    }
    
    
    @Action(value = "/list_trace_model",
	    results = {
		    @Result(location = "model/read/list.jsp", name = "success")
		  }
    )
    public String read() {
	nodes = new TreeNode("Root");
	nodes.setChildren(new LinkedList<TreeNode>());
	nodes.setId("root");
	nodes.setType("root");
	for (IBase base : getBases()) {
	    TreeNode nodeBase = new TreeNode(base.getLocalName()+" (Base)"); 
	    nodeBase.setId(formatUri(base.getUri()));
	    nodeBase.setState(TreeNode.NODE_STATE_OPEN);
	    nodeBase.setType("root");
	    nodeBase.setChildren(new LinkedList<TreeNode>());
	    if(base.getTraceModels() != null && !base.getTraceModels().isEmpty()){
		for (ITraceModel traceModel : base.getTraceModels()) {
		    TreeNode nodeTraceModel = new TreeNode(traceModel.getLabel()+" (Modèle de trace)");
		    nodeTraceModel.setType("folder");
		    //nodeTraceModel.setId("traceModel" + String.valueOf(nodes.getChildren().size()));
		    nodeTraceModel.setId(formatUri(traceModel.getUri()));
		    nodeTraceModel.setChildren(new LinkedList<TreeNode>());
		    for (IObselType obselType : traceModel.getObselTypes()) {
			TreeNode nodeObselType = new TreeNode(obselType.getLabel()+" (Type observé)");
			nodeObselType.setId(formatUri(obselType.getUri()));
			nodeObselType.setChildren(new LinkedList<TreeNode>());
			List<IAttributeType> listAttributeType = getAttributeFromObselType(traceModel, obselType);
			for (IAttributeType attributeType : listAttributeType) {
			    TreeNode nodeAttributeType = new TreeNode();
			    nodeAttributeType.setType("file");
			    if(!StringUtils.isEmpty(attributeType.getLabel())){
				nodeAttributeType.setTitle(attributeType.getLabel()+" (Type attribut)");
			    }else{
				nodeAttributeType.setTitle(attributeType.getLocalName()+" (Type attribut)");
			    }
			    nodeAttributeType.setId(formatUri(attributeType.getUri()));
			    nodeAttributeType.setChildren(new LinkedList<TreeNode>());
			    //for (String range : attributeType.getRanges()) {
			    //TreeNode nodeRange = new TreeNode(range);
			    //nodeRange.setId(formatUri(range));
			    //nodeAttributeType.getChildren().add(nodeRange);
			    //}
			    nodeObselType.getChildren().add(nodeAttributeType);
			}
		
			nodeTraceModel.getChildren().add(nodeObselType);
		    }
	    
		    nodeBase.getChildren().add(nodeTraceModel);    
		}
	    }
	    nodes.getChildren().add(nodeBase);
	}
	
       return SUCCESS;
    }
    
    public String getLabel(KtbsResource ktbsResource){
	return ktbsResource.getLabel();
    }
    
    public boolean getIsTraceModel(){
	return (ktbsResource instanceof ITraceModel);
    }
    public boolean getIsObselType(){
	return (ktbsResource instanceof IObselType);
    }
    
    public boolean getIsAttribute(){
	return (ktbsResource instanceof IAttributeType);
    }
    
    public boolean getIsBase(){
	return (ktbsResource instanceof IBase);
    }
    
    public String getLocalnameAttributeType(String attributeRange){
	if(attributeRange != null){
	    return StringUtils.substringAfter(attributeRange, "#");
	}else{
	    return EnumXSDType.STRING.getLabel();
	}
    }
    @Action(value = "/model_update_item",
	    results = {
		    @Result(location = "model/update/item.jsp", name = "success")
		  }
    )    
    public String model_update_item(){
	setKtbsResource(searchResource(uri));
	return SUCCESS;
    }
    
    private KtbsResource searchResource(String uri) {
	if(parseUri(uri).startsWith("http://www.w3.org/2000/01/rdf-schema")){
	    return null;
	}
	KtbsResource ktbsResource = (KtbsResource)SingleKtbs.getInstance().getRoot().getResourceService().getResource(parseUri(uri));
	//moche mais utile pour les AttributeType
	if(ktbsResource == null){
	    ktbsResource = (KtbsResource)SingleKtbs.getInstance().getRoot().getResourceService().getResource(parseUri(uri), IAttributeType.class);
	}
	return ktbsResource;

    }
    
    @Action(value = "/modelUploadCsv",
	    results = {
	    @Result(location = "model/management.jsp", name = "success")
	  },
		interceptorRefs ={@InterceptorRef(value ="roles",params={"allowedRoles", "expert"}),
		@InterceptorRef("defaultStack")})
    public String modelUploadCsv() {
	ITraceModel traceModel = SingleKtbs.getInstance().getTraceModel(SingleKtbs.getInstance().getCurrentBase() + getTraceModelLocalName());
	if(getUploadFileName() != null){
		try {
		    
			if(!FilenameUtils.getExtension(getUploadFileName()).equals("csv")){
				List<Object> ext = new ArrayList<Object>();
				ext.add(FilenameUtils.getExtension(getUploadFileName()));
				addActionError(getText("bad.extention",ext));
			}else{
			    TraceModelManagement traceModelManagement = new TraceModelManagement(upload);
			    if(traceModelManagement.checkTraceModel()){
				addActionMessage(getText("model.compliant"));
				if(traceModelManagement.writeToKtbs(traceModel)){
				    addActionMessage(getText("model.injected"));
				    String fullFileName = MTraceConst.WORK_CSV_PATH + File.separator + getUploadFileName();
				    File theFile = new File(fullFileName);
				    FileUtils.copyFile(upload, theFile);
				}else{
				    addActionError(getText("failed.injection.ktbs"));    
				}
			    }else{
				addActionError(getText("model.uncompliant"));    
			    }
			}
		} catch (Exception e) {
			addActionError(e.getMessage());
			e.printStackTrace();
		}
	}
        return SUCCESS;
    }
    
    @Action(value = "/modelCreate",
	    results = {
	    @Result(location = "model/management.jsp", name = "success")
	  },
		interceptorRefs ={@InterceptorRef(value ="roles",params={"allowedRoles", "expert"}),
		@InterceptorRef("defaultStack")})
    public String modelCreate() {
	if(getTraceModelLabel() != null){
	    SingleKtbs.getInstance().createTraceModel(null, getTraceModelLabel());
	}
        return SUCCESS;
    }
    
    
    @Action(value = "/create_TraceModel_obselType",
	    results = {
		    @Result(location = "model/management.jsp", name = "success")
		  },
			interceptorRefs ={@InterceptorRef(value ="roles",params={"allowedRoles", "expert"}),
		@InterceptorRef("defaultStack")})
    public String create_TraceModel_obselType(){
	ktbsResource = (KtbsResource)SingleKtbs.getInstance().getRoot().getResourceService().getResource(ktbsResourceUri, IKtbsResource.class);
	if(ktbsResource instanceof ITraceModel){
	    ITraceModel traceModel = ((ITraceModel)ktbsResource);
	    IObselType obselTypeCreated = SingleKtbs.getInstance().createObselType(traceModel, obselTypeLocalName, obselTypeLabel);
	    if(obselTypeCreated != null){
		addActionMessage(getText("obseltype.create.success", (obselTypeCreated.getLabel())));
		//((CachingResourceService)SingleKtbs.getInstance().getRoot().getResourceService()).clearCache();
	    }else{
		addActionError(getText("obseltype.create.failed", (obselTypeLabel)));
	    }
	}
	return SUCCESS;
    }
    
    @Action(value = "/create_TraceModel_attributeType",
	    results = {
		    @Result(location = "model/management.jsp", name = "success")
		  },
			interceptorRefs ={@InterceptorRef(value ="roles",params={"allowedRoles", "expert"}),
		@InterceptorRef("defaultStack")})
    public String create_TraceModel_attributeType(){
	ktbsResource = (KtbsResource)SingleKtbs.getInstance().getRoot().getResourceService().getResource(ktbsResourceUri, IObselType.class);
	ITraceModel traceModel = (ITraceModel) ((IObselType) ktbsResource)
		.getParentResource();
	IAttributeType attributeTypeCreated = SingleKtbs.getInstance()
		.createAttributeType(traceModel, (IObselType) ktbsResource,
			String.valueOf(new Date().getTime()),
			typeAttributeType, attributeTypeLocalName);
	if (attributeTypeCreated != null) {
	    addActionMessage(getText("attributeType.create.success",
		    (attributeTypeCreated.getLabel())));
	} else {
	    addActionError(getText("attributeType.create.failed",
		    (attributeTypeLocalName)));
	}
	return SUCCESS;
    }
    
    private String formatUri(String uri){
	return uri
		.replaceAll("/", "slash")
		.replaceAll(":", "twopoints")
		.replaceAll("#", "diese")
		.replaceAll("-", "tiret");
    }
    
    private String parseUri(String uri){
	return uri
		.replaceAll("slash", "/")
		.replaceAll("twopoints", ":")
		.replaceAll("diese", "#")
		.replaceAll("tiret", "-");
    }
    
//    public Map<String, Map<String, List<IAttributeType>>> getTraceModels(){
//	Map<String, Map<String, List<IAttributeType>>> map = new HashMap<String, Map<String,List<IAttributeType>>>();
//	Map<String, List<IAttributeType>> mapObselType = new HashMap<String, List<IAttributeType>>();
//	List<IAttributeType> listAttributeType = new ArrayList<IAttributeType>();
//	
//	for (ITraceModel traceModel : SingleKtbs.getInstance().getListTraceModelOfCurrentBase()) {
//	    String traceModelLocalName = traceModel.getLocalName();
//	    for (IObselType obselType : traceModel.getObselTypes()) {
//		    String obselTypeLocalName = obselType.getLocalName();
//		    listAttributeType = SingleKtbs.getInstance().getListAttributeTypeOfObselType(obselType);
//		    mapObselType.put(obselTypeLocalName, listAttributeType);
//	    }
//	    map.put(traceModelLocalName, mapObselType);
//	}
//	return map;
//    }
    
    public List<IAttributeType> getAttributeFromObselType(ITraceModel model, String obselType){
	return SingleKtbs.getInstance().getListAttributeTypeOfObselType(model, obselType);
    }

    public List<IAttributeType> getAttributeFromObselType(ITraceModel model, IObselType obselType){
	return SingleKtbs.getInstance().getListAttributeTypeOfObselType(model, obselType);
    }
    
    public String getUri() {
        return uri;
    }


    public void setUri(String uri) {
        this.uri = uri;
    }


    public void setNodes(TreeNode nodes) {
	this.nodes = nodes;
    }


    public TreeNode getNodes() {
	return nodes;
    }


    public void setKtbsResource(KtbsResource ktbsResource) {
	this.ktbsResource = ktbsResource;
    }


    public KtbsResource getKtbsResource() {
	return ktbsResource;
    }


    public void setAttributeType(AttributeType attributeType) {
	this.attributeType = attributeType;
    }


    public AttributeType getAttributeType() {
	return attributeType;
    }

    public void setKtbsResourceUri(String ktbsResourceUri) {
	this.ktbsResourceUri = ktbsResourceUri;
    }


    public String getKtbsResourceUri() {
	return ktbsResourceUri;
    }


    public void setObselTypeLocalName(String obselTypeLocalName) {
	this.obselTypeLocalName = obselTypeLocalName;
    }


    public String getObselTypeLocalName() {
	return obselTypeLocalName;
    }


    public void setObselTypeLabel(String obselTypelabel) {
	this.obselTypeLabel = obselTypelabel;
    }


    public String getObselTypeLabel() {
	return obselTypeLabel;
    }


    public String getAttributeTypeLocalName() {
        return attributeTypeLocalName;
    }


    public void setAttributeTypeLocalName(String attributeTypeLocalName) {
        this.attributeTypeLocalName = attributeTypeLocalName;
    }


    public String getTypeAttributeType() {
        return typeAttributeType;
    }


    public void setTypeAttributeType(String typeAttributeType) {
        this.typeAttributeType = typeAttributeType;
    }

    public void setKtbsResourceLabel(String ktbsResourceLabel) {
	this.ktbsResourceLabel = ktbsResourceLabel;
    }

    public String getKtbsResourceLabel() {
	return ktbsResourceLabel;
    }

    public void setUploadContentType(String uploadContentType) {
	this.uploadContentType = uploadContentType;
    }

    public String getUploadContentType() {
	return uploadContentType;
    }

    public void setUploadFileName(String uploadFileName) {
	this.uploadFileName = uploadFileName;
    }

    public String getUploadFileName() {
	return uploadFileName;
    }

    public void setTraceModelLabel(String traceModelLabel) {
	this.traceModelLabel = traceModelLabel;
    }

    public String getTraceModelLabel() {
	return traceModelLabel;
    }

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public void setTraceModelLocalName(String traceModelLocalName) {
	this.traceModelLocalName = traceModelLocalName;
    }

    public String getTraceModelLocalName() {
	return traceModelLocalName;
    }

    public void setAttributeTypeRange(String attributeTypeRange) {
	this.attributeTypeRange = attributeTypeRange;
    }

    public String getAttributeTypeRange() {
	return attributeTypeRange;
    }
    
//    @Action(value = "/select_trace_model",
//	    results = {
//		    @Result(location = "model/create/ihm.jsp", name = "success")
//		  }
//    )
//    public String selectModel() throws Exception {
//	setSelectedTab(1);
//	if(!StringUtils.isEmpty(getTraceModelSelectedLocalName())) {
//	    String uriTraceModel = SingleKtbs.getInstance().getCurrentBase().getUri()+getTraceModelSelectedLocalName()+"/";
//	    setTraceModelSelected(SingleKtbs.getInstance().getTraceModel(uriTraceModel));
//	}
//       return SUCCESS;
//    }
    
}
