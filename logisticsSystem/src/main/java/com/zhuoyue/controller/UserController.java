package com.zhuoyue.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuoyue.model.Area;
import com.zhuoyue.model.City;
import com.zhuoyue.model.CommonlyAddress;
import com.zhuoyue.model.HostHolder;
import com.zhuoyue.model.Province;
import com.zhuoyue.model.UserMessage;
import com.zhuoyue.model.ViewObject;
import com.zhuoyue.service.AreaService;
import com.zhuoyue.service.UserService;
import com.zhuoyue.util.JsonUtil;
import com.zhuoyue.util.LogisticsSystemUtil;

@Controller
public class UserController {
	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);
@Autowired
HostHolder hostHolder;
@Autowired
UserService userService;
@Autowired
AreaService areaService;

	@RequestMapping("/customer/host/addresspage")
	//@ResponseBody
	public String addAddresspage(Model model) {
		int count = userService.selectAddressCountByUserId(hostHolder.get().getId());
		if(count<1){
			model.addAttribute("code", 0);
			model.addAttribute("msg", "您还没有常用联系人，添加一个吧！");
			return "/customer/user/address";
		}
		List<CommonlyAddress> list = userService.selectByUserId(hostHolder
				.get().getId(), 0, LogisticsSystemUtil.DEFAULT_ADDRESS_PAGENUM);
		List<ViewObject> vos = new ArrayList<ViewObject>();
		for(CommonlyAddress commonlyAddress:list)
		{
			ViewObject vo = new ViewObject();
			vo.set("name",commonlyAddress.getName());
			vo.set("id",commonlyAddress.getId());
			Province province = areaService.selectProvinceByCode(commonlyAddress.getProvince());
			City city = areaService.selectCityByCode(commonlyAddress.getCity());
			Area area = areaService.selectAreaByCode(commonlyAddress.getArea());
			vo.set("province",province.getName());
			vo.set("city",city.getName());
			vo.set("area",area.getName());
			vo.set("detaiaddress",commonlyAddress.getDetailAddress());
			vo.set("mobile", commonlyAddress.getMobile());
			vos.add(vo);
		}
		model.addAttribute("vos", vos);
		model.addAttribute("count",count);
		model.addAttribute("code", 1);
		
		return "/customer/user/address";
	}

	@RequestMapping("/customer/host/addAddress")
	public String addAddress(Model model,@RequestParam("name") String name,
										 @RequestParam("province") int province,
										 @RequestParam("city") int city,
										 @RequestParam("area") int area,
										 @RequestParam("detaiaddress") String detaiaddress,
										 @RequestParam("mobile") String mobile){
		CommonlyAddress commonlyAddress = new CommonlyAddress(name, hostHolder.get().getId(), mobile, province, city, area, detaiaddress);
		int id = userService.addAddress(commonlyAddress);
		return "redirect:/customer/host/addresspage";
	}
	
	@RequestMapping("/customer/host/findpage")
	@ResponseBody
	public String addAddresspageBypage(Model model,@RequestParam(value="page",required = false,defaultValue = "1") int page){
		int count = userService.selectAddressCountByUserId(hostHolder.get().getId());
		List<CommonlyAddress> list = null;
		if(page*LogisticsSystemUtil.DEFAULT_ADDRESS_PAGENUM>count)
		{
			list=userService.selectByUserId(hostHolder.get().getId(), 0, LogisticsSystemUtil.DEFAULT_ADDRESS_PAGENUM);
		}
		else
		{
		int offset = (page-1)*LogisticsSystemUtil.DEFAULT_ADDRESS_PAGENUM;
		list = userService.selectByUserId(hostHolder.get().getId(), offset, LogisticsSystemUtil.DEFAULT_ADDRESS_PAGENUM);
		}
		List<Map<String,Object>> ls = new ArrayList();
		for(CommonlyAddress commonlyAddress:list){
			Map<String,Object> map = new HashMap();
			map.put("name",commonlyAddress.getName());
			map.put("username", userService.selectAddressById(hostHolder.get().getId()).getName());
			map.put("mobile", commonlyAddress.getMobile());
			map.put("area", areaService.selectAreaByCode(commonlyAddress.getArea()));
			map.put("city",areaService.selectCityByCode(commonlyAddress.getCity()));
			map.put("province", areaService.selectProvinceByCode(commonlyAddress.getProvince()));
			map.put("detaiaddress", commonlyAddress.getDetailAddress());
			ls.add(map);
		}
		return JsonUtil.getJSONString(1, ls);
	}
	
	@RequestMapping("/customer/host/usermessagepage")
	public String usermessagepage(Model model){
		UserMessage userMessage = userService.selectUserMessageByUserId(hostHolder.get().getId());
		if(userMessage==null){
			return "/customer/user/basicinfo";
		}
		ViewObject vo = new ViewObject();
		vo.set("loginname", hostHolder.get().getUsername());
		vo.set("name", userMessage.getName());
		vo.set("sex", userMessage.getSex());
		vo.set("provincecode", userMessage.getProvince());
		vo.set("citycode", userMessage.getCity());
		vo.set("areacode", userMessage.getArea());
		Province province = areaService.selectProvinceByCode(userMessage.getProvince());
		City city = areaService.selectCityByCode(userMessage.getCity());
		Area area = areaService.selectAreaByCode(userMessage.getArea());
		vo.set("provincename", province.getName());
		vo.set("cityname", city.getName());
		vo.set("areaname", area.getName());
		vo.set("mobile", userMessage.getMobile());
		model.addAttribute("vo", vo);
		return "/customer/user/basicinfo";
	}
	
	@RequestMapping("/customer/host/updateUserMessage")
	//@ResponseBody
	public String updateUserMessage(@RequestParam("area") int area,
									@RequestParam("city") int city,
									@RequestParam("province") int province,
									@RequestParam("mobile") String mobile,
									@RequestParam("name") String name,
									@RequestParam("sex") char sex
									){
		try{
		UserMessage userMessage = new UserMessage();
		userMessage.setArea(area);
		userMessage.setCity(city);
		userMessage.setProvince(province);
		userMessage.setMobile(mobile);
		userMessage.setName(name);
		userMessage.setSex(sex);
		userMessage.setUserid(hostHolder.get().getId());
		userService.UpdateUserMessage(userMessage);
		}catch(Exception e){
			logger.error("更新个人信息失败"+e.getMessage());
			//return JsonUtil.getJSONString(0);
		}
		//return JsonUtil.getJSONString(1);
		return "redirect:/customer/host/usermessagepage";
	}
	
	@RequestMapping("/customer/host/delete")
	@ResponseBody
	public String deleteAddress(@RequestParam("id") int id){
		Map<String,Object> map = userService.deleteAddressById(id);
		if(map.get("msg")!=null){
			return JsonUtil.getJSONString(0, map);
		}
		return JsonUtil.getJSONString(1, String.valueOf(id));
	}
}
