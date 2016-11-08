package com.zhuoyue.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.zhuoyue.model.MenuInfo;

@Mapper
public interface MenuDAO {
	  String TABLE_NAME = "menu";
	  String SELECT_FIELDS ="id,menuContent,menuPath,menuOrder,parentMenu_id";
	
	  //检索顶部导航标题
//	  @Select("CALL getTopMenu()")
//	  @Options(statementType=StatementType.CALLABLE)
//	  public List<MenuInfo> getTopMenu();
	  
	  @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where parentMenu_id=0"})
	  List<MenuInfo> getTopMenu();
	
	  //检索侧面标题
//	  @Select("CALL getSecondMenu(#{parentMenu_id})")
//	  @Options(statementType=StatementType.CALLABLE)
//	  public List<MenuInfo> getSecondMenu(int parentMenu_id); 
	  
	  @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where parentMenu_id=#{parentMenu_id}"})
	  List<MenuInfo> getSecondMenu(int parentMenu_id); 
}
