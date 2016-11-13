package com.zhuoyue.model;

import java.util.HashMap;
import java.util.Map;
/*
 * @author 兰心序
 * */
public class ViewObject {
	private Map<String, Object> objs = new HashMap<String, Object>();

	public Object get(String key) {
		return objs.get(key);
	}

	public void set(String key, Object value) {
		objs.put(key, value);
	}
	public Map<String,Object> getVOMap(){
		return objs;
	}
}
