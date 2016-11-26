package com.zhuoyue.dao;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zhuoyue.model.UserMessage;
import com.zhuoyue.model.ViewAdminInfo;
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
	
	@Select({"SELECT * FROM usermessage,`user` WHERE `user`.id = usermessage.user_id AND usermessage.sorttationid=0 AND `user`.uright=#{0} AND usermessage.city=#{1}"})
	List<UserMessage> getAdminInfo(int uright,int cityCode);
	
	@Select({"select count(*) from ",TABLE_NAME," where user_id=#{userid}"})
	int isExist(int userId);
	
	@Update({"update ",TABLE_NAME," set name = #{name},mobile = #{mobile},sex = #{sex},"
			+ "province = #{province},city = #{city},area = #{area},sorttationid = #{sorttationid} where user_id=#{userid}"})
	void updateUserMessage(UserMessage userMessage);
	
	
	@Delete({"delete from ",TABLE_NAME,"where id=#{id}"})
	void deleteById(int id);
	
	//获取管理员信息
	@Select({"SELECT usermessage.*,`user`.*,province.`name` AS provincename,province.*,city.`name` AS cityname,city.*,area.`name` AS areaname,area.* "
			+ "FROM usermessage,`user`,province,city,area WHERE `user`.id = usermessage.user_id AND usermessage.sorttationid=0 AND "
			+ "usermessage.province=province.`code` AND usermessage.city=city.`code` AND usermessage.area=area.`code` AND `user`.uright in (2,3)"})
	public List<ViewAdminInfo> getAdminAll();
	
	
	
	
}
