package com.wangxinenpu.springbootdemo.util;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ListUtil {

    public static <T> List<T> intersectionList(List<List<T>> lists){
        if (!CollectionUtils.isEmpty(lists)){
            List<T> fromList=lists.get(0);
            for (int i=0;i<lists.size();i++){
                fromList=intersectionList(fromList,lists.get(i));
            }
            return  fromList;
        }else {
            return new ArrayList<>();
        }
    }

    public static <T> List<T> intersectionList(List<T> lista, List<T> listb){
        return  lista.stream().filter(item -> listb.contains(item)).collect(toList());
    }
}
