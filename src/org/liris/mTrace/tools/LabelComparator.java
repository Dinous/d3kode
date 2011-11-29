package org.liris.mTrace.tools;

import java.util.Comparator;

import org.liris.ktbs.domain.interfaces.IKtbsResource;

public class LabelComparator implements Comparator<IKtbsResource> {

	@Override
	public int compare(IKtbsResource o1, IKtbsResource o2) {
		if(o1.getLabel() != null && o2.getLabel() != null){
			String label1 = o1.getLabel().toUpperCase();
			String label2 = o2.getLabel().toUpperCase();
 
			return label1.compareTo(label2);
		}else{
			return 1;
		}
	}

	
}
