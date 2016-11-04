package com.zhuoyue.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
/*
 * @author 兰心序
 * */
@Controller
public class HomeController {
	@RequestMapping("/admin/home")
	public String adminHome(Model model){
		//获取菜单的业务逻辑，放到model中
		return "/admin/index";
	}
	@RequestMapping(path={"/","/customer/home"})
	public String userHome(){
		return "/customer/index";
	}
	
	
	
}
