package org.liris.mTrace.kTBS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.liris.ktbs.client.Ktbs;
import org.liris.ktbs.client.KtbsClient;
import org.liris.ktbs.client.KtbsConstants;
import org.liris.ktbs.domain.AttributeType;
import org.liris.ktbs.domain.MethodParameter;
import org.liris.ktbs.domain.ObselType;
import org.liris.ktbs.domain.UriResource;
import org.liris.ktbs.domain.interfaces.IAttributeType;
import org.liris.ktbs.domain.interfaces.IBase;
import org.liris.ktbs.domain.interfaces.IComputedTrace;
import org.liris.ktbs.domain.interfaces.IKtbsResource;
import org.liris.ktbs.domain.interfaces.IMethod;
import org.liris.ktbs.domain.interfaces.IMethodParameter;
import org.liris.ktbs.domain.interfaces.IObselType;
import org.liris.ktbs.domain.interfaces.IPropertyStatement;
import org.liris.ktbs.domain.interfaces.IStoredTrace;
import org.liris.ktbs.domain.interfaces.ITrace;
import org.liris.ktbs.domain.interfaces.ITraceModel;
import org.liris.ktbs.service.CachingResourceService;
import org.liris.mTrace.AbstractApplication;
import org.liris.mTrace.tools.DefaultAttributeType;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

public class SingleKtbs {

    /**.
     * 
     */
    private static final String USER_STAGIAIRE = "stagiaire";
    /**.
     * 
     */
    private static Logger log = new LoggerContext().getLogger(SingleKtbs.class);
    private static SingleKtbs instance = null;
    private SingleKtbs() {}
    public final synchronized static SingleKtbs getInstance() {
        if (instance == null) 
            instance = new SingleKtbs();
        return instance;
    } 
    public void destroy(){
    	setRoot(null);
    }
    public void initialize(AbstractApplication abstractApplication){
    	//setRoot(Ktbs.getRestClient("http://localhost:8001/"));
	setRoot(Ktbs.getRestCachingClient("http://localhost:8001/",10000, 3600000L ));
    }
    

    private KtbsClient root;

    public void setRoot(KtbsClient root) {
	this.root = root;
    }

    public KtbsClient getRoot() {
	return root;
    }
    
    public IBase getCurrentBase() {
	String userName = ServletActionContext.getRequest().getUserPrincipal().getName();
	IBase base = this.root.getResourceService().getRoot().get(userName);
	    if(base == null){
	    	base = getBase(userName,userName,userName,true);
	    }
	return root.getResourceService().getBase(userName);
    }

//    public ITraceModel getFirstMTraceModelOfCurrentBase() {
//	ITraceModel traceModelRet = null;
//	if (getCurrentBase() != null
//		&& getCurrentBase().getTraceModels() != null
//		&& !getCurrentBase().getTraceModels().isEmpty()) {
//	    for (ITraceModel traceModel : getCurrentBase().getTraceModels()) {
//		if (traceModel.getPropertyValue("level").equals("0")) {
//		    traceModelRet = traceModel;
//		    break;
//		}
//	    }
//	}
//	return traceModelRet;
//    }
	/**
	 * Create a base and return its uri
	 * @param baseUri
	 * @param baseLabel
	 * @return
	 * @throws ResourceLoadException
	 */
	public IBase getBase(String baseUri) {
		IBase base = root.getResourceService().getBase(baseUri);
		if( base != null){
			log.info("Base "+baseUri + " exists.");
		} else {
			log.info("Base "+baseUri + " doesn't exists.");
		}
		return  base;
	}
	
	/**
	 * Create a base and return its uri
	 * @param baseUri
	 * @param baseLabel
	 * @return
	 * @throws ResourceLoadException
	 */
	public IBase getBase(String baseUri,String owner, String baseLabel, boolean createIfNotExist) {
		IBase base = getBase(baseUri);
		if( base == null){
			if(createIfNotExist){
				String baseID = root.getResourceService().newBase(baseUri);
				base = root.getResourceService().getBase(baseID);
				if(base != null){
					log.info("Base "+baseUri + " created");	
				}
				base.addLabel(baseLabel);
				base.setOwner(owner);
				if(root.getResourceService().saveResource(base)){
					log.info("Base "+baseUri + " modified");	
				}else{
					log.warn("Base "+baseUri + " not created");	
				}
				refresh(base);
			}
		}
		return  base;
	}
	
	public ITraceModel addMetadata(ITraceModel traceModel, String metadataName, String metadataType){
	    //TODO: vérifier comment gérer les properties ua niveau metadata du modèle de trace
	    traceModel.addProperty(KtbsConstants.METADATA+metadataName, KtbsConstants.NAMESPACE_XSD + metadataType.toLowerCase().replace("time", "Time"));
	    if(root.getResourceService().saveResource(traceModel)){
		log.info("TraceModel updated with property "+metadataName + ", "+metadataType);
	    }
	    return traceModel;
	}
	
	public ITraceModel createTraceModel(IBase base, String traceModelLocalName, String modelLabel){
	    if(StringUtils.isEmpty(traceModelLocalName)){
		traceModelLocalName = String.valueOf(new Date().getTime());
	    }
	    String traceModelUri = root.getResourceService().newTraceModel(base.getUri(), traceModelLocalName);
	    ITraceModel traceModel = root.getResourceService().getTraceModel(traceModelUri); 
	    traceModel.addLabel(modelLabel);
	    if(root.getResourceService().saveResource(traceModel)){
		log.info("TraceModel "+traceModel.getLocalName() + " created");
	    }
	    refresh(base);
	    return traceModel;
	}
	
	public ITraceModel createTraceModel(String traceModelLocalName, String modelLabel){
	    return createTraceModel(getCurrentBase(), traceModelLocalName, modelLabel);
	}
	
	/**
	 * Create a TraceModel and return its uri
	 * @param baseUri
	 * @param traceModelUri
	 * @param modelLabel
	 * @return
	 * @throws ResourceLoadException
	 */
	public ITraceModel getTraceModel(IBase base, String traceModelLocalName, String modelLabel,String level,boolean createIfNotExist)  {
		
		ITraceModel traceModel = getTraceModel(base, traceModelLocalName, modelLabel,createIfNotExist);
		if (traceModel != null) {
			traceModel.addProperty("level", level);
			root.getResourceService().saveResource(traceModel);
			if(traceModel != null){
				log.info("TraceModel "+traceModelLocalName + " modified");
			}
		}
		return  traceModel;
	}
	
	/**
	 * Create a TraceModel and return its uri
	 * @param baseUri
	 * @param traceModelUri
	 * @param modelLabel
	 * @return
	 * @throws ResourceLoadException
	 */
	public ITraceModel getTraceModel(IBase base, String traceModelLocalName, String modelLabel,boolean createIfNotExist)  {
		
		ITraceModel traceModel = getTraceModel(base + traceModelLocalName);
		if (traceModel == null) {
			if(createIfNotExist){
			    createTraceModel(base, traceModelLocalName, modelLabel);
			}
		}
		return  traceModel;
	}
	
	/**
	 * Create a TraceModel and return its uri
	 * @param baseUri
	 * @param traceModelUri
	 * @param modelLabel
	 * @return
	 * @throws ResourceLoadException
	 */
	public ITraceModel getTraceModel(String traceModelUri)  {
		ITraceModel traceModel = root.getResourceService().getTraceModel(traceModelUri);
		if (traceModel != null) {
			log.info("TraceModel "+traceModelUri + " exists.");
		} else {
			log.info("TraceModel "+traceModelUri + " doesn't exists.");
		}
		return traceModel;
	}
	
	/**
	 * Return all traceModel of the currentBase
	 * 
	 */
	public Set<ITraceModel> getListTraceModel()  {
	    if(getCurrentBase()!=null){
		return getCurrentBase().getTraceModels();
	    }
	    return null;
	}
	
	/**
	 * Return all traceModel of the current root
	 * 
	 */
	public Set<ITraceModel> getListTraceModelFull()  {
	    
	    Set<ITraceModel> listTraceModel = new HashSet<ITraceModel>();
	    
	    Set<IBase> baseList = root.getResourceService().getRoot().getBases();
	    for (IBase iBase : baseList) {
		if(!iBase.getLocalName().equals(USER_STAGIAIRE))
		listTraceModel.addAll(iBase.getTraceModels());
	    }
	    
	    return listTraceModel;
	}
	
	/**
	 * Create a TraceModel and return its uri
	 * @param baseUri
	 * @param traceModelUri
	 * @param modelLabel
	 * @return
	 * @throws ResourceLoadException
	 */
	public Set<ITraceModel> getListTraceModel(String baseUri)  {
		return root.getResourceService().getBase(baseUri).getTraceModels();
	}
	
	/**
	 * Create storedTrace and return its uri
	 * @param baseUri
	 * @param storedTraceUri
	 * @param modelUri
	 * @param traceOrigin
	 * @param traceLabel
	 * @return
	 */
//	public IStoredTrace getStoredTrace(String baseUri, String storedTraceName, String modelUri, String traceOrigin, String traceLabel, String level,String traceBeginDT, String traceEndDT,String subject, boolean createIfNotExist){
//		IStoredTrace storedTrace = root.getResourceService().getStoredTrace(baseUri + storedTraceName + "/");
//		if (storedTrace != null) {
//			log.info("StoredTrace "+storedTraceName + " exists.");
//		} else {
//			if(createIfNotExist){
//				String storedTraceId = root.getResourceService().newStoredTrace(
//					baseUri,
//					storedTraceName,
//					modelUri,
//					traceOrigin,
//					null,
//					traceBeginDT,
//					null,
//					traceEndDT,
//					subject);
//				if(storedTraceId != null){
//					storedTrace = root.getResourceService().getStoredTrace(storedTraceId);
//					log.info("Stored trace "+storedTraceId + " created");
//					storedTrace.addLabel(traceLabel);
//					if(root.getResourceService().saveResource(storedTrace)){
//						log.info("StoredTrace "+storedTraceId + " updated.");
//					} else {
//						log.info("StoredTrace "+storedTraceId + " doesn't updated.");
//					}
//				}
//			}
//		}
//		return storedTrace;
//	}
	
	/**
	 * Create storedTrace and return its uri
	 * @param baseUri
	 * @param storedTraceUri
	 * @param modelUri
	 * @param traceOrigin
	 * @param traceLabel
	 * @return
	 */
	public IStoredTrace createStoredTrace(String storedTraceName, String modelUri, String traceOrigin, String traceLabel, String level,String traceBeginDT, String traceEndDT,String subject){
	    
	    if(StringUtils.isEmpty(storedTraceName)){
		storedTraceName = String.valueOf(new Date().getTime());
	    }
	    
		IStoredTrace storedTrace = root.getResourceService().getStoredTrace(getCurrentBase().getUri() + storedTraceName + "/");
		if (storedTrace != null) {
			log.info("StoredTrace "+storedTraceName + " exists.");
		} else {
			
            	    String storedTraceId = root.getResourceService().newStoredTrace(
            		getCurrentBase().getUri(),
				storedTraceName,
				modelUri,
				traceOrigin,
				null,
				traceBeginDT,
				null,
				traceEndDT,
				subject);
            	    if (storedTraceId != null) {
            		storedTrace = root.getResourceService().getStoredTrace(
            			storedTraceId);
            		log.info("Stored trace " + storedTraceId + " created");
            		storedTrace.addLabel(traceLabel);
            		if (root.getResourceService().saveResource(storedTrace)) {
            		    log.info("StoredTrace " + storedTraceId + " updated.");
            		} else {
            		    log.info("StoredTrace " + storedTraceId
            			    + " doesn't updated.");
            		}
            	    }
			
		}
		return storedTrace;
	}
	
	public IObselType createObselType(ITraceModel traceModel, String obselTypeLocalName, String label) {
		
		ObselType obselTypeToCreate = new ObselType();
		if(obselTypeLocalName == null){
		    obselTypeLocalName = "_"+String.valueOf(new Date().getTime());
		}
		obselTypeToCreate.setUri(traceModel.getUri() + obselTypeLocalName);
		obselTypeToCreate.addLabel(label);
		traceModel.getObselTypes().add(obselTypeToCreate);
		root.getResourceService().saveResource(traceModel);
		
		refresh(traceModel);
		log.info("ObselType "+obselTypeToCreate.getUri() + " created");
		
		
		return obselTypeToCreate;
	}
	
	/**
	 * 
	 * @param traceModelUri
	 * @param obselTypeLocalName
	 * @return
	 */
	public IObselType getObselType(ITraceModel traceModel, String obselTypeLocalName) {

		List<IObselType> obselTypes = new ArrayList<IObselType>(traceModel.getObselTypes());
		
		for (IObselType iObselType : obselTypes) {
			if(iObselType.getLocalName().equals(obselTypeLocalName)){
				return iObselType;
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param modelUri
	 * @param obselTypeName
	 * @param attributeTypeUri
	 * @return
	 */
	public IAttributeType createAttributeType(ITraceModel traceModel, IObselType obselType,String attributeTypeLocalName, String typeAttributeType, String label){
		
		IAttributeType attributeType = null;
//		List<IAttributeType> attributesType = new ArrayList<IAttributeType>(traceModel.getAttributeTypes());
//			
//			for (IAttributeType iAttributeType : attributesType) {
//				if(iAttributeType.getLabel().equals(label)){
//					attributeType = iAttributeType; 
//					break; 
//				}
//			}
			
			if(attributeType == null){
				attributeType = new AttributeType();
				((AttributeType)attributeType).setUri(traceModel.getUri() + attributeTypeLocalName);
				attributeType.getRanges().add( new UriResource(KtbsConstants.NAMESPACE_XSD + typeAttributeType.toLowerCase().replace("time", "Time")));
				attributeType.addLabel(label);
			}
			
			attributeType.getDomains().add(obselType);
			traceModel.getAttributeTypes().add(attributeType);
			
			root.getResourceService().saveResource(traceModel);
			
			refresh(traceModel);
			
			log.info("ObselType "+attributeType.getUri() + " created");
			
			
		
		return attributeType;
		
	}
	
	public List<ITrace> getTraces(String baseUri){
		List<ITrace> iTraces = new ArrayList<ITrace>();
		iTraces.addAll(root.getResourceService().getBase(baseUri).getStoredTraces());
		iTraces.addAll(root.getResourceService().getBase(baseUri).getComputedTraces());
		Collections.sort(iTraces);
		return iTraces;
	}
	
	/**
	 * Create method filter and return its url
	 * @param baseUri
	 * @param methodUri
	 * @param methodLabel
	 * @param start
	 * @param end
	 * @return
	 */
	
	public IMethod createMethodFilter(String baseUri, String methodLocalName, String methodLabel, String start, String end ){
		
		IMethod method = root.getResourceService().getMethod(methodLocalName);
		if (method != null) {
			System.out.println("Method filter "+methodLocalName + " exists.");
		} else {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("start", start);
			parameters.put("finish", end);
			String methodUri = root.getResourceService().newMethod(baseUri, methodLocalName, KtbsConstants.FILTER, parameters);
			if(methodUri != null){
				System.out.println("Method filter "+methodUri + " created");
			}
		}
		return method;
	}
	
	public IMethod getMethodWithLocalName(String methodLocalName){
		
		IMethod method = null;
		for (IBase base : root.getResourceService().getRoot().getBases()) {
		    if(methodLocalName != null && base.getMethods() != null){
			List<IMethod> methods = new ArrayList<IMethod>(base.getMethods());
			for (IMethod iMethod : methods) {
				if(iMethod.getLocalName().equals(methodLocalName)){
					method=iMethod;
					break;
				}
			}
		    }
		}
		
		return method;
	}
	
	public IMethod getMethod(String methodUri){
		
		IMethod method = null;
		if(methodUri != null){
			method = root.getResourceService().getMethod(methodUri);
		}
		if (method != null) {
			System.out.println("Method "+methodUri + " exists.");
		}else{
			System.out.println("Method "+methodUri + " doesn't exists.");
		}
		
		return method;
	}
	
	/**
	 * Create method sparql and return its url
	 * @param baseUri
	 * @param methodUri
	 * @param methodLabel
	 * @param start
	 * @param end
	 * @return
	 */
	public IMethod createMethodKtbs(String baseUri, String methodLocalName, String methodLabel, 
			String sparqlRequest,String description, ITraceModel traceModelDestination, String inherits ){
		
		String methodUri = null;
		IMethod method = null;
		if(methodLocalName != null){
			method = root.getResourceService().getMethod(methodLocalName);
		}else{
		    methodLocalName  = "_"+String.valueOf(new Date().getTime());
		}
		if (method != null) {
			System.out.println("Method "+methodLocalName + " exists.");
		} else {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(KtbsConstants.PARAMETER_SPARQL, sparqlRequest);
			parameters.put(KtbsConstants.PARAMETER_MODEL, traceModelDestination.getUri());
			methodUri = root.getResourceService().newMethod(baseUri, methodLocalName, inherits, parameters);
			if(methodUri != null){
				System.out.println("Method filter "+methodUri + " created");
				if(!StringUtils.isEmpty(methodLabel)){
					method  = root.getResourceService().getMethod(methodUri);
					method.addLabel(methodLabel);
					method.addProperty(KtbsConstants.COMMENT, description);
					root.getResourceService().saveResource(method);
				}
			}
		}
		if(methodUri != null){
			method  = root.getResourceService().getMethod(methodUri);
		}
		
		
		return method;
	}
	
	/**
	 * Create method sparql and return its url
	 * @param methodLocalName
	 * @param methodLabel
	 * @param sparqlRequest
	 * @return
	 */
	public IMethod createMethodKtbs(String methodLocalName, String methodLabel, 
			String sparqlRequest,String description,ITraceModel traceModelDestination, String inherits ){
		
		return createMethodKtbs(getCurrentBase().getUri(), methodLocalName, methodLabel,sparqlRequest, description,traceModelDestination, inherits );
	}
	
	
	/**
	 * Create method filter and return its url
	 * @param baseUri
	 * @param methodUri
	 * @param methodLabel
	 * @param start
	 * @param end
	 * @return
	 */
	
	public IComputedTrace createComputedTrace(String baseUri, String computedTraceLocalName, String methodUri, Set<String> sourceTraces, Map<String,String> parameters, String label ){
		if(computedTraceLocalName == null){
		    computedTraceLocalName = String.valueOf(new Date().getTime());
		}
		IComputedTrace computedTrace = root.getResourceService().getComputedTrace(baseUri + computedTraceLocalName + "/");
		if (computedTrace != null) {
			log.info("StoredTrace "+computedTraceLocalName + " exists.");
		} else {
				String computedTraceUri = root.getResourceService().newComputedTrace(baseUri, computedTraceLocalName, methodUri, sourceTraces, parameters, label);
				if(computedTraceUri != null){
					log.info("Stored trace "+computedTraceUri + " created");
					computedTrace = root.getResourceService().getComputedTrace(computedTraceUri);
				}
			
		}
		refresh(computedTrace);
		return computedTrace;
	}
	/**
	 * Create method fusion and return its url
	 * @param baseUri
	 * @param methodUri
	 * @param methodLabel
	 * @param start
	 * @param end
	 * @return
	 */
	
	public IComputedTrace createFusionnedTrace(String baseUri, String computedTraceLocalName, Set<String> sourceTraces ){
	    if(computedTraceLocalName == null){
		computedTraceLocalName = String.valueOf(new Date().getTime());
	    }
	    if(baseUri == null){
		baseUri = getCurrentBase().getUri();
	    }
	    IComputedTrace computedTrace = root.getResourceService().getComputedTrace(baseUri + computedTraceLocalName + "/");
	    if (computedTrace != null) {
		log.info("StoredTrace "+computedTraceLocalName + " exists.");
	    } else {
		String computedTraceUri = root.getResourceService().newComputedTrace(baseUri, computedTraceLocalName,KtbsConstants.FUSION, sourceTraces, null);
		if(computedTraceUri != null){
		    log.info("Stored trace "+computedTraceUri + " created");
		    computedTrace = root.getResourceService().getComputedTrace(computedTraceUri);
		}
		
	    }
	    return computedTrace;
	}
	
	
	public String getRangeOfAttributeType(String attributeUri){
	    IAttributeType  attributeType = (IAttributeType)root.getResourceService().getResource(attributeUri, IAttributeType.class);
	    if( attributeType.getRanges().size() > 0){
		return attributeType.getRanges().toArray()[0].toString();
	    }
	    return null;
	}
	
//	public List<IAttributeType> getListAttributeTypeOfObselType(String traceModelUri, String obselTypeLabel){
//	    	ITraceModel traceModel = root.getResourceService().getResource(traceModelUri, ITraceModel.class);
//	    	Set<IObselType> obseltypes = traceModel.getObselTypes();
//	    	for (IObselType iObselType : obseltypes) {
//		    if(iObselType.getLabel().equals(obselTypeLabel)){
//			return getListAttributeTypeOfObselType(iObselType);
//		    }
//		}
//		 return null;
//	}
	
	public List<IAttributeType> getListAttributeTypeOfObselType(ITraceModel targetModel,IObselType obselType){
		 Set<IAttributeType> attributeTypes = targetModel.getAttributeTypes();
		 
		 List<IAttributeType> attributeTypes2 = new ArrayList<IAttributeType>();
		 DefaultAttributeType defaultAttributeType = new DefaultAttributeType(DefaultAttributeType.hasBegin);
		 attributeTypes2.add(defaultAttributeType);
		 defaultAttributeType = new DefaultAttributeType(DefaultAttributeType.hasEnd);
		 attributeTypes2.add(defaultAttributeType);
		 defaultAttributeType = new DefaultAttributeType(DefaultAttributeType.hasLabel);
		 attributeTypes2.add(defaultAttributeType);
		 for (IAttributeType iAttributeType : attributeTypes) {
			if(iAttributeType.getDomains().contains(obselType)){
				attributeTypes2.add(iAttributeType);
			}
		 }
		 return attributeTypes2;
	}
	
	public List<IAttributeType> getListAttributeTypeOfObselType(ITraceModel targetModel, String obselTypeUri){
	    IObselType obselTypeFound = null;
	    for (IObselType obseltype : targetModel.getObselTypes()) {
		    if(obseltype.getUri().equals(obselTypeUri)){
			obselTypeFound = obseltype;
			break;
		    }
		}
		return getListAttributeTypeOfObselType(targetModel, obselTypeFound);
	}
	
	public Set<IMethod> getMethodes() {
		return getCurrentBase().getMethods();
	}
	
	public Set<IMethod> getSuperMethods() {
	    Set<IMethod> superMethods = new HashSet<IMethod>();
	    for (IMethod method : getMethodes()) {
		if(method.getInherits().equals(KtbsConstants.SUPER_METHOD)){
		    superMethods.add(method);
		}
	    }
	    
	    return superMethods;
	}
	public HashSet<IBase> getListBase() {
		return (HashSet<IBase>)root.getResourceService().getRoot().getBases();
	}
	/**.
	 * Valeurise currentBase par la base dont user est owner
	 * @param name
	 */
//	public void setCurrentBase(String user) throws Exception{
//	    IBase base = this.root.getResourceService().getRoot().get(user);
//	    if(base == null){
//	    	base = getBase(user,user,user,true);
//	    }
//		setCurrentBase(base);
//	}
	/**.
	 * @return
	 */
	public List<ITrace> getTraces() {
	    return getTraces(getCurrentBase().getUri());
	}
	/**.
	 * @return
	 */
	public List<IKtbsResource> getResources(boolean isExpert) {
	    List<IKtbsResource> ktbsResources = new LinkedList<IKtbsResource>();
	    for(IBase base : getListBase()){
		if(isExpert){
		    ktbsResources.add(base);
		    for(IMethod method : base.getMethods()){
			    ktbsResources.add(method);
			}
		}else{
		    if(!base.getLocalName().equals(USER_STAGIAIRE)){
			continue;
		    }
		}
		    
		
//		for(ITrace trace : base.getStoredTraces()){
//		    ktbsResources.add(trace);
//		}
//		for(ITrace trace : base.getComputedTraces()){
//		    ktbsResources.add(trace);
//		}
		for(ITrace trace : getTraces(base.getUri())){
		    ktbsResources.add(trace);
		}
//		for(ITraceModel traceModel : base.getTraceModels()){
//		    ktbsResources.add(traceModel);
//		    for(IObselType obselType : traceModel.getObselTypes()){
//			ktbsResources.add(obselType);
//			for(IAttributeType attributeType : getListAttributeTypeOfObselType(obselType)){
//			    ktbsResources.add(attributeType);
//			}
//		    }
//		}
	    }
	    return ktbsResources;
	}
	/**.
	 * @param traceModelUri
	 * @param obselLabel
	 * @return
	 */
	public IObselType getObselTypeUriByLabel(String traceModelUri,
		String obselLabel) {
	   ITraceModel traceModel = getRoot().getResourceService().getTraceModel(traceModelUri);
	   for (IObselType obselType : traceModel.getObselTypes()) {
	       if(obselType.getLabel().equals(obselLabel)){
		   return obselType;
	       }
	   }
	   return null;
	}
	/**.
	 * @param traceModelUri
	 * @param string
	 * @param string2
	 * @return
	 */
	public IAttributeType getAttributeTypeUriByLabel(String traceModelUri,
		String obselLabel, String attributeLabel) {
	    ITraceModel traceModel = getRoot().getResourceService().getTraceModel(traceModelUri);
	    for (IObselType obselType : traceModel.getObselTypes()) {
		if(obselType.getLabel().equals(obselLabel)){
		    for (IAttributeType attributeType : getListAttributeTypeOfObselType(traceModel,obselType)) {
			if(attributeType.getLabel() != null && attributeType.getLabel().equals(attributeLabel)){
			    attributeType.getLabel();
			    return attributeType; 
			}
		    }
		}
	    }
	    return null;
	}
	/**.
	 * @param uri
	 * @param ktbsString
	 * @return
	 */
	public IMethod updateMethodKtbs(String uri, String ktbsString, String label, String description, ITraceModel traceModelDestination,String inherits) {
	    IMethod method = root.getResourceService().getMethod(getCurrentBase().getUri() + uri);
	    if(method != null){
		Set<IMethodParameter> p = new HashSet<IMethodParameter>();
		for(IMethodParameter methodParameter:method.getMethodParameters()){ 
		    if(methodParameter.getName().equals(KtbsConstants.PARAMETER_SPARQL)){
			p.add(new MethodParameter(methodParameter.getName(), ktbsString));
		    }else{
			if(methodParameter.getName().equals(KtbsConstants.PARAMETER_MODEL)){
			    p.add(new MethodParameter(methodParameter.getName(), traceModelDestination.getUri()));    
			}else{
			    p.add(new MethodParameter(methodParameter.getName(), methodParameter.getValue()));
			}
		    }
		}
		method.setMethodParameters(p);
		if(!StringUtils.isEmpty(label)){
		    method.addLabel(label);
		}
		if(!StringUtils.isEmpty(description)){
		    method.addProperty(KtbsConstants.COMMENT, description);
		}
		
		if(!StringUtils.isEmpty(inherits)){
		    method.setInherits(inherits);
		}
		
		getRoot().getResourceService().saveResource(method);
	    }
	    return method;
	}
	/**.
	 * Création d'une super method
	 * @param rules
	 * @param name
	 * @param traceModelTarget
	 * @return
	 */
	public IMethod createOrUpdateSuperMethodKtbs(String rules,String localName, String label,ITraceModel traceModelTarget, String description) {
	    
	    	String methodUri = null;
		IMethod method = null;
		try{
		    if(localName != null){
			method = getMethod(getCurrentBase().getUri() + localName);
		    }
		}catch(Exception ex){
		    
		}
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(KtbsConstants.PARAMETER_SUBMETHODS, rules);
		if(traceModelTarget != null){
		    parameters.put(KtbsConstants.PARAMETER_MODEL, traceModelTarget.getUri());
		}
		if(method == null){
		    String methodLocalName  = "_"+String.valueOf(new Date().getTime());
		    methodUri = root.getResourceService().newMethod(getCurrentBase().getUri(), methodLocalName,
			    KtbsConstants.SUPER_METHOD, parameters);
		    method  = root.getResourceService().getMethod(methodUri);
		}else{
		    Set<IMethodParameter> p = new HashSet<IMethodParameter>();
//		    Iterator<IMethodParameter> itMethodParam = method.getMethodParameters().iterator();
//		    while(itMethodParam.hasNext()){
//			if(itMethodParam.next().equals("submethods") || itMethodParam.next().equals("model")){
//			    itMethodParam.remove();
//			}
//		    }
		    p.add(new MethodParameter(KtbsConstants.PARAMETER_SUBMETHODS, rules));
		    p.add(new MethodParameter(KtbsConstants.PARAMETER_MODEL, traceModelTarget.getUri()));
		    method.setMethodParameters(p);
		}
		
		if(!StringUtils.isEmpty(description)){
		    method.removeProperty(KtbsConstants.COMMENT);
		    method.addProperty(KtbsConstants.COMMENT, description);
		}
		if(!StringUtils.isEmpty(label)){
		    method.addLabel(label);
		}
		
		root.getResourceService().saveResource(method);
		refresh(getCurrentBase());
	    return method;
	}
	
	/**
	 * Duplication d'une super method et des méthodes associées.
	 * @param method
	 * @return
	 */
	public IMethod duplicateSuperMethodKtbs(IMethod method){
	    
	    //Suppression si elle existait déjà
	    IMethod methodToSup = getMethod(getCurrentBase().getUri() + method.getLocalName());
	    if(methodToSup != null){
		root.getResourceService().deleteResource(methodToSup);
	    }
	    
	    Map<String, String> parameter = new HashMap<String, String>();
	    for (IMethodParameter methodParameter : method.getMethodParameters()) {
		parameter.put(methodParameter.getName(), methodParameter.getValue());
		if(methodParameter.getName().equals(KtbsConstants.PARAMETER_SUBMETHODS)){
		    for (String methodLocalName : methodParameter.getValue().split(" ")) {
			duplicateSuperMethodKtbs(getMethodWithLocalName(methodLocalName));
		    }
		}
	    }
	    
	    String methodUri = root.getResourceService().newMethod(
		    getCurrentBase().getUri(),
		    method.getLocalName(),
		    method.getInherits(),
		    parameter);
	    IMethod newMethod = getMethod(methodUri);
	    for (String label : method.getLabels()) {
		newMethod.addLabel(label);
	    }
	    Set<IPropertyStatement> properties = new HashSet<IPropertyStatement>();
	    for (IPropertyStatement propertyStatement : method.getProperties()) {
		properties.add(propertyStatement);
	    }
	    newMethod.setProperties(properties);
	
	    root.getResourceService().saveResource(newMethod);
	    
	    return newMethod;

	}
	
	/**.
	 * 
	 */
	public void refresh(String uri) {
	    //((CachingResourceService)SingleKtbs.getInstance().getRoot().getResourceService()).removeFromCache(uri);
	    ((CachingResourceService)SingleKtbs.getInstance().getRoot().getResourceService()).clearCache();
	}
	
	public void refresh(IKtbsResource resource) {
	    ((CachingResourceService)SingleKtbs.getInstance().getRoot().getResourceService()).clearCache();
//	    if(resource instanceof IAttributeType || resource instanceof IObselType){
//		((CachingResourceService)SingleKtbs.getInstance().getRoot().getResourceService()).removeFromCache(resource.getParentResource().getParentResource());
//	    }else{
//		((CachingResourceService)SingleKtbs.getInstance().getRoot().getResourceService()).removeFromCache(resource);
//	    }
	}
	/**.
	 * @param string
	 * @param string2
	 */
	public IMethod createMethodExternalKtbs(String localName, String label) {
	    String methodUri = null;
	    IMethod method = null;
	    Map<String, String> parameters = new HashMap<String, String>();
	    ///usr/lib/jvm/java-6-openjdk/ => debian (linux)
	    ///cygdrive/c/Program\\ Files\\ \\(x86\\)/Java/jre6/ => Windows avec cygwin
	    
	    String javaHome =SystemUtils.JAVA_HOME;
	    if(SystemUtils.IS_OS_WINDOWS){
		javaHome.replaceAll("\\\\","/");
		javaHome = javaHome.replaceAll("\\\\","/");
		javaHome = javaHome.replaceAll(" ", "\\\\\\ ");
		javaHome = javaHome.replaceAll("\\(", "\\\\\\(");
		javaHome = javaHome.replaceAll("\\)", "\\\\\\)");
		javaHome = javaHome.replaceAll("C:/", "/cygdrive/c/");
	    }
	    
	    parameters.put(KtbsConstants.PARAMETER_COMMAND_LINE, javaHome + "/bin/java -jar Sparql1.1_4j.jar \"%(__sources__)s\" \"%(__destination__)s\"");
	    parameters.put(KtbsConstants.PARAMETER_FORMAT, "xml");
	    methodUri = root.getResourceService().newMethod(getCurrentBase().getUri(), localName, KtbsConstants.EXTERNAL, parameters);
	    if(methodUri != null){
		System.out.println("Method filter "+methodUri + " created");
		if(!StringUtils.isEmpty(label)){
		    method  = root.getResourceService().getMethod(methodUri);
		    method.addLabel(label);
		    method.addProperty(KtbsConstants.COMMENT, "Méthode temporaire visant à palier l' exécution du SPARQL du kTBS.");
		    root.getResourceService().saveResource(method);
		}
	    }
		
	    if(methodUri != null){
		method  = root.getResourceService().getMethod(methodUri);
	    }
	    return method;
	    
	}
}
