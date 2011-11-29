/**.
 * 
 */
package org.liris.mTrace.actions.visualization;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.liris.ktbs.domain.interfaces.IComputedTrace;
import org.liris.ktbs.domain.interfaces.IObsel;
import org.liris.ktbs.domain.interfaces.ITrace;
import org.liris.ktbs.utils.KtbsUtils;
import org.liris.mTrace.MTraceConst;
import org.liris.mTrace.actions.D3KODEAction;
import org.liris.mTrace.kTBS.SingleKtbs;

/**.
 * @author Dino
 * @25 août 2011
 */
public class VisualizationAction extends D3KODEAction{

    
    
    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Action(value = "/visualization_choice",
	    results = {
	    @Result(location = "visualization/visualization.jsp", name = "success")
	  }/*,
		interceptorRefs ={@InterceptorRef(value ="roles",params={"allowedRoles", "expert"}),
		@InterceptorRef("defaultStack")}*/)
    @Override
    public String execute() throws Exception {
        return super.execute();
    }
    
    public Map<Double, String> getMapScale() {
        Map<Double, String> map = new TreeMap<Double, String>();
        map.put(0.10, "1/10");
        map.put(0.25, "1/4");
        map.put(0.50, "1/2");
        map.put(0.75, "3/4");
        map.put(1.0, "1/1");
        map.put(1.25, "5/4");
        map.put(1.5, "3/2");
        map.put(2.0, "2/1");
        map.put(5.0, "5/1");
        map.put(10.0, "10/1");
        map.put(20.0, "20/1");
        map.put(40.0, "40/1");        
        return map;
    }
    
    public String getFirstBeginObsel(String uri){
	ITrace  trace = (ITrace)SingleKtbs.getInstance().getRoot().getResourceService().getResource(uri);
	List<IObsel> obsels = new ArrayList<IObsel>(trace.getObsels());
	
	Collections.sort(obsels, new Comparator<IObsel>() {

	    @Override
	    public int compare(IObsel o1, IObsel o2) {
		return o1.getBegin().compareTo(o2.getBegin());
	    }
	    
	});
	
	try {
	    Date dateBeginTrace = MTraceConst.strDateFromCSV(trace.getOrigin());
	    dateBeginTrace = DateUtils.addMilliseconds(dateBeginTrace, obsels.get(0).getBegin().intValue());
	    return longdf.format(dateBeginTrace);
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	return "";
    }
    
    public String getLastEndObsel(String uri){
	ITrace  trace = (ITrace)SingleKtbs.getInstance().getRoot().getResourceService().getResource(uri);
	List<IObsel> obsels = new ArrayList<IObsel>(trace.getObsels());
	
	Collections.sort(obsels, new Comparator<IObsel>() {

	    @Override
	    public int compare(IObsel o1, IObsel o2) {
		return o1.getBegin().compareTo(o2.getBegin());
	    }
	    
	});
	
	try {
	    Date dateEndTrace = MTraceConst.strDateFromCSV(trace.getOrigin());
	    dateEndTrace = DateUtils.addMilliseconds(dateEndTrace, obsels.get(obsels.size()-1).getEnd().intValue());
	    return longdf.format(dateEndTrace);
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	return "";
    }
    
    public List<IComputedTrace> getListComputedTrace(Set<IComputedTrace> computedTraces){
	List<IComputedTrace> cts = new ArrayList<IComputedTrace>(computedTraces);
	Collections.sort(cts, new Comparator<IComputedTrace>() {

	    @Override
	    public int compare(IComputedTrace o1, IComputedTrace o2) {
		Long idKtbsO1 = Long.valueOf(o1.getLocalName().replaceAll("_", ""));
		Long idKtbsO2 = Long.valueOf(o2.getLocalName().replaceAll("_", ""));
		
		if(idKtbsO1 < idKtbsO2){
		    return 1;
		}else{
		    if(idKtbsO1 > idKtbsO2){
			return -1;
		    }
		}
		return 0;
	    }
	});
	return cts;
    }
}
