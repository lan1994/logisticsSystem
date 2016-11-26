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
	  														   
	  
	  @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where parentMenu_id=0"})
	  List<MenuInfo> getTopMenu();
	
	  
	  @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where parentMenu_id=#{parentMenu_id}"})
	  List<MenuInfo> getSecondMenu(int parentMenu_id); 
	  
	  //根据路径查询菜单id
	  @Select({"select id from ",TABLE_NAME," where menuPath=#{menuPath}"})
	  int getMenuId(String menuPath);
	  
	  //根据路径查询parentMenu_id
	  @Select({"select parentMenu_id from ",TABLE_NAME," where menuPath=#{menuPath} and parentMenu_id=0"})
	  int getParentMenu_id(String menuPath);
	  
	  
}
