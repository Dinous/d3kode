package test;

import org.liris.ktbs.domain.interfaces.IAttributeType;
import org.liris.ktbs.domain.interfaces.IBase;
import org.liris.ktbs.domain.interfaces.IObselType;
import org.liris.ktbs.domain.interfaces.ITraceModel;
import org.liris.mTrace.WebApplication;
import org.liris.mTrace.kTBS.SingleKtbs;

public class TestKtbs {

	public static void main(String[] args) {
	    SingleKtbs.getInstance().initialize(new WebApplication("http://localhost:8001/"));
	    IBase base = SingleKtbs.getInstance().getBase("dino");
	    for(int i =0 ; i< 2;i++){
	    long debut = System.currentTimeMillis();
	    long fin = System.currentTimeMillis();
	    
	    for(ITraceModel traceModel :base.getTraceModels()){
		
		System.out.println("");
		System.out.println(traceModel);
		    for(IAttributeType attr : traceModel.getAttributeTypes()){
			for(IObselType obselType :attr.getDomains()){
			    obselType.getUri();
			}
		    }
		    
	    }
	    fin = System.currentTimeMillis();
	    System.out.println(fin - debut);
	    }
	}
}
