/**.
 * 
 */
package org.liris.mTrace.kTBS;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.liris.ktbs.domain.interfaces.IObselType;
import org.liris.ktbs.domain.interfaces.ITraceModel;
import org.liris.mTrace.MTraceConst;
import org.liris.mTrace.jaxb.base.ObselTypeFigure;
import org.liris.mTrace.jaxb.base.ObselTypes;
import org.liris.mTrace.jaxb.base.TraceModelFigure;
import org.liris.mTrace.jaxb.base.TraceModelFigures;

/**.
 * @author Dino
 * @1 sept. 2011
 */
public class XmlObseltypeFigureGenerator {

    private TraceModelFigures traceModelFigures;
    private String baseLocalName;
    private final File imageFolder;
    private File fileObselTypeFigureXml;
    
    
    public XmlObseltypeFigureGenerator() {
	this(null, null);
    }
    /**.
     * 
     */
    public XmlObseltypeFigureGenerator(File imageF, Set<ITraceModel> traceModels) {
	this.imageFolder = imageF;
	setBaseLocalName(SingleKtbs.getInstance().getCurrentBase().getLocalName());
	//File fileObselTypeFigureXml = new File(MTraceConst.WORK_XML_PATH + baseLocalName);
	//rendre caduc si modification model
	if(fileObselTypeFigureXml.exists()){
	    setTraceModelFigures(load());
	}else{
	    TraceModelFigures traceModelFigures = new TraceModelFigures();
	    for (ITraceModel traceModel : traceModels) {
		
		TraceModelFigure traceModelFigure = new TraceModelFigure();
		traceModelFigure.setUri(traceModel.getUri());
		ObselTypes obselTypes = new ObselTypes();
		Double step = 200D / (traceModel.getObselTypes().size()+1);
		int i =0;
		for (IObselType obselType : traceModel.getObselTypes()) {
		    ObselTypeFigure obselTypeFigure = new ObselTypeFigure();
		    obselTypeFigure.setLabel(obselType.getLabel());
		    obselTypeFigure.setXlinkHref(getImage(obselType.getLabel(), getImageFolder()));
		    
		    obselTypeFigure.setTraceLevel(i*step + 30);		
		    
		    obselTypes.getObselTypeFigure().add(obselTypeFigure);
		    i++;
		}
		traceModelFigure.setObselTypes(obselTypes);
		traceModelFigures.getTraceModelFigure().add(traceModelFigure);
	    }
	    setTraceModelFigures(traceModelFigures);
	   save();
	}
    }

    /**.
     * @param label
     * @param imageFolder
     * @return
     */
    private String getImage(String label, File imageFolder) {
	if(imageFolder != null){
	for (File image : imageFolder.listFiles()) {
	    if(image.getName().startsWith(label)){
		return image.getName();
	    }
	}
	}
	return "";
    }
    

    /**.
     * 
     */
    public void save() {
	//File fileObselTypeFigureXml = new File(MTraceConst.WORK_XML_PATH + baseLocalName+File.separator+baseLocalName);
	 JAXBContext jaxbCtx;
	    try {
		jaxbCtx = JAXBContext.newInstance(org.liris.mTrace.jaxb.base.ObjectFactory.class);
		Marshaller m = jaxbCtx.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(false));
		fileObselTypeFigureXml.createNewFile();
		FileOutputStream fileOutputStream = new FileOutputStream(fileObselTypeFigureXml);
		m.marshal(traceModelFigures, fileOutputStream);
		fileOutputStream.close();
		setTraceModelFigures(traceModelFigures);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	
    }
    /**.
     * 
     */
    public TraceModelFigures load() {
	JAXBContext jaxbCtx;
	try {
	    jaxbCtx = JAXBContext.newInstance(org.liris.mTrace.jaxb.base.ObjectFactory.class);
	    javax.xml.bind.Unmarshaller m = jaxbCtx.createUnmarshaller();
	    return (TraceModelFigures)m.unmarshal(fileObselTypeFigureXml);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
	
    }

    public void setTraceModelFigures(TraceModelFigures traceModelFigures) {
    	this.traceModelFigures = traceModelFigures;
    }
    public TraceModelFigures getTraceModelFigures() {
    	return traceModelFigures;
    }
    public void setBaseLocalName(String baseLocalName) {
    	this.baseLocalName = baseLocalName;
    	this.fileObselTypeFigureXml = new File(MTraceConst.WORK_XML_PATH + baseLocalName+File.separator+baseLocalName);
    }
    public String getBaseLocalName() {
    	return baseLocalName;
    }
    /**.
     * @param traceModelUri
     * @return
     */
    public ObselTypes getObselTypeFigure(String traceModelUri, Set<ITraceModel> traceModels) {
	for (TraceModelFigure traceModelFigure : traceModelFigures.getTraceModelFigure()) {
	    if(traceModelFigure.getUri().equals(traceModelUri)){
		ObselTypes obselTypes = traceModelFigure.getObselTypes();
		Collections.sort(obselTypes.getObselTypeFigure(), new Comparator<ObselTypeFigure>() {

		    @Override
		    public int compare(ObselTypeFigure o1, ObselTypeFigure o2) {
			if(o1.getTraceLevel() < o2.getTraceLevel()){
			    return -1;
			}else if(o1.getTraceLevel() > o2.getTraceLevel()){
			    return 1;
			}
			
			return 0;
		    }
		    
		});
		return traceModelFigure.getObselTypes();
	    }
	}
	//File fileObselTypeFigureXml = new File(MTraceConst.WORK_XML_PATH + baseLocalName);
	fileObselTypeFigureXml.delete();
	XmlObseltypeFigureGenerator xmlObseltypeFigureGenerator = new XmlObseltypeFigureGenerator(getImageFolder(),traceModels);
	return xmlObseltypeFigureGenerator.getObselTypeFigure(traceModelUri, traceModels);
    }
    public File getImageFolder() {
	return imageFolder;
    }
}
