package com.zhuoyue.dao;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zhuoyue.model.CommonlyAddress;
/*
 * @author 兰心序
 * */
@Mapper
public interface CommonlyAddressDAO {
	String TABLE_NAME = " commonlyaddress";
	String INSERT_FIELDS =" name, user_id, mobile, province,city,area,detailaddress";
	String SELECT_FIELDS = " id,"+INSERT_FIELDS;
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,
		    " where user_id = #{userId} order by id desc limit #{offset},#{limit}"})
	public List<CommonlyAddress> selectByUserId(@Param("userId") int userId,
													  @Param("offset") int offset,
													  @Param("limit")  int limit 
													 );	
	@Select({"select","count(*) from ",TABLE_NAME," where user_id = #{userId}"})
	public int selectAddressCountByUserId(int userId);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,
	    	" where id = #{id}"})
	public CommonlyAddress selectById(int id);
	
	@Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,")",
			" values(#{name},#{userId},#{mobile},#{province},#{city},#{area},#{detailAddress})"	
	})
	public int addAddress(CommonlyAddress commonlyAddress);
	
	@Update({"update ",TABLE_NAME," set name=#{name},mobile=#{mobile},province=#{province},"
			+ "city=#{city},area=#{area},detailaddress=#{detailAddress}"})
	public void updateAddress(CommonlyAddress commonlyAddress);
	
	
	
	//ruan 管理员后台修改订单中对应的常用收件人信息
	@Update({"update ",TABLE_NAME," set name=#{name},mobile=#{mobile},province=#{province},"
			+ "city=#{city},area=#{area},detailaddress=#{detailAddress}"+"where id=#{id}"})
	public void updateCommonlyAddress(CommonlyAddress commonlyAddress);
      
	
	@Delete({"delete from ",TABLE_NAME," where id=#{id}"})
	public int deleteById(int id);
}
