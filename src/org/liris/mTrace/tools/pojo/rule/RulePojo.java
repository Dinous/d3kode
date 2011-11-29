/**.
 * 
 */
package org.liris.mTrace.tools.pojo.rule;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.liris.ktbs.client.KtbsConstants;
import org.liris.ktbs.domain.interfaces.IMethod;
import org.liris.mTrace.MTraceConst;
import org.liris.mTrace.kTBS.SingleKtbs;
import org.liris.mTrace.tools.pojo.rule.construct.ConstructPartPojo;
import org.liris.mTrace.tools.pojo.rule.util.OperandePojo;
import org.liris.mTrace.tools.pojo.rule.where.FilterPojo;
import org.liris.mTrace.tools.pojo.rule.where.WherePartPojo;

/**.
 * @author Dino
 * @2 mai 2011
 */
public class RulePojo  implements Serializable{
    /**.
     * 
     */
    private static final long serialVersionUID = 702381945961509328L;
    private WherePartPojo wherePart;
    private ConstructPartPojo constructPart;
    
    private String uri;
    private String label;
    private Integer id;
    
    private Boolean isInKtbs =false;
    private String description;
    private String ktbsString;
    /**.
     * 
     */
    @Deprecated
    public RulePojo() {}
    
    /**.
     * 
     */
    public RulePojo(String mLabel) {
	this();
	label = mLabel;
    }
    
    
    public void createKtbsString(){
	if(wherePart != null){
	String wherePartStr = wherePart.toKtbsString();
	String constructPartStr = "";
	String letPart = "";
	if(constructPart != null){
	    constructPartStr = constructPart.toKtbsString(wherePart.getMapObselName(), wherePart.getListWhere());
	    letPart = constructLetPart(constructPart.getLetMap());
	    constructPartStr = StringUtils.replaceEach(constructPartStr, 
		    	constructPart.getLetMap().keySet().toArray(new String[0]),
		    	constructPart.getLetMap().values().toArray(new String[0]));
	    constructPartStr = StringUtils.replaceEach(constructPartStr,
			wherePart.getMapObselName().keySet().toArray(new String[0]), 
			wherePart.getMapObselName().values().toArray(new String[0]));
	    letPart = StringUtils.replaceEach(letPart,
			wherePart.getMapObselName().keySet().toArray(new String[0]), 
			wherePart.getMapObselName().values().toArray(new String[0]));
	}
	
	Map<String, String> mapPrefixPart = constructMapPrefixPart(wherePart, constructPart);
	String prefixPart = constructPrefixPart(mapPrefixPart);
	
	
	
	String preRequest = StringUtils.replaceEach(constructPartStr + " WHERE {" + wherePartStr + letPart + "}",mapPrefixPart.values().toArray(new String[0]),mapPrefixPart.keySet().toArray(new String[0]) );
	String[] myFunction = {"Min","Max"};
	String[] myFunctionWithNs = {"d3kode:Min","d3kode:Max"};
	preRequest = StringUtils.replaceEach(preRequest, myFunction, myFunctionWithNs);
	
	if(preRequest.contains(MTraceConst.SPARQL_NOT_EXISTS_OPEN)){
	    preRequest = preRequest.replace("FILTER  ("+MTraceConst.SPARQL_NOT_EXISTS_OPEN+")", "");
	    preRequest = preRequest.replace("FILTER  ("+MTraceConst.SPARQL_NOT_EXISTS_CLOSE+")", "");
	    preRequest = preRequest.replace("WHERE {","WHERE {FILTER(?notExists).LET ( ?notExists:=NOT EXISTS{SELECT *WHERE{");
	    preRequest = preRequest.substring(0,preRequest.length()-2);
	    preRequest = preRequest + "}})}";
	}
	
	this.setKtbsString(prefixPart + preRequest);
	}
    }
    
    private String constructLetPart(Map<String, String> mapLet){
	StringBuffer stringBuffer = new StringBuffer();
	for (Map.Entry<String, String> entry : mapLet.entrySet()) {
	    stringBuffer.append(" LET (");
	    stringBuffer.append(entry.getValue()).append(":=").append(entry.getKey());
	    stringBuffer.append(" ) ");
	}
	return stringBuffer.toString();
    }
    
    public String getNiceSparqlRepresentation(){
	createKtbsString();
	Map<String , String> replacement = new HashMap<String, String>();
	replacement.put("PREFIX", "<br />PREFIX");
	replacement.put("CONSTRUCT", "<br />CONSTRUCT<br/>");
	replacement.put("WHERE", "<br />WHERE<br/>");
	replacement.put("LET", "<br />LET<br/>");
	replacement.put("FILTER", "<br />FILTER<br/>");
	replacement.put(";", ";<br />");
	replacement.put(".", ".<br />");
	return StringUtils.replaceEach(getKtbsString(), replacement.keySet().toArray(new String[0]),
		replacement.values().toArray(new String[0]));
    }
    
    /**.
     * @param traceModelSourceSelected
     * @param traceModelTargetSelected
     * @return
     */
    private String constructPrefixPart(Map<String, String> map) {
	StringBuffer ret = new StringBuffer();
	for (Map.Entry<String, String> entry : map.entrySet()) {
	    ret.append(" PREFIX ").append(entry.getKey()).append(" <").append(entry.getValue()).append(">");
	}
	
	return ret.toString();
    }
    
    private Map<String, String> constructMapPrefixPart(
	    WherePartPojo wherePartPojo, ConstructPartPojo constructPartPojo) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("ktbs:", "http://liris.cnrs.fr/silex/2009/ktbs#");
	map.put("rdfs:", "http://www.w3.org/2000/01/rdf-schema#");
	map.put("afn:", "http://jena.hpl.hp.com/ARQ/function#");
	map.put("fn:", "http://www.w3.org/2005/xpath-functions#");
	map.put("d3kode:", "java:org.liris.sparql.functions.");
	
	
	if(wherePartPojo != null)
	 map.put("modelSrc:", wherePartPojo.getTraceModelUri());
	if(constructPartPojo != null)
	 map.put("modelDest:", constructPartPojo.getTraceModelUri());
	return map;
    }

    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public Integer getId() {
	return id;
    }

    public void setWherePart(WherePartPojo wherePart) {
	this.wherePart = wherePart;
    }

    public WherePartPojo getWherePart() {
	return wherePart;
    }

    public void setConstructPart(ConstructPartPojo constructPart) {
	this.constructPart = constructPart;
    }

    public ConstructPartPojo getConstructPart() {
	return constructPart;
    }

    public Boolean getIsInKtbs() {
	IMethod method = SingleKtbs.getInstance().getMethod(getUri());
	
        return !(method == null);
    }

    public void setIsInKtbs(Boolean isInKtbs) {
        this.isInKtbs = isInKtbs;
    }

    public void setKtbsString(String ktbsString) {
	this.ktbsString = ktbsString;
    }

    public String getKtbsString() {
	return ktbsString;
    }

    /**.
     * @return
     */
    public String getDescription() {
	return description;
    }
    public void setDescription(String description) {
	this.description = description;
    }
    
    public boolean getIsNotExistsRule(){ 
	if(this.getWherePart() != null && this.getWherePart().getListFilter() != null){
		FilterPojo firstFilter = this.getWherePart().getListFilter().get(0);
		OperandePojo firstOperande = (OperandePojo)firstFilter.getOperatorOrOperande().get(0);
		if(firstOperande != null && firstOperande.getLabel().equals(MTraceConst.SPARQL_NOT_EXISTS_OPEN)){
		    return true;
		}
	}
	return false;
    }
}
