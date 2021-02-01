package com.wangxinenpu.springbootdemo.util;

import star.vo.search.TypeCountVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssemblerHelper {
	/**
	 * 按类型行数转换为map
	 * @param typeCounts
	 * @return
	 */
	public static Map<String, Integer> toMap4Count(List<TypeCountVo> typeCounts){
		Map<String, Integer> typeCountMap = new HashMap<String, Integer>();
		
		for(TypeCountVo typeCount : typeCounts){
			if(typeCount != null){
				typeCountMap.put(typeCount.getType(), typeCount.getCount());
			}
		}
		
		return typeCountMap;
	}
}
