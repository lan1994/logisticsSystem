package com.zhuoyue.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhuoyue.model.HostHolder;
import com.zhuoyue.model.Repertory;
import com.zhuoyue.service.RepertoryService;
import com.zhuoyue.service.UserService;
import com.zhuoyue.util.StockStatus;

/*
 * @author 兰心序
 * */
@Controller
public class StockController {
	@Autowired
	RepertoryService repertoryService;
	@Autowired
	HostHolder hostHolder;
	@Autowired
	UserService userService;
	
	//在当前库中的全部订单
	@RequestMapping("/admin/stockpage")
	public String instockpage(Model model){
		int areaId = userService.selectUserMessageByUserId(hostHolder.get().getId()).getArea();
		List<Repertory> list = repertoryService.selectByStation(areaId, StockStatus.INSTOCK.getValue(), 
				0, 10);
		model.addAttribute("lists", list);
		return "admin/stock/instock";
	}
	
	@RequestMapping("/admin/outstockpage")
	public String outstockpage(Model model) {
		
		return "/admin/stock/outstock";
	}
}
