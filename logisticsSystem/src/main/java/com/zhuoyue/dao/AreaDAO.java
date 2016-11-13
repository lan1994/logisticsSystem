package com.zhuoyue.dao;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.zhuoyue.model.Area;
import com.zhuoyue.model.Province;
/*
 * @author 兰心序
 * */
@Mapper
public interface AreaDAO {
	String TABLE_NAME = " area";
	String INSERT_FIELDS =" code,name,citycode";
	String SELECT_FIELDS = " id,"+INSERT_FIELDS;
	
	@Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,")"
		,"values (#{code},#{name})"
		})
	int addArea(Area area);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where id=#{id}"})
	Area selectById(int id);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where code=#{code}"})
	Area selectByCode(int code);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,"where citycode=#{citycode}"})
	List<Area> selectByProvinceCode(int citycode);
}
