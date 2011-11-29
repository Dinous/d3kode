/**.
 * 
 */
package org.liris.mTrace.tools;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.liris.ktbs.domain.interfaces.IAttributeType;
import org.liris.ktbs.domain.interfaces.IBase;
import org.liris.ktbs.domain.interfaces.IObselType;
import org.liris.ktbs.domain.interfaces.ITraceModel;
import org.liris.mTrace.kTBS.SingleKtbs;

/**.
 * @author Dino
 * @27 avr. 2011
 */
public class TraceModelManagement {

    private File traceModelFile;
    private TraceModelManagement(){}
    
    public TraceModelManagement(File oneTraceModelFile){
	this();
	this.setTraceModelFile(oneTraceModelFile);
    }
    
    public boolean checkTraceModel() throws IOException{
	boolean ret = true;
	/**.
	 * Chaque ligne doit contenir :
	 * 1. catégorie faisant partie de l enum EnumModelTraceObject
	 * 2. nom de l'objet
	 * liste de triplet (nom de l'attribut, type de l'attribut, nullable)
	 */
	//TODO encodage
	List<String> listContent = FileUtils.readLines(getTraceModelFile(), "ISO-8859-1");
	
	for (int i = 0; i < listContent.size(); i++) {
	    	String content = listContent.get(i); 
		String[] contentTab = StringUtils.splitPreserveAllTokens(content, ";");
		//Vérification de la première cellule
		String categ = contentTab[0];
		for (int j = contentTab.length -1; j > 0; j--) {
		    if(StringUtils.isEmpty(contentTab[j])){
			contentTab = (String[])ArrayUtils.remove(contentTab, j);
		    }
		}
		EnumModelTraceObject categEnum = EnumModelTraceObject.getEnumModelTraceObject(categ); 
		if(categEnum != EnumModelTraceObject.UNDEFINED){
		    switch (categEnum) {
		    case OBSEL_TYPE:
			//Vérification que l'on a bien le triplet (attribut,type,nullable)
			//1. vérifier que la liste des cellules suivant les deux premières est bien un multiple de 3
			if((contentTab.length - 2) % 3 == 0){
			    //2. vérifier que la deuxième cellule de ces tripplets contient bien un TYPE
			    for (int j = 3; j < contentTab.length-1; j = j+3) {
				String type = contentTab[j];
				EnumXSDType xsdType = EnumXSDType.getEnumModelTraceObject(type);
				if(xsdType != EnumXSDType.UNDEFINED){
				}else{
				    System.out.println("Wrong attribute type ("+content+") line "+i);
				    ret = false;
				}
			    }
			    //3. vérifier que la troisième cellule contient bien un booléen
			    //il spécification spéciale d'un obselType
			    if(contentTab.length > 2){
				for (int j = 4; j < contentTab.length-1; j = j+3) {
				    String nullable = contentTab[j];
				
				    if(nullable != null && (
					nullable.toLowerCase().equals(Boolean.toString(true)) 
					|| 
					nullable.toLowerCase().equals(Boolean.toString(false))
					)){
				    }else{
					System.out.println("Wrong boolean value ("+content+") line "+i);
					ret = false;
				    }
				}
			    }
			    //4. vérifier que l'on a bien 2 champs appelés dateDébut dateFin de type DATETIME
//			    boolean dateDebutFound = false;
//			    boolean dateFinFound = false;
//			    boolean dateDebutType = false;
//			    boolean dateFinType = false;
//			    for (int j = 2; j < contentTab.length-1; j = j+3) {
//				//vérification du nom
//				String nomDate = contentTab[j];
//				String typeDate = contentTab[j+1];
//				
//				if(nomDate != null ){
//					if(nomDate.toLowerCase().equals("datedebut")){
//					    dateDebutFound = true;
//					    if(typeDate != null && typeDate.toLowerCase().equals("datetime")){
//						dateDebutType = true;
//					    }
//					}
//					if(nomDate.toLowerCase().equals("datefin")){
//					    dateFinFound = true;
//					    if(typeDate != null && typeDate.toLowerCase().equals("datetime")){
//						dateFinType = true;
//					    }
//					}
//				}
//			    }
//			    if(dateDebutFound && dateFinFound){
//				if(dateDebutType && dateFinType){
//				    
//				}else{
//				    System.out.println("Wrong field type (datedebut, dateFin) line "+i);
//				}
//			    }else{
//				System.out.println("Missing field  (datedebut, dateFin) line "+i);
//				ret = false;
//			    }
			}else{
			    System.out.println("Wrong attribute type declaration  ("+content+") line "+i);
			    ret = false;
			}
			break;
		    case METADATA:
			//1. vérifier que la cellule qui suit les 2 premières est bien un TYPE
			String type = contentTab[2];
			EnumXSDType xsdType = EnumXSDType.getEnumModelTraceObject(type);
			if(xsdType != EnumXSDType.UNDEFINED){
			}else{
			    System.out.println("Wrong attribute type ("+content+") line "+i);
			    ret = false;
			}
			break;
		    case RELATION_TYPE:
			//TODO à implémenter
		    break;
		    default:
			break;
		    }
		}else{
		    System.out.println("Wrong categ "+categ+" unknow ("+content+") line "+i);
		    return false;
		}
		
	}
	return ret;
    }

    public void setTraceModelFile(File traceModelFile) {
	this.traceModelFile = traceModelFile;
    }
    public File getTraceModelFile() {
	return traceModelFile;
    }

    /**.
     * @return
     * @throws IOException 
     */
    public boolean writeToKtbs(String modelLabel) throws IOException {
	IBase base = SingleKtbs.getInstance().getCurrentBase();
	ITraceModel traceModel = SingleKtbs.getInstance().createTraceModel(base, null, modelLabel);
	return writeToKtbs(traceModel);
    }
    
    /**.
     * @return
     * @throws IOException 
     */
    public boolean writeToKtbs(ITraceModel traceModel) throws IOException {
	
	List<String> listContent = FileUtils.readLines(getTraceModelFile(), "ISO-8859-1");
	try {
	    for (int i = 0; i < listContent.size(); i++) {
	        	String content = listContent.get(i); 
	    	String[] contentTab = StringUtils.splitPreserveAllTokens(content, ";");
	    	for (int j = contentTab.length -1; j > 0 && StringUtils.isEmpty(contentTab[j]); j--) {
		    if(StringUtils.isEmpty(contentTab[j])){
			contentTab = (String[])ArrayUtils.remove(contentTab, j);
		    }
		}
	    	
	    	EnumModelTraceObject categEnum = EnumModelTraceObject.getEnumModelTraceObject(contentTab[0]);
	    	 switch (categEnum) {
	    	    case OBSEL_TYPE:
	    		String obselTypeLocalName = "_"+String.valueOf(new Date().getTime());
	    		IObselType obselType = SingleKtbs.getInstance().createObselType(traceModel, obselTypeLocalName.replaceAll(" ", ""), contentTab[1]);
	    		for (int j = 2; j < contentTab.length-1; j=j+3) {
	    		    String attributeTypeName = contentTab[j];
	    		    String attributeTypeType = contentTab[j+1];
	    		    String attributeTypeNullable = contentTab[j+2];
	    		    IAttributeType attributeType = SingleKtbs.getInstance().createAttributeType(traceModel, obselType, "_"+String.valueOf(new Date().getTime()), attributeTypeType, attributeTypeName);
	    		}
	    		break;
	    	    case METADATA:
	    		SingleKtbs.getInstance().addMetadata(traceModel, contentTab[1], contentTab[2]);
	    		break;
	    	    case RELATION_TYPE:
	    		break;
	    	    default:
	    		break;
	    	 }			    
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	SingleKtbs.getInstance().refresh(traceModel);
	return (SingleKtbs.getInstance().getTraceModel(traceModel.getUri()) != null);
    }
}
