package com.zhuoyue.util;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/*
 * @author 兰心序
 * @param code:状态码    1正常  0异常
 * @param msg :消息	 
 *	
 * */
public class JsonUtil {
	public static String getJSONString(int code) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		return json.toJSONString();
	}

	public static String getJSONString(int code, String msg) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		return json.toJSONString();
	}

	public static String getJSONString(int code, Map<String, Object> map) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			json.put(entry.getKey(), entry.getValue());
		}
		return json.toJSONString();
	}
	
	public static String getJSONString(int code,List<Map<String,Object>> list){
		JSONObject json = new JSONObject();
		json.put("code", code);
		json = getJSON(json, list);
		return json.toJSONString();
	} 
	
	private static JSONObject getJSON(JSONObject json,List<Map<String,Object>> list){
		JSONArray jsonArray = new JSONArray();
		for(Map<String, Object> map : list){
			JSONObject jsobj = new JSONObject();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				jsobj.put(entry.getKey(), entry.getValue());
			}
			jsonArray.add(jsobj);
		}
		json.put("msg",jsonArray);
		return json;
	}
}
