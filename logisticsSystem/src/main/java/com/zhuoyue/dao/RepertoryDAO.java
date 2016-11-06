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
	String INSERT_FIELDS =" sortstation_id, order_id, status, date,nextStationCode,user_id";
	String SELECT_FIELDS = " id,"+INSERT_FIELDS;
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,
		    " where sortstationId = #{sortstationId} and status = #{status} limit #{offset},#{limit}"})
	public List<Repertory> selectByStationId(@Param("sortstationId") int sortstationId,
													  @Param("status") int status,
													  @Param("offset") int offset,
													  @Param("limit")  int limit 
													 );	
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,
	    	" where orderId = #{orderId} order by date desc"})
	public List<Repertory> selectByOrderId(int orderId);
	
	@Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,")",
			" values(#{sortstationId},#{orderId},#{status},#{date},#{nextStationCode},#{userId})"	
	})
	public int addRepertory(Repertory repertory);
	
	@Update({"update ",TABLE_NAME," set status = #{status} where id=#{id}"})
	public void updateStatus(@Param("status") int status,@Param("id") int id);
}
