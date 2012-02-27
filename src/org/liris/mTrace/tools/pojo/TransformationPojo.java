/**.
 * 
 */
package org.liris.mTrace.tools.pojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang.StringUtils;
import org.liris.ktbs.domain.interfaces.IMethod;
import org.liris.ktbs.domain.interfaces.ITraceModel;
import org.liris.mTrace.MTraceConst;
import org.liris.mTrace.kTBS.SingleKtbs;
import org.liris.mTrace.tools.pojo.rule.RulePojo;

/**.
 * @author Dino
 * @29 avr. 2011
 */
public class TransformationPojo  implements Serializable{

    /**.
     * 
     */
    private static final long serialVersionUID = 1423198026360831072L;
    private String uri;
    private String localName;
    private String label;
    private String traceModelSourceSelected;
    private transient ITraceModel traceModelSource;
    private String traceModelTargetSelected;
    private transient ITraceModel traceModelTarget;
    private List<RulePojo> rules; 
    private String description;
    private Boolean isInKtbs = false;
    private boolean changed;
    
    public boolean saveIntoKtbs() throws IOException{
	//TODO à enelver quand le kTBS gèrera mieux le SPARQL
	String inherits = SingleKtbs.getInstance().getCurrentBase() + "sparql11";
	IMethod methodSparql11 = SingleKtbs.getInstance().getMethod(SingleKtbs.getInstance().getCurrentBase() + "sparql11");
	if(methodSparql11 == null){
	    SingleKtbs.getInstance().createMethodExternalKtbs("sparql11", "Appel externe");   
	}
	
	isInKtbs = true;
	String submethods = "";
	// create
	for (RulePojo rule : rules) {
	    rule.createKtbsString();
	    IMethod method = null;
	    if (rule.getUri() != null && rule.getUri().startsWith("http")) {
		// MAJ
		method = SingleKtbs.getInstance().updateMethodKtbs(
			rule.getUri(), rule.getKtbsString(), rule.getLabel(),
			rule.getDescription(), getTraceModelTarget(), inherits);
	    } else {
		method = SingleKtbs.getInstance().createMethodKtbs(null,
			rule.getLabel(), rule.getKtbsString(),
			rule.getDescription(), getTraceModelTarget(), inherits);
	    }
	    if (method != null) {
		submethods += " " + method.getLocalName();
		rule.setIsInKtbs(true);
		rule.setUri(method.getUri());
	    } else {
		rule.setIsInKtbs(false);
		rule.setUri(null);
	    }

	    isInKtbs = isInKtbs && rule.getIsInKtbs();
	}
	// test exist supermethod
	if (!"".equals(submethods)) {
	    IMethod superMethod = SingleKtbs.getInstance()
		    .createOrUpdateSuperMethodKtbs(submethods.substring(1),
			    localName, label, traceModelTarget,
			    getDescription());
	    SingleKtbs.getInstance().refresh(superMethod.getUri());
	    localName = superMethod.getLocalName();
	}
	
	serialize(false);
	
	return isInKtbs;
    }
    
    
    public static List<RulePojo> deserializeAll(String transformationLocalName, String traceModelSource, String traceModelDest) throws IOException, ClassNotFoundException {
	List<RulePojo> rulePojos = new ArrayList<RulePojo>();
	File folderTransformation = new File(MTraceConst.WORK_TRANSFORMATION_PATH);
    	IOFileFilter fileFilter = FileFilterUtils.prefixFileFilter("_");
    	Collection<File> transformationFiles = FileUtils.listFiles(folderTransformation, fileFilter, null);
    	for (File file : transformationFiles) {
    	    //if(!file.getAbsolutePath().equals(transformationLocalName)){
        	    TransformationPojo transformationPojo = TransformationPojo.deserialize(file);
        	    if(transformationPojo != null && transformationPojo.getRules() != null  && transformationPojo.getTraceModelSourceSelected().equals(traceModelSource)
        		    && transformationPojo.getTraceModelTargetSelected().equals(traceModelDest)){
        		rulePojos.addAll(transformationPojo.getRules());
        	    }
    	    //}
	}
    	return rulePojos;
    }
    
    
    public static TransformationPojo deserialize(File methodFile) throws IOException, ClassNotFoundException {
	if(!methodFile.exists()){
	    TransformationPojo newTransformationPojo = new TransformationPojo();
	    newTransformationPojo.serialize(methodFile, true);
	}
	FileInputStream fis = new FileInputStream(methodFile);
	ObjectInputStream ois = new ObjectInputStream(fis);		
	TransformationPojo transformationPojo =(TransformationPojo)ois.readObject();
	ois.close();
	if(!StringUtils.isEmpty(transformationPojo.traceModelSourceSelected)){
	    ITraceModel traceModel = SingleKtbs.getInstance().getTraceModel(transformationPojo.traceModelSourceSelected);
	    if(traceModel == null)
		return null;
	    transformationPojo.setTraceModelSource(traceModel);
	}
	if(!StringUtils.isEmpty(transformationPojo.traceModelTargetSelected)){
	    ITraceModel traceModel = SingleKtbs.getInstance().getTraceModel(transformationPojo.traceModelTargetSelected);
	    if(traceModel == null)
		return null;
	    transformationPojo.setTraceModelTarget(traceModel);
	}
	return transformationPojo;
    }

    public void serialize( File methodFile, boolean changed) throws IOException {
    	FileOutputStream fos = new FileOutputStream(methodFile); 
    	ObjectOutputStream oos = new ObjectOutputStream(fos);
    	this.setUri(methodFile.getAbsolutePath());
    	this.setChanged(changed);
    	oos.writeObject(this);
    	oos.close();
    }
    
    /**.
     * @throws IOException 
     * 
     */
    public void serialize() throws IOException {
    	serialize(true);
    } 
    
    public void serialize(boolean changed) throws IOException {
    	serialize(new File(this.uri), changed);
        }
    
    public void setLocalName(String localName) {
	this.localName = localName;
    }
    public String getLocalName() {
	return localName;
    }
    public void setTraceModelSourceSelected(String traceModelSourceSelected) {
	this.traceModelSourceSelected = traceModelSourceSelected;
    }
    public String getTraceModelSourceSelected() {
	return traceModelSourceSelected;
    }
    public void setTraceModelTargetSelected(String traceModelTargetSelected) {
	this.traceModelTargetSelected = traceModelTargetSelected;
    }
    public String getTraceModelTargetSelected() {
	return traceModelTargetSelected;
    }

    public ITraceModel getTraceModelSource() {
        return traceModelSource;
    }

    public void setTraceModelSource(ITraceModel traceModelSource) {
        this.traceModelSource = traceModelSource;
    }

    public ITraceModel getTraceModelTarget() {
        return traceModelTarget;
    }

    public void setTraceModelTarget(ITraceModel traceModelTarget) {
        this.traceModelTarget = traceModelTarget;
    }

    public void setUri(String uri) {
	this.uri = uri;
    }

    public String getUri() {
	return uri;
    }

    public void setRules(List<RulePojo> rules) {
	this.rules = rules;
    }

    public List<RulePojo> getRules() {
	return rules;
    }

    public void setIsInKtbs(Boolean isInKtbs) {
	this.isInKtbs = isInKtbs;
    }

    public Boolean getIsInKtbs() {
    	return !changed;
    	/*Long tm = System.currentTimeMillis();
    	System.out.println("getIsInKtbs debut " + (System.currentTimeMillis()- tm));
	File file = new File(this.getUri());
	if(file.exists()){
		
	IMethod superMethod = SingleKtbs.getInstance().getMethod(SingleKtbs.getInstance().getCurrentBase().getUri() + this.localName);
	
	if(superMethod!=null && superMethod.getMethodParameters() != null){
	    for (IMethodParameter methodParameter : superMethod.getMethodParameters()) {
		if(methodParameter.getName().equals("submethods")){
		    if(methodParameter.getValue() == null || methodParameter.getValue().equals("null")){
			isInKtbs = false;
			System.out.println("getIsInKtbs fin 225 " + (System.currentTimeMillis()- tm));
			    return false;
		    }
		break;	
		}
	    }
	}
	for (int i=0;i<rules.size();i++) {
	    RulePojo rule = rules.get(i);
	    if(rule.getUri() != null && rule.getUri().startsWith("http")){
		IMethod iMethod = SingleKtbs.getInstance().getMethod(rule.getUri());
		String methodString = null;
		if(iMethod != null){
		for(IMethodParameter parameter :iMethod.getMethodParameters()){
		    if(parameter.getName().equals("sparql")){
			methodString = parameter.getValue();
		    }
		}
		//rule.createKtbsString();
		if(methodString != null && rule.getKtbsString() != null && !rule.getKtbsString().equals(methodString)){
		    isInKtbs = false;
		    break;
		}else{
		    if(!rule.getIsInKtbs()){
			isInKtbs = false;
			break;
		    }else{
			isInKtbs =true;
		    }
		}
		}else{
		    isInKtbs = false;
		    rule.setUri(null);
		    try {
			serialize();
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		}
	    }else{
		isInKtbs = false;
		break;
	    }
	}
    }else{
    	System.out.println("getIsInKtbs fin 270 " + (System.currentTimeMillis()- tm));
	return false;
    }
	
	System.out.println("getIsInKtbs fin 274 " + (System.currentTimeMillis()- tm));
	return isInKtbs;*/
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getDescription() {
	return description;
    }

    public void setLabel(String label) {
	this.label = label;
    }

    public String getLabel() {
	return label;
    }


    /**.
     * @param ruleToCopyUri
     * @return
     */
    public static RulePojo findRulePojo(String transformationLocalName, String ruleToCopyUri) throws Exception {
	File folderTransformation = new File(MTraceConst.WORK_TRANSFORMATION_PATH);
    	IOFileFilter fileFilter = FileFilterUtils.prefixFileFilter("_");
    	Collection<File> transformationFiles = FileUtils.listFiles(folderTransformation, fileFilter, null);
    	for (File transformationFile : transformationFiles) {
    	    TransformationPojo transformationPojo = TransformationPojo.deserialize(transformationFile);
    	    //&& !transformationPojo.getLocalName().equals(transformationLocalName)
    	    if(transformationPojo != null ){
        	    for (RulePojo rulePojo : transformationPojo.getRules()) {
    		    	if(rulePojo.getUri() != null && rulePojo.getUri().equals(ruleToCopyUri)){
    		    	    return rulePojo;
    		    	}
        	    }
    	    }
    	}
	return null;
    }
    
    /**.
     * @param ruleToCopyUri
     * @return
     */
    public static TransformationPojo findTransformationPojo(String transformationLocalName, String ruleToCopyUri) throws Exception {
	File folderTransformation = new File(MTraceConst.WORK_TRANSFORMATION_PATH);
    	IOFileFilter fileFilter = FileFilterUtils.prefixFileFilter("_");
    	Collection<File> transformationFiles = FileUtils.listFiles(folderTransformation, fileFilter, null);
    	for (File transformationFile : transformationFiles) {
    	    TransformationPojo transformationPojo = TransformationPojo.deserialize(transformationFile);
    	    //&& !transformationPojo.getLocalName().equals(transformationLocalName)
    	    if(transformationPojo != null ){
        	    for (RulePojo rulePojo : transformationPojo.getRules()) {
    		    	if(rulePojo.getUri() != null && rulePojo.getUri().equals(ruleToCopyUri)){
    		    	    return transformationPojo;
    		    	}
        	    }
    	    }
    	}
	return null;
    }


	public boolean isChanged() {
		return changed;
	}


	public void setChanged(boolean changed) {
		this.changed = changed;
	}
}
