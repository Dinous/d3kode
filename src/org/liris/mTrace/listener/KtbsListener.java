package org.liris.mTrace.listener;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContextEvent;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.dispatcher.ng.listener.StrutsListener;
import org.liris.mTrace.MTraceConst;
import org.liris.mTrace.WebApplication;
import org.liris.mTrace.kTBS.SingleKtbs;

public class KtbsListener extends StrutsListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//super.contextDestroyed(sce);
		SingleKtbs.getInstance().destroy();
	}
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		super.contextInitialized(sce);
		File transformationFile = new File(MTraceConst.WORK_TRANSFORMATION_PATH);
		transformationFile.mkdirs();
		File methodFile = new File(MTraceConst.WORK_METHOD_CONDITION_PATH);
		methodFile.mkdirs();
		methodFile = new File(MTraceConst.WORK_METHOD_CONSTRUCTION_PATH);
		methodFile.mkdirs();
		File folderCsv = new File(MTraceConst.WORK_CSV_PATH);
		folderCsv.mkdirs();
		File folderXml = new File(MTraceConst.WORK_XML_PATH);
		folderXml.mkdirs();
		File folderSvg = new File(MTraceConst.WORK_SVG_PATH);
		folderSvg.mkdirs();
		
		File folderXsl = new File(MTraceConst.WORK_XSL_PATH);
		folderXsl.mkdirs();
		try {
			
			FileUtils.copyDirectory(new File(sce.getServletContext().getRealPath("work/xsl")), folderXsl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SingleKtbs.getInstance().initialize(new WebApplication("http://localhost:8001/"));
	}
}
