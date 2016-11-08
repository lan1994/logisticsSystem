package com.zhuoyue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhuoyue.model.MenuInfo;
import com.zhuoyue.service.MenuService;
/*
 * @author 兰心序
 * */
@Controller
public class HomeController {
	@Autowired
	MenuService menuService;
	
	@RequestMapping(path={"/admin/home","/admin/","admin/index"})
	public String adminHome(Model model){
		//获取菜单的业务逻辑，放到model中
		String view=initMenu(model,"/admin/index");
//		return "/admin/index";
		return view;
	}
	
	@RequestMapping(path={"/","/customer/home"})
	public String userHome(){
		return "/customer/index";
	}
	
	//初始化菜单
  	public String initMenu(Model model,String pagepath){
  	    //顶部菜单
    	List<MenuInfo> menulist=menuService.getTopMenu();
    	//二级目录    根据对应的parentMenu_id显示不同的右侧菜单栏   
	    List<MenuInfo> siderlist=menuService.getSecondMenu(1);
	    model.addAttribute("menulist",menulist);
	    model.addAttribute("siderlist",siderlist);
	    return pagepath;
  	}
	
}
