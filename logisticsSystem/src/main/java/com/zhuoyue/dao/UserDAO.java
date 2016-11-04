package com.zhuoyue.dao;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.zhuoyue.model.User;
/*
 * @author 兰心序
 * */
@Mapper
public interface UserDAO {
	String TABLE_NAME = " user";
	String INSERT_FIELDS =" username,password,salt,uright";
	String SELECT_FIELDS = " id,"+INSERT_FIELDS;
	
	@Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,")"
		,"values (#{username},#{password},#{salt},#{uright})"
		})
	int addUser(User user);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where id=#{id}"})
	User selectById(int id);
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where username=#{username}"})
	User selectByName(String username);
	
	@Update({"update ",TABLE_NAME," set password = #{password} where id=#{id}"})
	void updatePassword(User user);
	
	@Delete({"delete from ",TABLE_NAME,"where id=#{id}"})
	void deleteById(int id);
}
