package com.zhuoyue.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuoyue.dao.AreaDAO;
import com.zhuoyue.dao.CityDAO;
import com.zhuoyue.dao.ProvinceDAO;
import com.zhuoyue.model.Area;
import com.zhuoyue.model.City;
import com.zhuoyue.model.Province;

@Service
public class AreaService {
	@Autowired
	CityDAO cityDAO;
	
	@Autowired
	AreaDAO areaDAO;
	
	@Autowired
	ProvinceDAO provinceDAO;
	
	public List<City> selectCitiesByProvinceCode(int provincecode){
		return cityDAO.selectByProvinceCode(provincecode);
	}
	
	public List<Area> selectAreasByCityCode(int cityCode){
		return areaDAO.selectByProvinceCode(cityCode);
	}
	
	public List<Province> selectAllProvince(){
		return provinceDAO.selectAll();
	}
}
