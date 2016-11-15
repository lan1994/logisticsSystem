package com.zhuoyue.dao;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.boot.test.IntegrationTest;

import com.zhuoyue.model.Repertory;
@Mapper
public interface RepertoryDAO {
	String TABLE_NAME = " repertory";
	String INSERT_FIELDS =" sortstation_id, order_id, status, date,nextStationCode,user_id,modify";
	String SELECT_FIELDS = " id,"+INSERT_FIELDS;
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,
		    " where sortstation_id = #{sortstationId} and status = #{status} limit #{offset},#{limit}"})
	public List<Repertory> selectByStationId(@Param("sortstationId") int sortstationId,
													  @Param("status") int status,
													  @Param("offset") int offset,
													  @Param("limit")  int limit 
													 );	
	@Select({"select count(*)"," from ",TABLE_NAME,
    " where sortstation_id = #{sortstationId} and status = #{status}"})
	public int selectCountByStaion(@Param("sortstationId") int sortstationId,@Param("status") int status);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,
	    	" where order_id = #{orderId} order by date desc"})
	public List<Repertory> selectByOrderId(long orderId);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,
	" where id = #{id} "})
	public Repertory selectById(int id);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,
	" where sortstation_id = #{sortstationId} and order_id = #{orderId} order by date desc limit 0,1"})
	public Repertory selectByIdAndStation(@Param("sortstationId") int sortstationId,@Param("orderId")  long orderId);
	
	@Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,")",
			" values(#{sortstationId},#{orderId},#{status},#{date},#{nextStationCode},#{userId},#{modify})"	
	})
	public int addRepertory(Repertory repertory);
	
	@Update({"update ",TABLE_NAME," set status = #{status} where id=#{id}"})
	public void updateStatus(@Param("status") int status,@Param("id") int id);
	
	@Update({"update ",TABLE_NAME," set nextStationCode = #{next},modify=#{modify},status=#{status} where id=#{id}"})
	public void updateNextStationAndOut(@Param("next") int next,@Param("status")int status,@Param("id") int id,@Param("modify") int modify);
}
