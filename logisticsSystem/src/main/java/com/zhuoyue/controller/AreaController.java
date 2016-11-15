package com.zhuoyue.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuoyue.dao.SortStationDAO;
import com.zhuoyue.model.Area;
import com.zhuoyue.model.City;
import com.zhuoyue.model.Province;
import com.zhuoyue.model.SortStation;
import com.zhuoyue.service.AreaService;
import com.zhuoyue.util.JsonUtil;
/*
 * @author 兰心序
 * */
@Controller
public class AreaController {
	@Autowired
	AreaService areaService;
	@Autowired
	SortStationDAO sortStationDAO;
	
	@RequestMapping("/getProvince")
	@ResponseBody
	public String getProvince() {
		List<Province> list =  areaService.selectAllProvince();
		if (list == null) {
			return JsonUtil.getJSONString(0);
		}
		List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
		for (Province province : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("provincecode", province.getCode());
			map.put("name", province.getName());
			ls.add(map);
		}
		   return JsonUtil.getJSONString(1, ls);
	}

	@RequestMapping("/getCity")
	@ResponseBody
	public String getCity(@RequestParam("provinceCode") int provinceCode) {
		List<City> list = areaService.selectCitiesByProvinceCode(provinceCode);
		if (list == null) {
			return JsonUtil.getJSONString(0);
		}
		List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
		for (City city : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("citycode", city.getCode());
			map.put("name", city.getName());
			ls.add(map);
		}
		   return JsonUtil.getJSONString(1, ls);
	}
	
	@RequestMapping("/getArea")
	@ResponseBody
	public String getArea(@RequestParam("cityCode") int cityCode) {
		List<Area> list = areaService.selectAreasByCityCode(cityCode);
		if (list == null) {
			return JsonUtil.getJSONString(0);
		}
		List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
		for (Area area : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("areacode", area.getCode());
			map.put("name", area.getName());
			ls.add(map);
		}
		   return JsonUtil.getJSONString(1, ls);
	}
	
	@RequestMapping("/getStation")
	@ResponseBody
	public String getStation(@RequestParam("areaCode") int areaCode) {
		List<SortStation> list = sortStationDAO.selectByArea(areaCode);
		if (list == null) {
			return JsonUtil.getJSONString(0);
		}
		List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
		for (SortStation sortStation : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", sortStation.getId());
			map.put("name", sortStation.getStationName());
			ls.add(map);
		}
		   return JsonUtil.getJSONString(1, ls);
	}
}
