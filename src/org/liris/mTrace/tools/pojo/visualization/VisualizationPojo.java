package org.liris.mTrace.tools.pojo.visualization;

import java.io.File;
import java.util.Date;

import org.liris.mTrace.MTraceConst;

public class VisualizationPojo {

	private String localName;
	private String begin;
	private String end;
	private Double scale;
	private Integer nbTransformation;
	private Date creationDate;
	
	public VisualizationPojo(File svgFile) {
		this.localName = svgFile.getName();
		String svgFilePath = svgFile.getName().replace(".svg", "");
		String[] svgFileTab = svgFilePath.split("_");
		this.begin = svgFileTab[1];
		this.end = svgFileTab[2];
		if(svgFileTab.length > 3){
			this.scale = new Double(svgFileTab[3]);
			this.nbTransformation = Integer.valueOf(svgFileTab[4]);
		}
		this.creationDate = new Date(svgFile.lastModified());
		
		
	}
	
	public String getLocalName() {
		return localName;
	}
	public void setLocalName(String localName) {
		this.localName = localName;
	}
	public String getBegin() {
		return begin;
	}
	public void setBegin(String begin) {
		this.begin = begin;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public Integer getNbTransformation() {
		return nbTransformation;
	}
	public void setNbTransformation(Integer nbTransformation) {
		this.nbTransformation = nbTransformation;
	}
	public Double getScale() {
		return scale;
	}
	public void setScale(Double scale) {
		this.scale = scale;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public String getCreationDateStr() {
		return MTraceConst.dateFromkTBSDateDT(creationDate);
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	
}
