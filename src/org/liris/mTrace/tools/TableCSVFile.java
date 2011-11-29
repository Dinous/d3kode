package org.liris.mTrace.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;

public class TableCSVFile {

	private String filePath;
	private List<String[]> contentFile;
	private String[] headFile;
	
	private Map<String, String[]> mapObselTypeAttribute;
	
	public TableCSVFile(File csvFile, boolean limited) throws IOException {
		mapObselTypeAttribute = new HashMap<String, String[]>();
		setFilePath(csvFile.getAbsolutePath());
		List<String[]> csvContentFile = new ArrayList<String[]>();
		
		//read line
		List<String> listContent = FileUtils.readLines(csvFile, "ISO-8859-1");
		//for each line
			for (String content : listContent) {
				String[] contentTab = null;
				//first line
				if(headFile == null){
					contentTab = content.split(";");
					headFile = (String[])ArrayUtils.clone(contentTab); 
				}else{
					//split colomns
					contentTab = content.split(";");
					mapObselTypeAttribute.put(contentTab[2],headFile);
					csvContentFile.add(contentTab);
					if(limited){
						if(csvContentFile.size() > 5){
							break;
						}
					}
				}
			}
			setContentFile(csvContentFile);
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public List<String[]> getContentFile() {
		return contentFile;
	}

	public void setContentFile(List<String[]> contentFile) {
		this.contentFile = contentFile;
	}

	public void setHeadFile(String[] headFile) {
		this.headFile = headFile;
	}

	public String[] getHeadFile() {
		return headFile;
	}

	public void setMapObselTypeAttribute(Map<String, String[]> mapObselTypeAttribute) {
		this.mapObselTypeAttribute = mapObselTypeAttribute;
	}

	public Map<String, String[]> getMapObselTypeAttribute() {
		return mapObselTypeAttribute;
	}
}
