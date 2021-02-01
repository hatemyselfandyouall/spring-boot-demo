package com.wangxinenpu.springbootdemo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by yinjh on 2019/1/14.
 */
public class TreeUtil {

    /**
     * 将JSONArray数组转为树状结构
     *
     * @param arr 需要转化的数据
     * @param id 数据唯一的标识键值
     * @param pid 父id唯一标识键值
     * @param child 子节点键值
     * @return JSONArray
     */
    public static JSONArray listToTree(JSONArray arr, String id, String pid,
                                       String child) {
        JSONArray r = new JSONArray();
        JSONObject hash = new JSONObject();
        // 将数组转为Object的形式，key为数组中的id
        for (int i = 0; i < arr.size(); i++) {
            JSONObject json = (JSONObject) arr.get(i);
            hash.put(json.getString(id), json);
        }
        // 遍历结果集
        for (int j = 0; j < arr.size(); j++) {
            // 单条记录
            JSONObject aVal = (JSONObject) arr.get(j);
            if(aVal.get(pid) != null) {
                // 在hash中取出key为单条记录中pid的值
                JSONObject hashVP = (JSONObject) hash.get(aVal.get(pid).toString());
                // 如果记录的pid存在，则说明它有父节点，将她添加到孩子节点的集合中
                if (hashVP != null) {
                    // 检查是否有child属性
                    if (hashVP.get(child) != null) {
                        JSONArray ch = (JSONArray) hashVP.get(child);
                        ch.add(aVal);
                        hashVP.put(child, ch);
                    } else {
                        JSONArray ch = new JSONArray();
                        ch.add(aVal);
                        hashVP.put(child, ch);
                    }
                } else {
                    r.add(aVal);
                }
            } else {
                r.add(aVal);
            }
        }
        return r;
    }



    public static JSONArray listToTree2(JSONArray arr, String id, String pid,
                                        String child) {
        JSONArray r = new JSONArray();
        JSONObject hash = new JSONObject();

        // 将数组转为Object的形式，key为数组中的id
        for (int i = 0; i < arr.size(); i++) {
            JSONObject json = (JSONObject) arr.get(i);
            hash.put(json.getString(id), json);
        }
        // 遍历结果集
        for (int j = 0; j < arr.size(); j++) {
            // 单条记录
            JSONObject areaList = new JSONObject();
            JSONObject aVal = (JSONObject) arr.get(j);
            JSONObject out = hash.getJSONObject(aVal.getString("areaId"));
            areaList.put("areaId",out.getString("areaId"));
            areaList.put("areaName",out.getString("areaName"));
            areaList.put("parentId",out.getString("parentId"));
            aVal.remove("areaId");
            aVal.remove("areaName");
            aVal.remove("parentId");
            if (out.containsKey("systemList")){
                JSONArray area = out.getJSONArray("systemList");
                area.add(aVal);
                areaList.put("systemList",area);
            }else {
                JSONArray area = new JSONArray();
                area.add(aVal);
                areaList.put("systemList",area);
            }
           hash.put(areaList.getString("areaId"),areaList);
        }
        Set<String> strings = hash.keySet();
        for (String s:strings){
            JSONObject object = hash.getJSONObject(s);
            r.add(object);
        }
        return r;
    }

    public static JSONArray groupBy(JSONArray _befortArray, String byKey, String byName) {

        Map<Object, String> map = new HashMap<Object, String>();
        JSONArray _resuleArray = new JSONArray();
        for (Iterator iterator = _befortArray.iterator(); iterator.hasNext();) {
            JSONObject _item = (JSONObject) iterator.next();
            // 如果JSON分组不存在

            if (StringUtils.isBlank(map.get(_item.get(byKey)))) {
                JSONArray jsonArray = JSONArray.parseArray(_item.getString(byKey));
                if (jsonArray!=null){
                    for (Object i : jsonArray){
                        _item.put(byKey,i);
                        JSONObject _group_obj = new JSONObject();
                        JSONArray _group_list = new JSONArray();
                        _group_obj.put(byKey, _item.get(byKey));
                        _group_obj.put(byName, _item.get(byName));
                        _group_list.add(_item);
                        _group_obj.put("data", _group_list);
                        map.put(_item.get(byKey), "exist");
                        _resuleArray.add(_group_obj);
                    }

                }
            } else {
                for (int _i = 0; _i < _resuleArray.size(); _i++) {
                    JSONObject _temp_obj = _resuleArray.getJSONObject(_i);
                    if (_temp_obj.get(byKey).equals(_item.get(byKey))) {
                        _temp_obj.getJSONArray("data").add(_item);
                    }
                }
            }

        }
        return _resuleArray;
    }
}
