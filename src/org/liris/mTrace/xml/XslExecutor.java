package org.liris.mTrace.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.liris.mTrace.MTraceConst;
/**
 * 
 * @author Dino
 *
 */
public  class  XslExecutor{

	private XslExecutor() {}
	
	private File sourceXmlFile;
	private String destFilePath; 
	private String xslFilePath;
	private Integer seconde;
	private Integer beginStone;
	private Double scale;
	private Integer nbSuperMethod;
	private Integer temporalScale;
	
	public XslExecutor(File sourceXmlFile, String xslFilePath) {
		this();
		this.sourceXmlFile = sourceXmlFile;
		this.xslFilePath = xslFilePath;
	}

	public String createSvg(Integer beginStone,Integer endStone, Double scale, int nbSuperMethod, Integer temporalScale){
	    this.seconde = endStone -beginStone;
	    this.beginStone = beginStone;
	    this.scale = scale;
	    this.destFilePath = sourceXmlFile.getAbsolutePath()
	    		.replace(MTraceConst.WORK_XML_PATH, MTraceConst.WORK_SVG_PATH)
	    		.replace(".xml", ".svg");
	    this.setTemporalScale(temporalScale);
	    this.setNbSuperMethod(nbSuperMethod);
	    
		String ret = doTransformation();
		if(ret != null){
			Utils.deleteLines(getDestFilePath(), 1);
		}
		return ret;
	}
	
	public String doTransformation(){
		String ret = null;
		try {
			ret = execute();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return ret;
	}
	
	public static void main(String[] args) {
		//String xml = "result/allObs.xml";
//		String xml = "result/baseXml.xml";
//		String svg = "result/baseXml.svg";
//		String xsl = "transformer/trace.xsl";
//		
//		try{
//		      
//			//execute( xml,  xsl,  svg, "0");
//			execute( xml,  xsl,  svg);
//			deleteLine(svg, 1);
//			
//			xml = "result/baseXml.xml";
//			String html = "result/obselsXml.xml";
//			xsl = "transformer/obsels.xsl";
//			execute(xml, xsl, html);
//			//xml = "result/sparqlResult.xml";
//			//svg = "result/sparqlObs.svg";
//			//execute( xml,  xsl,  svg, "200");	
//		    }
//		    catch (Exception e){
//		      e.printStackTrace();
//		    }
	}
	
	private String execute() throws FileNotFoundException, TransformerException{
	    
	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    Transformer transformer = tFactory.newTransformer(new StreamSource(getXslFilePath()));
	    transformer.setParameter("seconde", seconde);
	    if(beginStone != null)
		transformer.setParameter("beginStone", beginStone);
	    transformer.setParameter("scale", scale);
	    
	    if(temporalScale != null)
		transformer.setParameter("temporalScale", temporalScale);
	    //nbSuperMethod = countNbSuperMethod(getSourceFilePath());
	    
	    if(nbSuperMethod != 0)
	    transformer.setParameter("nbSuperMethod", nbSuperMethod);
	      
	    File destFile = new File(getDestFilePath());
	    transformer.transform(new StreamSource(getSourceXmlFile()), new StreamResult(new FileOutputStream(destFile)));
	      
	    return destFile.getAbsolutePath();
	}
	
	public File getSourceXmlFile() {
		return sourceXmlFile;
	}
	public String getDestFilePath() {
		return destFilePath;
	}
	public String getXslFilePath() {
		return xslFilePath;
	}

	public void setNbSuperMethod(Integer nbSuperMethod) {
	    this.nbSuperMethod = nbSuperMethod;
	}

	public Integer getNbSuperMethod() {
	    return nbSuperMethod;
	}

	public void setTemporalScale(Integer temporalScale) {
	    this.temporalScale = temporalScale;
	}

	public Integer getTemporalScale() {
	    return temporalScale;
	}
}
