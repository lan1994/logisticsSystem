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
	
	public Province getProvinceByCode(int code){
		return provinceDAO.selectById(code);
	}
	
	public City getCityByCode(int code){
		return cityDAO.selectById(code);
	}
	
	public Area getAreaByCode(int code){
		return areaDAO.selectById(code);
	}
	
	public Area selectAreaById(int id){
		return areaDAO.selectById(id);
	}
	
	public Province selectProvinceById(int id){
		return provinceDAO.selectById(id);
	}
	
	public City selectCityById(int id){
		return cityDAO.selectById(id);
	}
	
	public Area selectAreaByCode(int code){
		return areaDAO.selectByCode(code);
	}
	
	public Province selectProvinceByCode(int code){
		return provinceDAO.selectByCode(code);
	}
	
	public City selectCityByCode(int code){
		return cityDAO.selectByCode(code);
	}
	
	
}
