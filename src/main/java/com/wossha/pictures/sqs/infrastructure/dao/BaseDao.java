package com.wossha.pictures.sqs.infrastructure.dao;

import org.apache.commons.lang3.StringUtils;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.Update;

import java.util.List;
import java.util.Map;

public class BaseDao<t> {
	
	public Query<t> addInClauseBind(Query<t> q, Map<String, List<String>> typesBindMap) {
		Query<t> result = q;
		for (Map.Entry<String, List<String>> entry : typesBindMap.entrySet())
		{
			for (int i = 0; i < entry.getValue().size(); i++) {
				result.bind(entry.getKey()+"_"+i, entry.getValue().get(i));
			}
		}
		
		return result;
	}
	
	public Update addInClauseBindUpdate(Update q, Map<String, List<String>> typesBindMap) {
		Update result = q;
		for (Map.Entry<String, List<String>> entry : typesBindMap.entrySet())
		{
			for (int i = 0; i < entry.getValue().size(); i++) {
				result.bind(entry.getKey()+"_"+i, entry.getValue().get(i));
			}
		}
		
		return result;
	}

	public String generateBingIdentifier(String query, Map<String, List<String>> map) {
		String result = query;
		if (result == null) {
			return null;
		}
		
		for (Map.Entry<String, List<String>> entry : map.entrySet()){
			result = StringUtils.replace(result, "<"+entry.getKey()+">", getBingIdentifier(entry.getKey(), entry.getValue().size()));
		}
		
		return result;
	}

	private String getBingIdentifier(String key, int size) {
		String output = "";
		for (int i = 0; i < size; i++) {
			output += ":"+key+"_"+i;
			if(i<size-1) {
				output = output+",";
			}
		}
		return output;
	}

}
