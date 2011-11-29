package org.liris.mTrace.kTBS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.liris.ktbs.client.KtbsConstants;
import org.liris.ktbs.domain.interfaces.IAttributePair;
import org.liris.ktbs.domain.interfaces.IBase;
import org.liris.ktbs.domain.interfaces.IComputedTrace;
import org.liris.ktbs.domain.interfaces.IMethod;
import org.liris.ktbs.domain.interfaces.IObsel;
import org.liris.ktbs.domain.interfaces.IStoredTrace;
import org.liris.ktbs.domain.interfaces.ITrace;
import org.liris.ktbs.domain.interfaces.ITraceModel;
import org.liris.mTrace.MTraceConst;
import org.liris.mTrace.jaxb.base.Base;
import org.liris.mTrace.jaxb.base.Description;
import org.liris.mTrace.jaxb.base.Lines;
import org.liris.mTrace.jaxb.base.Lines.Line;
import org.liris.mTrace.jaxb.base.Method;
import org.liris.mTrace.jaxb.base.Methods;
import org.liris.mTrace.jaxb.base.Model;
import org.liris.mTrace.jaxb.base.Obsel;
import org.liris.mTrace.jaxb.base.ObselType;
import org.liris.mTrace.jaxb.base.Obsels;
import org.liris.mTrace.jaxb.base.Param;
import org.liris.mTrace.jaxb.base.Params;
import org.liris.mTrace.jaxb.base.SuperMethod;
import org.liris.mTrace.jaxb.base.Trace;

/**
 * 
 * @author Dino
 *
 */
public class XmlBaseGenerator {

	/**.
     * 
     */
    private static final int SCALE = 1000;

	public static enum TypeGenerateSvg {TRACE_WITH_METHOD, TRACE, BASE}; 
	
	private TypeGenerateSvg srcGenerateSvg;
	private static List<String> methods;
	private Integer nbSuperMethod =0;
	
	private SimpleDateFormat simpleDateFormatOutPut = new SimpleDateFormat("HH' h 'mm' min 'ss','SSS' sec'");
	private static DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh'h'mm'min'ss','SSS' sec'", Locale.FRANCE);
	
	public String getBaseUri() {
		return baseUri;
	}

	private Integer indexOfComputedTrace = 1;
	
	private String baseUri;
	private String traceUri;
	private List<String> listSuperMethod = new ArrayList<String>();
	
	private Integer beginStone;
	private Integer  endStone;
	
	private XmlBaseGenerator(){};
	
	/**
	 * Constructeur à changé dés qu'on pourra récupérer l'ui du modèle de trace à partir de la base
	 * @param baseName
	 * @param methodName
	 */
	public XmlBaseGenerator(String baseUri, String traceUri, TypeGenerateSvg srcGenerateSvg){
	    this();
	    this.baseUri = baseUri;
	    this.traceUri = traceUri;
	    this.srcGenerateSvg = srcGenerateSvg;
	}
	
	private File getFile(String xmlFilePath,Integer beginStone, Integer endStone, Integer nbTranformation, String scale ){
		File ret = new File( MTraceConst.WORK_XML_PATH + xmlFilePath+"_"+beginStone+"_"+endStone+"_"+scale+"_"+nbTranformation+".xml");
		return ret;
	}
	
	public File createOrUpdateFileXml(String xmlFilePath, Integer beginStone, Integer endStone, Integer nbTransformation, String scale) throws JAXBException, IOException {
	    File ret = getFile(xmlFilePath, beginStone, endStone, nbTransformation, scale);

	    if(ret.exists()){
	    	updateFileXml(ret.getAbsolutePath());
	    }else{
	    
		    setBeginStone(beginStone);
		    setEndStone(endStone);
		    switch (srcGenerateSvg) {
			    case TRACE_WITH_METHOD:
			    	//ret = createFileXmlFormTraceWithMethod(xmlFilePath, beginStone, endStone);
			    	break;
			    case TRACE:
			    	ret = createFileXmlFormTrace(ret.getAbsolutePath(), beginStone, endStone);
			    	break;
			    case BASE:
			    	ret = createFileXmlFormBase(ret.getAbsolutePath(), beginStone, endStone);
			    	break;
			    default:
				break;
		    }
	    }
	    return ret;
	}
	
	private File createFileXmlFormTrace(String xmlFilePath, Integer beginStone, Integer endStone) throws JAXBException, IOException {
		if(beginStone == null){
		    beginStone = 0;
		}
		if(endStone == null){
		    endStone = 1000;
		}
		IStoredTrace storedTrace = (IStoredTrace)SingleKtbs.getInstance().getRoot().getResourceService().getTrace(traceUri);
		
		org.liris.mTrace.jaxb.base.Base baseForXml = new org.liris.mTrace.jaxb.base.Base();
		org.liris.mTrace.jaxb.base.Trace traceForXml = new org.liris.mTrace.jaxb.base.Trace();
		org.liris.mTrace.jaxb.base.Traces tracesForXml = new org.liris.mTrace.jaxb.base.Traces();
		
		baseForXml.setLabel(SingleKtbs.getInstance().getCurrentBase().getLabel());

		//StoredTrace : trace première
		traceForXml.setFirstTrace(Boolean.TRUE);
		Set<IObsel> obselsFromTrace = new HashSet<IObsel>(SingleKtbs.getInstance().getRoot()
			.getStoredTraceService().listObsels(storedTrace, new Long(beginStone) * 1000 , new Long(endStone) * 1000));
		traceForXml.setObsels(sort(buildObsels(obselsFromTrace)));
		
		tracesForXml.getTrace().add(traceForXml);
		
		baseForXml.setTraces(tracesForXml);
		
		File xmlFile = new File(xmlFilePath);
		
		JAXBContext jaxbCtx = JAXBContext.newInstance(org.liris.mTrace.jaxb.base.ObjectFactory.class);
		Marshaller m = jaxbCtx.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(false));
		xmlFile.createNewFile();
		FileOutputStream fileOutputStream = new FileOutputStream(xmlFile);
		m.marshal(baseForXml, fileOutputStream);
		fileOutputStream.close();
		
		return xmlFile;
	}
	
	private String createSvgLabel(Set<String> labels){
	    String label = "";
	    if(labels != null){
		for (String l : labels) {
		    if(!l.contains("Subject")){
			label = l;
		    }
		}
	    }
	    return label;
	}
	
	private Model createModelSvg(ITraceModel traceModel){
	    Model model = new Model();
	    model.setLabel(traceModel.getLabel());
	    model.setUri(traceModel.getUri());
	    return model;
	}
	
	private SuperMethod createSuperMethodSvg(Set<IComputedTrace> computedTraces){
	    SuperMethod superMethod = new SuperMethod();
	    
	    Methods methodsSvg = new Methods();
	    
	    for (IComputedTrace computedTrace : computedTraces) {
		if(computedTrace.getIsIntermediateSource()){
		    Set<IObsel> obselsFromTrace;
		    if(getBeginStone() != null && getEndStone() != null){
			obselsFromTrace = new HashSet<IObsel>(SingleKtbs.getInstance().getRoot()
				.getComputedTraceService().listObsels((IComputedTrace)computedTrace, new Long(getBeginStone()) *1000 , new Long(getEndStone()) * 1000));
		    }else{
			obselsFromTrace = computedTrace.getObsels();
		    }
		    Collection<Method> listMethodSvg = createMethodSvg(sort(buildObsels(obselsFromTrace)), computedTrace.getMethod());
		    methodsSvg.getMethod().addAll(listMethodSvg);
		}else{
		    if(!listSuperMethod.contains(computedTrace.getUri())){
			superMethod.setDescription(createDescriptionSvg(computedTrace.getMethod().getLabel()));
			superMethod.setLabel(computedTrace.getMethod().getLabel());
			superMethod.setTrace(createSvgTrace(computedTrace, getBeginStone(),getEndStone()));
			listSuperMethod.add(computedTrace.getUri());
			setNbSuperMethod(getNbSuperMethod() + 1);
		    }
		}
	    }
	    superMethod.setMethods(methodsSvg);
	    
	    return superMethod;
	}

	private Collection<Method> createMethodSvg(Obsels obsels, IMethod methodKtbs) {
	    Collection<Method> methodsKtbs = new ArrayList<Method>();
	    for(Obsel targetObsel: obsels.getObsel()){
		BigInteger beginRule ;
	    	BigInteger endRule ;
	    	Lines lines = null;
		if(targetObsel.getLines().getLine().size() > 0){
        		lines = new Lines();
        	    	beginRule = targetObsel.getLines().getLine().get(0).getBegin();
        	    	endRule = targetObsel.getLines().getLine().get(targetObsel.getLines().getLine().size()-1).getEnd();
        	    
        	    	BigInteger rulePoint = beginRule.add(BigInteger.valueOf(endRule.intValue() - beginRule.intValue()).divide(BigInteger.valueOf(2)));
        	    	for (int i = 0; i < targetObsel.getLines().getLine().size(); i++) {
        	    	    Line line = targetObsel.getLines().getLine().get(i);
        	    	    line.setEnd(rulePoint);
        	    	    lines.getLine().add(line);
        	    	}
		}else{
		    beginRule = targetObsel.getHasBegin();
		    endRule = targetObsel.getHasEnd();
		}
	    	Method method = new Method();
	    	method.setBegin(beginRule);
	    	method.setEnd(endRule);
	    	method.setDescription(createDescriptionSvg((String)methodKtbs.getPropertyValue(KtbsConstants.COMMENT)));
	    	method.setLabel(methodKtbs.getLabel());
	    	method.setLines(lines);
	    	
	    	methodsKtbs.add(method);
	    }
	    
	    return methodsKtbs;
	}
	
	private Description createDescriptionSvg(String descriptionkTBS){
	    Description description = new Description();
	    if(descriptionkTBS != null){
	    String[] tabDescription = descriptionkTBS.split(" ");
	    String d = "";
	    for(int i=0;i<tabDescription.length-1; i++){
	        d += tabDescription[i]+" "; 
	        if(d.length() > 91){
	        	description.getD().add(d); 
	        	d= "";
	        }
	    }
	    if(d != null){
		description.getD().add(d);
	    }
	    if((description.getD().get(description.getD().size()-1).length() +
		    1 + 
		    tabDescription[tabDescription.length-1].length()) >= 91){
			description.getD().add(tabDescription[tabDescription.length-1]);
	    }else{
		description.getD().set(description.getD().size()-1, description.getD().get(description.getD().size()-1) + " " + tabDescription[tabDescription.length-1]);
	    }
	    }
	    return description;
	}
	
	private Trace createSvgTrace(ITrace ktbsTrace, Integer beginStone, Integer endStone){
	    Trace svgTrace = new Trace();
	    Set<IObsel> obselsFromTrace = new HashSet<IObsel>();
	    
	    svgTrace.setUri(ktbsTrace.getUri());
	    svgTrace.setIndex(indexOfComputedTrace++);
	    svgTrace.setLabel(createSvgLabel(ktbsTrace.getLabels()));    
	    svgTrace.setModel(createModelSvg(ktbsTrace.getTraceModel()));    
	    
	    if(getBeginStone() != null)
	    setBeginStone(beginStone);
	    if(getEndStone() != null)
	    setEndStone(endStone);
	    
	    if(ktbsTrace instanceof IComputedTrace){
		svgTrace.setFirstTrace(false);
		if(beginStone != null && endStone != null){
			obselsFromTrace = new HashSet<IObsel>(SingleKtbs.getInstance().getRoot()
				.getComputedTraceService().listObsels((IComputedTrace)ktbsTrace, new Long(getBeginStone()) *1000 , new Long(getEndStone()) * 1000));
			}else{
			    obselsFromTrace = new HashSet<IObsel>(ktbsTrace.getObsels());
			}
	    }else{
		svgTrace.setFirstTrace(true);
		if(beginStone != null && endStone != null){
		obselsFromTrace = new HashSet<IObsel>(SingleKtbs.getInstance().getRoot()
			.getStoredTraceService().listObsels((IStoredTrace)ktbsTrace, new Long(getBeginStone()) *1000 , new Long(getEndStone()) * 1000));
		}else{
		    obselsFromTrace = new HashSet<IObsel>(ktbsTrace.getObsels());
		}
	    }
	    svgTrace.setObsels(sort(buildObsels(obselsFromTrace)));
	    
	    svgTrace.getSuperMethod().add(createSuperMethodSvg(ktbsTrace.getTransformedTraces()));
	    
	    return svgTrace;
	}
	
	/**
	 * @param args
	 * @throws JAXBException 
	 * @throws IOException 
	 * @throws ResourceLoadException 
	 */
	private File createFileXmlFormBase(String xmlFilePath, Integer beginStone, Integer endStone) throws JAXBException, IOException {
		File xmlFile = new File(xmlFilePath);
		IBase base = SingleKtbs.getInstance().getBase(baseUri);
		
		org.liris.mTrace.jaxb.base.Base baseForXml = new org.liris.mTrace.jaxb.base.Base();
		org.liris.mTrace.jaxb.base.Trace traceForXml = null;
		org.liris.mTrace.jaxb.base.Traces tracesForXml = new org.liris.mTrace.jaxb.base.Traces();
		XmlObseltypeFigureGenerator xmlObseltypeFigureGenerator = new XmlObseltypeFigureGenerator();
		
		baseForXml.setTraceModelFigures(xmlObseltypeFigureGenerator.getTraceModelFigures());
		
		baseForXml.setLabel(base.getLabel());
		//int indexOfComputedTrace = 1;
		for (ITrace trace : SingleKtbs.getInstance().getTraces(baseUri)) {
		    //Set<IObsel> obselsFromTrace = null;
		    traceForXml = new Trace();
		    if(trace instanceof IComputedTrace){
		    }else{
			
			traceForXml = createSvgTrace((IStoredTrace)trace, beginStone , endStone );
			tracesForXml.getTrace().add(traceForXml);
			break;
		    }
		} 
		baseForXml.setTraces(tracesForXml);
		
		JAXBContext jaxbCtx = JAXBContext.newInstance(org.liris.mTrace.jaxb.base.ObjectFactory.class);
		Marshaller m = jaxbCtx.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(false));
		xmlFile.createNewFile();
		FileOutputStream fileOutputStream = new FileOutputStream(xmlFile);
		m.marshal(baseForXml, fileOutputStream);
		fileOutputStream.close();
		
		return xmlFile;
	}
	
	
	private Obsels buildObsels(Set<IObsel> iObsels){
	    Obsels obsels = new Obsels();
	    org.liris.mTrace.jaxb.base.Obsel obselForXml = null;
		Lines lines = null;
		Line line = null;
	    for (IObsel obsel : iObsels) {
		
		obselForXml = new org.liris.mTrace.jaxb.base.Obsel();
		obselForXml.setHasBegin(obsel.getBegin().divide(BigInteger.valueOf(SCALE)));
		obselForXml.setHasEnd(obsel.getEnd().divide(BigInteger.valueOf(SCALE)));
		obselForXml.setId("_"+obsel.getLocalName());
		obselForXml.setLabel(obsel.getLabel());
		ObselType obselType = new ObselType();
		obselType.setId(obsel.getObselType().getUri());
		obselType.setLabel(obsel.getObselType().getLabel());
		obselForXml.setObselType(obselType);
		obselForXml.setParams(new Params());
		
		addDateParams(obselForXml.getParams(), obsel);
		
		Map<String,IAttributePair> hsAttributePairs = new TreeMap<String, IAttributePair>();
		for (IAttributePair attributeStatement : obsel.getAttributePairs()) {
		    hsAttributePairs.put(attributeStatement.getAttributeType().getLabel(), attributeStatement);
		}
		
		List<Param> paramList = new ArrayList<Param>();
		
		Param param = new Param(); 
		param.setName("Label");
		param.setValue(obsel.getLabel());
		paramList.add(param);
		
		String strdateOrigin = ((ITrace)obsel.getParentResource()).getOrigin();
		try {
		    Date dateOrigin = MTraceConst.strDateFromCSV(strdateOrigin);
		    
		    Calendar cal = GregorianCalendar.getInstance();
		    cal.setTime(dateOrigin);
		    cal.add(Calendar.MILLISECOND, Integer.valueOf(obsel.getBegin().toString()));
		    
		    param = new Param();
		    param.setName("Date apparition");
		    
		    param.setValue(df.format(cal.getTime()));
		    paramList.add(param);
		} catch (ParseException e) {}
		
		
		for (IAttributePair attributeStatement : hsAttributePairs.values()) {
			param = new Param(); 
			param.setName(attributeStatement.getAttributeType().getLabel());
			param.setValue(attributeStatement.getValue().toString());
			paramList.add(param);
		}
		
		obselForXml.getParams().getParam().addAll(paramList);
		lines = new Lines();
//		System.out.println("obsel " + obsel.getBegin().divide(BigInteger.valueOf(SCALE)) +
//			    " "+ obsel.getEnd().divide(BigInteger.valueOf(SCALE)));
		for (IObsel obselSource : obsel.getSourceObsels()) {
		    line = new Line();
		    
//		    System.out.println("obsel.getSourceObsels() " + obselSource.getBegin().divide(BigInteger.valueOf(SCALE)) +
//			    " "+ obselSource.getEnd().divide(BigInteger.valueOf(SCALE)));
		    
		    line.setBegin(obselSource.getEnd().divide(BigInteger.valueOf(SCALE)));
		    line.setEnd(obselSource.getEnd().divide(BigInteger.valueOf(SCALE)));
		    line.setDest(obselSource.getObselType().getLabel());
		    line.setSrc(obsel.getObselType().getLabel());
		    lines.getLine().add(line);
		}
		obselForXml.setLines(sort(lines));
		obsels.getObsel().add(obselForXml);
	}
	    return obsels;
	}
	
	
	
//	/**
//	 * @param args
//	 * @throws JAXBException 
//	 * @throws IOException 
//	 * @throws ResourceLoadException 
//	 */
//	private File createFileXmlFormTrace(String xmlFilePath) throws JAXBException, IOException {
//		File xmlFile = new File(xmlFilePath);
//		ITrace trace = SingleKtbs.getInstance().getRoot().getResourceService().getTrace(traceUri);
//		
//		IBase base = SingleKtbs.getInstance().getBase(trace.getParentUri());
//		
//		org.liris.mTrace.jaxb.base.Base baseForXml = new org.liris.mTrace.jaxb.base.Base();
//		org.liris.mTrace.jaxb.base.Trace traceForXml = null;
//		org.liris.mTrace.jaxb.base.Traces tracesForXml = new org.liris.mTrace.jaxb.base.Traces();
//		
//		baseForXml.setLabel(base.getLabel());
//		baseForXml.setTraces(tracesForXml);
//		 Set<IObsel> obselsFromTrace = null;
//		if(trace instanceof IComputedTrace){
//			traceForXml = new org.liris.mTrace.jaxb.base.Trace();
//			obselsFromTrace = trace.getObsels();
//		    }else{
//			traceForXml = new org.liris.mTrace.jaxb.base.Trace();
//			traceForXml.setFirstTrace(Boolean.TRUE);
//			obselsFromTrace = new HashSet<IObsel>(SingleKtbs.getInstance().getRoot()
//				.getStoredTraceService().listObsels((IStoredTrace)trace, 0 , second * 1000));
//		    }
//			
//			 if(trace.getLabels().size() > 1){
//				    traceForXml.setLabel(trace.getLabels().toArray(new String[0])[1]);    
//				}else{
//				    traceForXml.setLabel(trace.getLabel());
//				}
//			
//			traceForXml.setUri(trace.getUri());
//			
//			Model modelForXml = new Model();
//			modelForXml.setLabel(trace.getTraceModel().getLabel());
//			modelForXml.setUri(trace.getTraceModel().getUri());
//			traceForXml.setModel(modelForXml);
//			
//			Methods methodsXml = new Methods();
//			if(trace instanceof IComputedTrace){
//				methods = new ArrayList<String>();
//				getRecursiveMethod((IComputedTrace)trace);
//				for (String methodStr : methods) {
//					org.liris.mTrace.jaxb.base.Method method = new org.liris.mTrace.jaxb.base.Method();
//					method.setLabel(methodStr);
//					methodsXml.getMethod().add(method);
//				}
//			}
//			//traceForXml.setMethods(methodsXml);
//			
//			Obsels obsels = new Obsels();
//			org.liris.mTrace.jaxb.base.Obsel obselForXml = null;
//			Lines lines = null;
//			Line line = null;
//			//System.out.println(obselsFromTrace.size());
//			for (IObsel obsel : obselsFromTrace) {
//				
//				obselForXml = new org.liris.mTrace.jaxb.base.Obsel();
//				obselForXml.setHasBegin(obsel.getBegin().divide(BigInteger.valueOf(SCALE)));
//				obselForXml.setHasEnd(obsel.getEnd().divide(BigInteger.valueOf(SCALE)));
//				obselForXml.setId(obsel.getUri());
//				obselForXml.setLabel(obsel.getLabel());
//				ObselType obselType = new ObselType();
//				obselType.setId(obsel.getObselType().getUri());
//				obselType.setLabel(obsel.getObselType().getLabel());
//				obselForXml.setObselType(obselType);
//				obselForXml.setParams(new Params());
//				
//				addDateParams(obselForXml.getParams(), obsel);
//				
//				for (IAttributePair attributeStatement : obsel.getAttributePairs()) {
//					Param param = new Param(); 
//					param.setName(attributeStatement.getAttributeType().getLabel());
//					param.setValue(attributeStatement.getValue().toString());
//					obselForXml.getParams().getParam().add(param);
//				}
//				
//				lines = new Lines();
//				
//				for (IObsel obselSource : obsel.getSourceObsels()) {
//				//if(obselSource != null){
//					line = new Line();
//					line.setBegin(obselSource.getBegin().divide(BigInteger.valueOf(SCALE)));
//					line.setEnd(obselSource.getEnd().divide(BigInteger.valueOf(SCALE)));
//					line.setDest(obselSource.getObselType().getUri());
//					line.setSrc(obsel.getObselType().getUri());
//					lines.getLine().add(line);
//				}
//				obselForXml.setLines(lines);
//				obsels.getObsel().add(obselForXml);
//			}
//			traceForXml.setObsels(sort(obsels));
//			
//			
//			
//			baseForXml.getTraces().getTrace().add(traceForXml);    
//			
//			
//		//} 
//		
//		JAXBContext jaxbCtx = JAXBContext.newInstance(org.liris.mTrace.jaxb.base.ObjectFactory.class);
//		Marshaller m = jaxbCtx.createMarshaller();
//		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
//		xmlFile.createNewFile();
//		FileOutputStream fileOutputStream = new FileOutputStream(xmlFile);
//		m.marshal(baseForXml, fileOutputStream);
//		fileOutputStream.close();
//		
//		return xmlFile;
//	}
	
    /**
     * .
     * 
     * @param params
     */
    private void addDateParams(Params params, IObsel obsel) {
	Param param = new Param();
	param.setName("Début");
	try{
	    param.setValue(simpleDateFormatOutPut
		.format(new Date(obsel.getBegin().longValue()  - 3600000L)));
	}catch (Exception e) {
	    e.printStackTrace();
	    param.setValue(null);
	}
	params.getParam().add(param);
	param = new Param();
	param.setName("Fin");
	try{
	param.setValue(simpleDateFormatOutPut
		.format(new Date(obsel.getEnd().longValue()  - 3600000L)));
	}catch (Exception e) {
	    e.printStackTrace();
	    param.setValue(null);
	}
	params.getParam().add(param);
    }

	private Obsels sort(Obsels obsels) {
	
		Comparator<org.liris.mTrace.jaxb.base.Obsel> comparator = new Comparator<org.liris.mTrace.jaxb.base.Obsel>() {
			
			@Override
			public int compare(org.liris.mTrace.jaxb.base.Obsel o1, org.liris.mTrace.jaxb.base.Obsel o2) {
				if(o1.getHasEnd().intValue() > o2.getHasEnd().intValue()) return 1;
				else if(o1.getHasEnd().intValue() < o2.getHasEnd().intValue()) return -1;
				return 0;
			}
		};
		Collections.sort(obsels.getObsel(), comparator);
		return obsels;
	}

	private Lines sort(Lines lines) {
		
		Comparator<Line> comparator = new Comparator<Line>() {
			
			@Override
			public int compare(Line o1, Line o2) {
				if(o1.getEnd().intValue() > o2.getEnd().intValue()) return 1;
				else if(o1.getEnd().intValue() < o2.getEnd().intValue()) return -1;
				return 0;
			}
		};
		Collections.sort(lines.getLine(), comparator);
		return lines;
	}
	
	public void getRecursiveMethod(IComputedTrace computedTrace){
		IMethod method = computedTrace.getMethod();
		if(method != null){
		    methods.add(method.getInherits());
		}
		Set<ITrace> sourceTraces = computedTrace.getSourceTraces();
		
		for (ITrace sourceTrace : sourceTraces) {
			if(sourceTrace instanceof IComputedTrace){
				getRecursiveMethod((IComputedTrace)sourceTrace);
			}
		}
	}


	public void setSrcGenerateSvg(TypeGenerateSvg srcGenerateSvg) {
	    this.srcGenerateSvg = srcGenerateSvg;
	}


	public TypeGenerateSvg getSrcGenerateSvg() {
	    return srcGenerateSvg;
	}

	public void setTraceUri(String traceUri) {
	    this.traceUri = traceUri;
	}

	public String getTraceUri() {
	    return traceUri;
	}

	/**.
	 * @param pathDes
	 */
	public void updateFileXml(String pathDes) throws JAXBException, IOException {
	    File xmlFile = new File(pathDes);
	    
	    JAXBContext jaxbCtx = JAXBContext.newInstance(org.liris.mTrace.jaxb.base.ObjectFactory.class);
	    Unmarshaller unm = jaxbCtx.createUnmarshaller();
	    Base baseForXml = (Base)unm.unmarshal(xmlFile);
	    setNbSuperMethod(countNbSuperMethod(baseForXml));
	    XmlObseltypeFigureGenerator xmlObseltypeFigureGenerator = new XmlObseltypeFigureGenerator();
	    baseForXml.setTraceModelFigures(xmlObseltypeFigureGenerator.getTraceModelFigures());
	    
	    Marshaller m = jaxbCtx.createMarshaller();
	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(false));
	    FileOutputStream fileOutputStream = new FileOutputStream(xmlFile);
	    m.marshal(baseForXml, fileOutputStream);
	    fileOutputStream.close();
	    
	}

	/**.
	 * @param sourceFilePath2
	 * @return
	 */
	private Integer countNbSuperMethod(Base base) {
	    Integer nbSuperMethod = 0;
	    
	    for(Trace trace : base.getTraces().getTrace()){
		nbSuperMethod += countNbSuperMethod(trace.getSuperMethod());
	    }
	    return nbSuperMethod;
	}

	/**.
	 * @param trace
	 * @return
	 */
	private Integer countNbSuperMethod(List<SuperMethod> superMethods) {
	    if(superMethods != null){
		for (SuperMethod superMethod : superMethods) {
		    if(superMethod.getTrace() == null){
			return 0;
		    }else{
			return 1 + countNbSuperMethod(superMethod.getTrace().getSuperMethod());
		    }
		}
	    }
	    return 0;
	}
	
	/**.
	 * @return
	 */
	public Integer getNbSuperMethod() {
	    return nbSuperMethod;
	}

	public void setNbSuperMethod(Integer nbSuperMethod) {
	    this.nbSuperMethod = nbSuperMethod;
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
}
