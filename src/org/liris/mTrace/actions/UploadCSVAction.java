package org.liris.mTrace.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.liris.mTrace.MTraceConst;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "mTrace")
public class UploadCSVAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8092531028169557408L;
	private File upload;// The actual file
	private String uploadContentType; // The content type of the file
	private String uploadFileName; // The uploaded file name
	private String fileCaption;// The caption of the file entered by user
	private String traceModelSelected;
	
	public String getCsvPath() {
		return MTraceConst.WORK_CSV_PATH;
	}

 @Action(value = "/doUpload_upload_result",
	results = {@Result(location = "uploadResult.jsp", name = "success")})
	public String upload() {
		if(getUploadFileName() != null){
			try {
				if(!FilenameUtils.getExtension(getUploadFileName()).equals("csv")){
					List<Object> ext = new ArrayList<Object>();
					ext.add(FilenameUtils.getExtension(getUploadFileName()));
					addActionError(getText("bad.extention",ext));
				}
				File folderTraceModel = new File(getCsvPath() + File.separator + getTraceModelSelected());
				folderTraceModel.mkdir();
				String fullFileName = folderTraceModel.getAbsolutePath() + File.separator + getUploadFileName();
				File theFile = new File(fullFileName);
				FileUtils.copyFile(upload, theFile);
				
			} catch (Exception e) {
				addActionError(e.getMessage());
			}
 		}
		return SUCCESS;
	}
 
 public Map<File, List<File>> getFileList(){
		Map<File, List<File>> map = new HashMap<File, List<File>>();
		for (File folder : new File(MTraceConst.WORK_CSV_PATH).listFiles()) {
		    if(folder.isDirectory()){
			map.put(folder, Arrays.asList(folder.listFiles()));
		    }
		}
		return map;
	}
 
 public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getFileCaption() {
		return fileCaption;
	}

	public void setFileCaption(String fileCaption) {
		this.fileCaption = fileCaption;
	}

	public void setTraceModelSelected(String traceModelSelected) {
		this.traceModelSelected = traceModelSelected;
	}

	public String getTraceModelSelected() {
		return traceModelSelected;
	}
}
