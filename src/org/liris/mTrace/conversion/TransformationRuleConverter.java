package org.liris.mTrace.conversion;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.liris.ktbs.domain.interfaces.ITraceModel;
import org.liris.mTrace.actions.json.ListValue;
import org.liris.mTrace.kTBS.SingleKtbs;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

public class TransformationRuleConverter extends DefaultTypeConverter   {

	@Override
	public Object convertValue(Map<String, Object> context, Object target, Member member, String propertyName,
			Object value, Class toType) {
		String[] tmpValue = (String[])value;
		if(toType.equals(ITraceModel.class)){
			return SingleKtbs.getInstance().getTraceModel(tmpValue[0]);
		}else if(toType.equals(SortedMap.class)){
			Pattern p = Pattern.compile("[\\{\\}\\=\\, ]++");
			String[] split = p.split( tmpValue[0] );
			SortedMap<String, String> tmpMap = new TreeMap<String, String>();
			for (int i = 1; i < split.length; i = i+2) {
				tmpMap.put(split[i], split[i+1]);
			}
			return tmpMap;
		}else
			return super.convertValue(context, target, member, propertyName, value, toType);
		}

	@Override
	public Object convertValue(Map<String, Object> context, Object value, Class toType) {
		// TODO Auto-generated method stub
		return super.convertValue(context, value, toType);
	}

	@Override
	public Object convertValue(Object value, Class toType) {
		// TODO Auto-generated method stub
		return super.convertValue(value, toType);
	}
}
