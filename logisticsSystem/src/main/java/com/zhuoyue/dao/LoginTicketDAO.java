package com.zhuoyue.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zhuoyue.model.LoginTicket;
/*
 * @author 兰心序
 * */
@Mapper
public interface LoginTicketDAO {
	String TABLE_NAME = " loginticket";
	String INSERT_FIELDS =" user_id, ticket, status, expired";
	String SELECT_FIELDS = " id,"+INSERT_FIELDS;
	
	@Insert({"insert into ",TABLE_NAME," (",INSERT_FIELDS,")"
		," values(#{userId},#{ticket},#{status},#{expired})"
	})
	int addTicket(LoginTicket ticket);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,
		"where ticket=#{ticket}"
	})
	LoginTicket selectByTicket(String ticket);
	
	@Update({"update ",SELECT_FIELDS," set status = #{status} where ticket=#{ticket}"})
	void updateStatus(@Param("ticket") String ticket,@Param("status") int status);
}
