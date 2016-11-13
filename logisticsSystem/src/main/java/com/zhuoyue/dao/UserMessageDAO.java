package com.zhuoyue.dao;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zhuoyue.model.User;
import com.zhuoyue.model.UserMessage;
/*
 * @author 兰心序
 * */
@Mapper
public interface UserMessageDAO {
	String TABLE_NAME = " usermessage";
	String INSERT_FIELDS =" user_id,name,mobile,sex,province,city,area,sorttationid";
	String SELECT_FIELDS = " id,"+INSERT_FIELDS;
	
	@Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,")"
		,"values (#{userid},#{name},#{mobile},#{sex},#{province},#{city},#{area},#{sorttationid})"
		})
	int addUserMessage(UserMessage userMessage);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where user_id=#{userid}"})
	UserMessage selectByUserId(int userid);
	
	@Update({"update ",TABLE_NAME," set name = #{name},mobile = #{mobile},sex = #{sex},"
			+ "province = #{province},city = #{city},area = #{area} where user_id=#{userid}"})
	void updateUserMessage(UserMessage userMessage);
	
	@Delete({"delete from ",TABLE_NAME,"where id=#{id}"})
	void deleteById(int id);
}
