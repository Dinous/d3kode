/**.
 * 
 */
package org.liris.mTrace.tools.pojo.rule.where;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.liris.mTrace.tools.pojo.rule.util.AttributePojo;

/**.
 * @author Dino
 * @10 mai 2011
 */
public class WherePartPojo implements Serializable{
    /**.
     * 
     */
    private static final long serialVersionUID = 4518196590652964791L;
    private List<WherePojo> listWhere;
    private List<FilterPojo> listFilter;
    private Boolean isInKtbs = false;
    private Map<String, String> mapObselName;
    private String traceModelUri;
    
    public WherePartPojo(String traceModelUri){
	this.traceModelUri = traceModelUri;
    }

    public String toKtbsString(){
	mapObselName = new HashMap<String, String>();
	StringBuffer stringBuffer = new StringBuffer(" ");
	Integer index = 0;
	for (WherePojo where : listWhere) {
	    stringBuffer.append("?").append(where.getLocalName());
	    stringBuffer.append(" a ").append(where.getTypeFromKtbs()).append(";");
	    
	    for (int i =0;i<where.getListAttribute().size();i++) {
		AttributePojo attribute = where.getListAttribute().get(i);
		
		stringBuffer.append(attribute.getUri()).append(" ");
		stringBuffer.append("?").append(attribute.getLocalName()).append(index);
		
		mapObselName.put(where.getLocalName()+"."+attribute.getLocalName(), "?"+attribute.getLocalName()+index);
		
		if(i < where.getListAttribute().size()-1){
		    //another attribute
		    stringBuffer.append(";");
		}else{
		    //last attribute
		    stringBuffer.append(".");
		}
	    }
	    index = index + 1;
	}
	if(listFilter != null){
    	for (FilterPojo filter : listFilter) {
    	    //FILTER (regex(?Evenement1 , 'En apparition'))"
    	    stringBuffer.append(" FILTER ");
    	    stringBuffer.append(" (");    	    
    	    stringBuffer.append(filter.toKtbsString());
    	    stringBuffer.append(") ");

    	}
	}
	String request = StringUtils.replaceEach(stringBuffer.toString(), mapObselName.keySet().toArray(new String[0]), mapObselName.values().toArray(new String[0]));
	return request;
    }
    
    
    
    public void setListWhere(List<WherePojo> listWherePojo) {
	this.listWhere = listWherePojo;
    }
    public List<WherePojo> getListWhere() {
	return listWhere;
    }
    public List<FilterPojo> getListFilter() {
        return listFilter;
    }
    public void setListFilter(List<FilterPojo> listFilterPojo) {
        this.listFilter = listFilterPojo;
    }
    public void setIsInKtbs(Boolean isInKtbs) {
	this.isInKtbs = isInKtbs;
    }
    public Boolean getIsInKtbs() {
	return isInKtbs;
    }

    public void setMapObselName(Map<String, String> mapObselName) {
	this.mapObselName = mapObselName;
    }

    public Map<String, String> getMapObselName() {
	return mapObselName;
    }

    public void setTraceModelUri(String traceModelUri) {
	this.traceModelUri = traceModelUri;
    }

    public String getTraceModelUri() {
	return traceModelUri;
    }
}
