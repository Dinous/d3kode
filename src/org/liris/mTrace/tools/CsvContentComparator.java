package org.liris.mTrace.tools;

import java.util.Comparator;

public class CsvContentComparator implements Comparator<String[]> {

	@Override
	public int compare(String[] arg0, String[] arg1) {
		Integer end0 = Integer.valueOf(arg0[1]);
		Integer end1 = Integer.valueOf(arg1[1]);
		if(end0 > end1){
			return 1;
		}else if(end0 < end1){
			return -1;
		}
		return 0;
	}

}
