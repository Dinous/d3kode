/**.
 * 
 */
package test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.liris.ktbs.service.impl.ObselBuilder;
import org.liris.ktbs.utils.KtbsUtils;
import org.liris.mTrace.kTBS.SingleKtbs;

/**.
 * @author Dino
 * @10 juin 2011
 */
public class TestCSVRead {

    /**.
     * @param args
     */
    public static void main(String[] args) {
	String traceModelUri = "TRACE_MODEL_URI";
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'H:m:s.S");
	File obselFile = new File("C:\\Users\\Dino\\Dropbox\\these_olivier\\Memoire Dino\\Log Simu EDF\\journal_alarme_actionOperateur_actionInstrcuteur_actionTor_ActionAna.csv");
	
	try {
	    List<String> lines = FileUtils.readLines(obselFile, "ISO-8859-1");
	        Map<String, String[]> obselTypeFromKtbs = new HashMap<String, String[]>();
	    	for (String string : lines) {
	    	    //ObselBuilder obselBuilder = SingleKtbs.getInstance().getRoot().getStoredTraceService().newObselBuilder(storedTrace) ;
	    	    String[] cols = StringUtils.splitByWholeSeparatorPreserveAllTokens(string, ";");
	    	    if(cols.length > 0){
	    		//check template traceModel
	    		if(cols[0].equals(cols[1]) && cols[1].equals("")){
	    		    //System.out.println("model_ligne");
	    		
	    		    //traceModel declaration
	    		    String[] attributeTypes = new String[cols.length];
//			    attributeTypes[0] = traceModelUri + "ktbs#hasBeginDT";
//			    attributeTypes[1] = traceModelUri + "ktbs#hasEndDT";
//			    attributeTypes[2] = "http://www.w3.org/2000/01/rdf-schema#label";
	    		    for (int i=4;i<cols.length;i++) {
	    			if(!StringUtils.isEmpty(cols[i-1])){
	    			//System.out.println("attributeType "+cols[i-1]);
	    			    attributeTypes[i-1] = traceModelUri + cols[i-1];
	    			}
//	    			    else{
//	    			    attributeTypes[i-1] = null;
//	    			}
	    		    }
	    		    obselTypeFromKtbs.put(cols[2], attributeTypes);
	    		    
	    		}else{
	    		System.out.println("obsel_ligne "+ cols[2]);
	    		    //obsel
	    		    String beginDT = cols[0];
	    		    String endDT = cols[1];
	    		    String obselLocalName = cols[2];
	    		    String label = cols[3];
	    		    
//			    obselBuilder.addLabel(label);
//			    			    
//			    obselBuilder.setBeginDT(KtbsUtils.xsdDate(dateFormat.parse(beginDT)));
//			    obselBuilder.setEndDT(KtbsUtils.xsdDate(dateFormat.parse(endDT)));
//			    obselBuilder.setType(traceModelUri + obselLocalName);
//			    obselBuilder.setSubject("");
//			    
			    for (int i = 4; i < cols.length; i++) {
				if(!StringUtils.isEmpty(obselTypeFromKtbs.get(obselLocalName)[i])){
				    //obselBuilder.addAttribute(obselTypeFromKtbs.get(obselLocalName)[i-1], cols[i-1]);
				    System.out.println(obselTypeFromKtbs.get(obselLocalName)[i]+"   "+ cols[i]);
				}
			    }
//			    //builders.add(obselBuilder);
//			    obselBuilder.create();
			    System.out.println("");
	    		}
	    	    }
	    	}
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

}
