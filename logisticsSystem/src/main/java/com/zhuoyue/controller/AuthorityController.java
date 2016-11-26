package com.zhuoyue.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhuoyue.model.Area;
import com.zhuoyue.model.City;
import com.zhuoyue.model.Province;
import com.zhuoyue.model.ViewAdminInfo;
import com.zhuoyue.service.AreaService;
import com.zhuoyue.service.UserService;

/*
 * @author 吴兵兵
 * 
 */
@Controller
public class AuthorityController {
	private static final Logger logger = LoggerFactory
			.getLogger(AuthorityController.class);
	@Autowired
	UserService userService;
	@Autowired
	AreaService areaService;
	@RequestMapping("/admin/authority/index")
	public String adminauthority(Model model){
		return "/admin/authority/index";
	}
	@RequestMapping("admin/getadmininfo")
	public String getAdminInfo(Model model){
		//获取菜单的业务逻辑，放到model中
		List<ViewAdminInfo> adminlist = userService.getAdminAll();	
		if(adminlist.size()==0){
		System.out.println("暂时还没有管理员，请先注册！");
		model.addAttribute("info", "暂时还没有管理员，请先注册！");
		}
		else{
		model.addAttribute("adminlist", adminlist);
		}
		return "/admin/authority/modifyauthority";
	}
	@RequestMapping(path = { "admin/regadmin" })
	public String regadmin(Model model,@RequestParam("uright")int uright,@RequestParam("adminname") String username,
			@RequestParam("adminpassword") String password,
			HttpServletResponse response) {
		try {
			//System.out.println(uright);
			Map<String, Object> map = userService.regAdmin(uright, username, password);
			if (map.containsKey("success")) {
				//System.out.println(map.get("success").toString());
				model.addAttribute("msg", map.get("success").toString());
				return "/admin/authority/modifyauthority";
			} else {
				model.addAttribute("msg", map.get("msg").toString());
				return "/admin/authority/modifyauthority";
			}
		} catch (Exception e) {
			logger.error("注册异常" + e.getMessage());
			model.addAttribute("msg", "服务器异常");
			return "/admin/authority/modifyauthority";
		}
	}
	
	//修改管理员权限
	@RequestMapping("modifyauthority")
	public String ModifyAuthority(){
		return "";
	}
}
