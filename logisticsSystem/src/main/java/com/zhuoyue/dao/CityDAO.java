package com.zhuoyue.dao;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.zhuoyue.model.City;
import com.zhuoyue.model.Province;
/*
 * @author 兰心序
 * */
@Mapper
public interface CityDAO {
	String TABLE_NAME = " city";
	String INSERT_FIELDS =" code,name,provincecode";
	String SELECT_FIELDS = " id,"+INSERT_FIELDS;
	
	@Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,")"
		,"values (#{code},#{name})"
		})
	int addCity(City city);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where id=#{id}"})
	City selectById(int id);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,"where provincecode=#{provincecode}"})
	List<City> selectByProvinceCode(int provincecode);
}
