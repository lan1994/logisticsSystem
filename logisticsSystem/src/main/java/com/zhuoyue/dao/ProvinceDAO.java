package com.zhuoyue.dao;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.zhuoyue.model.Province;
/*
 * @author 兰心序
 * */
@Mapper
public interface ProvinceDAO {
	String TABLE_NAME = " province";
	String INSERT_FIELDS =" code,name";
	String SELECT_FIELDS = " id,"+INSERT_FIELDS;
	
	@Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,")"
		,"values (#{code},#{name})"
		})
	int addProvice(Province province);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where id=#{id}"})
	Province selectById(int id);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where code=#{code}"})
	Province selectByCode(int code);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME})
	List<Province> selectAll();
}
