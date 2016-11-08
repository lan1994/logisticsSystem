package com.zhuoyue.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhuoyue.model.MenuInfo;
import com.zhuoyue.service.MenuService;

@Controller
public class CustomerController {
	@Autowired
	MenuService menuService;
	
	//客户管理首页
    @RequestMapping(path={"/admin/customer","customer/index"})
    public String  index(Model model){
    	String view=initMenu(model,"admin/customer/index");
    	return view;
    }
    
    //初始化菜单
  	public String initMenu(Model model,String pagepath){
  	    //顶部菜单
    	List<MenuInfo> menulist=menuService.getTopMenu();
    	//二级目录    根据对应的parentMenu_id显示不同的右侧菜单栏   
	    List<MenuInfo> siderlist=menuService.getSecondMenu(2);
	    model.addAttribute("menulist",menulist);
	    model.addAttribute("siderlist",siderlist);
	    return pagepath;    
  	}
      
}