package com.zhuoyue.controller;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuoyue.model.HostHolder;
import com.zhuoyue.model.Repertory;
import com.zhuoyue.service.RepertoryService;
import com.zhuoyue.service.UserService;
import com.zhuoyue.util.JsonUtil;
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
//应该是vo，由于orderservice还没写,
		return "/admin/stock/atstock";
	}
	/*
	@RequestMapping("/admin/outstock")
	@ResponseBody
	public String outstock(){
		
	}*/
	
	@RequestMapping("/admin/outstockpage")
	public String outstockpage(Model model) {
		
		return "/admin/stock/outstock";
	}
	//入库页面
	@RequestMapping("/admin/instockpage")
	public String instockpage(){
		return "/admin/stock/instock";
	}
	//入库
	@RequestMapping("/admin/instock")
	@ResponseBody
	public String intock(@RequestParam("orderNumber") int ordernumber){
		Repertory repertory = new Repertory();
		repertory.setDate(new Date());
		repertory.setOrderNumber(ordernumber);
		repertory.setUserId(hostHolder.get().getId());
		repertory.setSortstationId(userService.selectUserMessageByUserId(
				hostHolder.get().getId()).getArea());
		repertory.setStatus(StockStatus.INSTOCK.getValue());
		int newid = repertoryService.instock(repertory);
		return newid>0?JsonUtil.getJSONString(1, String.valueOf(newid)):JsonUtil.getJSONString(0);
	}
}
