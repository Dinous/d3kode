package org.liris.mTrace.actions.visualization;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.liris.mTrace.actions.D3KODEAction;
import org.liris.mTrace.tools.pojo.visualization.VisualizationPojo;

public class HistoryVisuAction extends D3KODEAction {

	private List<VisualizationPojo> visualisationPojos = new ArrayList<VisualizationPojo>();
	
	@Action(value = "/history_visu",
		    results = {
			    @Result(location = "visualization/history_visualization.jsp", name = "success")
			  }
	    ) 
	@Override
	public String execute() throws Exception {
		
//		File svgFolder = new File(MTraceConst.WORK_SVG_PATH + getUserLogin());
//		if(svgFolder.exists()){
//			for (File svgFile : svgFolder.listFiles()) {
//				if(!svgFile.getName().startsWith(".")){
//					VisualizationPojo visualisationPojo = new VisualizationPojo(svgFile);
//					visualisationPojos.add(visualisationPojo);
//				}
//			}
//		}
		
		return SUCCESS;
	}

	public List<VisualizationPojo> getVisualisationPojos() {
		return visualisationPojos;
	}

	public void setVisualisationPojos(List<VisualizationPojo> visualisationPojos) {
		this.visualisationPojos = visualisationPojos;
	}
}
