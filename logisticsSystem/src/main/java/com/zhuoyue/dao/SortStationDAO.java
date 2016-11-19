package com.zhuoyue.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zhuoyue.model.SortStation;

/*
 * @author 吴兵兵
 */
@Mapper
public interface SortStationDAO {
	String TABLE_NAME = " sortstation";
	String INSERT_FIELDS =" stationid,stationname,rank,adminid,manager,tel,mobile,address,province,city,area";
	String SELECT_FIELDS = " id,"+INSERT_FIELDS;
	@Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,")"
		,"values (#{stationId},#{stationName},#{rank},#{adminId},#{manager},#{tel},#{mobile},#{address},#{province},#{city},#{area})"
		})
	int AddSortStration(SortStation station);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where id=#{id}"})
	SortStation selectById(int id);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where rank=#{rank}"})
	SortStation selectByRank(int rank);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where rank=#{rank} and city=#{city}"})
	SortStation selectByRankAndCity(@Param("rank")int rank,@Param("city") int city);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where rank=#{rank} and area=#{area}"})
	SortStation selectByRankAndArea(@Param("rank")int rank,@Param("area") int area);
	
	@Delete({"delete from ",TABLE_NAME,"where id=#{id}"})
	void DelStation(int id);
	
	@Update({"update ",TABLE_NAME," set stationid = #{stationname},stationname = #{stationname},adminid = #{adminid},manager = #{manager},"
			+ "tel = #{tel},mobile = #{mobile},address = #{address},city = #{city},area = #{area} where id=#{id}"})
	void updateStation(SortStation station);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME})
	List<SortStation> selectAllStation();
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,"where area=#{area}"})
	List<SortStation> selectByArea(int area);

}
