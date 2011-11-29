package org.liris.mTrace.tools;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;
import org.liris.ktbs.domain.interfaces.ITraceModel;

public class ComparatorTraceModel<T extends ITraceModel> implements Comparator<ITraceModel> {

	@Override
	public int compare(ITraceModel arg0, ITraceModel arg1) {
		if(StringUtils.isEmpty(arg0.getPropertyValue("level").toString()) ||
			StringUtils.isEmpty(arg1.getPropertyValue("level").toString())){
			return -1;
		}
		Integer lvl0 = Integer.valueOf(arg0.getPropertyValue("level").toString());
		Integer lvl1 = Integer.valueOf(arg1.getPropertyValue("level").toString());
		if(lvl0 <  lvl1){
			return -1;
		}else if(lvl0 > lvl1){
			return 1;
		}
		return 0;
	}

}
