package com.zhuoyue.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhuoyue.model.Area;
import com.zhuoyue.model.City;
import com.zhuoyue.model.CommonlyAddress;
import com.zhuoyue.model.HostHolder;
import com.zhuoyue.model.Order;
import com.zhuoyue.model.Province;
import com.zhuoyue.model.Repertory;
import com.zhuoyue.model.ViewObject;
import com.zhuoyue.service.AreaService;
import com.zhuoyue.service.OrderService;
import com.zhuoyue.service.RepertoryService;
import com.zhuoyue.service.UserService;
import com.zhuoyue.util.LogisticsSystemUtil;
import com.zhuoyue.util.StockStatus;

@Controller
public class OrderController {
	@Autowired
	OrderService orderService;
	@Autowired
	AreaService areaService;
	@Autowired
	HostHolder hostHolder;
	@Autowired
	UserService userService;
	@Autowired
	RepertoryService repertoryService;
	// 查看订单详情
	@RequestMapping("/admin/orders/showorder/{orderNumber}")
	public String showorder(Model model, @PathVariable long orderNumber) {
		Order or = orderService.getOrderByNumber(orderNumber);
		ViewObject vo = new ViewObject();
		List<ViewObject> vos = new ArrayList<ViewObject>();		
		vo.set("orderNumber", orderNumber);
		vo.set("username",userService.selectAddressById(or.getSendaddressId()).getName());
		vo.set("mobile",userService.selectAddressById(or.getSendaddressId()).getMobile() );
    	String sad=areaService.selectProvinceByCode(userService.selectAddressById(or.getReaddressId()).getProvince()).getName();
		sad=sad+areaService.selectCityByCode(userService.selectAddressById(or.getReaddressId()).getCity()).getName();
		sad=sad+areaService.selectAreaByCode(userService.selectAddressById(or.getReaddressId()).getArea()).getName();
		sad=sad+userService.selectAddressById(or.getSendaddressId()).getDetailAddress();
		vo.set("sendaddress",sad);	
		
    	String redaddress=areaService.selectProvinceByCode(userService.selectAddressById(or.getReaddressId()).getProvince()).getName();
    	redaddress=redaddress+areaService.selectCityByCode(userService.selectAddressById(or.getReaddressId()).getCity()).getName();
    	redaddress=redaddress+areaService.selectAreaByCode(userService.selectAddressById(or.getReaddressId()).getArea()).getName();
    	redaddress=redaddress+userService.selectAddressById(or.getReaddressId()).getDetailAddress();
    	vo.set("readdress",redaddress);
    	vo.set("receivename",userService.selectAddressById(or.getReaddressId()).getName());
		vo.set("receivephone",userService.selectAddressById(or.getReaddressId()).getMobile());
		String msg = null;
		if (or.getTimeliness() == 1 && or.getState() == 0) {
            msg = "未完成";
			} 
		else if(or.getTimeliness() == 1 && or.getState() == 1) {
				msg = "已完成";
			}
		else {
			msg = "作废订单";
		}
		vo.set("orderSchedule",or.getorderSchedule());
		vo.set("msg",msg);
			
		vos.add(vo);
		model.addAttribute("vos", vos);
		return "/admin/order/showorder";
	}

	// 管理人员查看所有订单
	@RequestMapping("/admin/orders/manageorder")
	public String showorder(Model model) {
		List<Order> list = orderService.getAllOrder();
		model.addAttribute("lists", list);
		return "admin/order/manageorder";
	}

	// 按订单号查看
	@RequestMapping("admin/orders/selectorderbynumpage")
	public String showorderbynumpage() {
		return "/admin/order/selectorder";
	}
	@RequestMapping("/admin/orders/selectorderbynum")
	public String showorderbynum(Model model,
			@RequestParam("orderNumber") long orderNumber) {
		Order or = orderService.getOrderByNumber(orderNumber);
		List<Order> list = new ArrayList<Order>();
		list.add(or);
		model.addAttribute("lists", list);
		return "admin/order/manageorder";
	}


	// 删除订单 缺少提示
	@RequestMapping("/admin/orders/deletorder/{orderNumber}")
	public String deleteorder(Model model, @PathVariable long orderNumber) {
		boolean pull = orderService.deletOrder(orderNumber);
		// 执行删除操作
		if (pull == true) {

			return "redirect:/admin/orders/manageorder";
		} else {

			return "redirect:/admin/orders/showorder" + orderNumber;
		}
	}

	// 修改订单
	@RequestMapping("/admin/orders/updateorder/{orderNumber}")
	public String upadteorder(Model model, @PathVariable long orderNumber) {
		Order or = orderService.getOrderByNumber(orderNumber);
		List<Order> list = new ArrayList<Order>();
		list.add(or);
		model.addAttribute("lists", list);
		return "/admin/order/updateorder";
	}

	// 修改
	@RequestMapping("/admin/orders/updateorder")
	public String upadteorder(Model model,
			 @RequestParam("orderNumber") long orderNumber,
			 @RequestParam("name") String name,
			 @RequestParam("province") int province,
			 @RequestParam("city") int city,
			 @RequestParam("area") int area,
			 @RequestParam("detaiaddress") String detaiaddress,
			 @RequestParam("mobile") String mobile) {
		int userid = orderService.getOrderByNumber(orderNumber).getUserId();
		int sendaddressid= orderService.getOrderByNumber(orderNumber).getSendaddressId();
		int id=orderService.getOrderByNumber(orderNumber).getReaddressId();
		CommonlyAddress commonlyAddress = new CommonlyAddress(id, name, userid, mobile, province, city, area, detaiaddress);
		userService.updateCommonlyAddress(commonlyAddress);
		Order order = new Order();
		order.setOrderNumber(Order.getRandomOrderNumber());
		order.setUserId(userid);
		order.setSendaddressId(sendaddressid);
		order.setReaddressId(id);
		order.setorderSchedule(new Date());
		boolean pu = orderService.updateOrder(orderNumber, order);
		if (pu == true)
			return "redirect:/admin/orders/manageorder";
		else
			return "redirect:/admin/orders/manageorder"+orderNumber;

	}
	
	
	@RequestMapping("/customer/host/insertorderpage")
	public String insertorderpage(Model model) {
		int count = userService.selectAddressCountByUserId(hostHolder.get().getId());
		if(count<1){
			model.addAttribute("display","none");
			return "customer/order/insertorder";
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
		model.addAttribute("display","block");
		return "customer/order/insertorder";
		
	}
	@RequestMapping("/customer/host/insertorder")
	public String insertorder(Model model,
			@RequestParam("sendcommonlyaddressid") int sendcommonlyaddressid,
			@RequestParam("receivecommonlyaddressid") int receivecommonlyaddressid){
				Order order=new Order();
				order.setOrderNumber(Order.getRandomOrderNumber());
				order.setorderSchedule(new Date());
				order.setSendaddressId(sendcommonlyaddressid);
				order.setReaddressId(receivecommonlyaddressid);;
				order.setUserId(hostHolder.get().getId());
				boolean pull=orderService.getNeworder(order);
				if(pull==true){
					return "redirect:/customer/host/myorderspage";
				}
				else{
					model.addAttribute("msg", "提交订单失败!");
					return "redirect:/customer/host/insertorderpage";
				}
	}
	
	//ruan
	@RequestMapping("/customer/host/myorderspage")
	public String myorderspage(Model model) {
        int userid = hostHolder.get().getId();
       
        List<ViewObject> ls = new ArrayList<ViewObject>();	
        List<Order> orders=orderService.getOrdrById(userid,0);
        List<Order> finishorders=orderService.getOrdrById(userid,1);
        for(Order foror:finishorders){
        	orders.add(foror);
        }
        for(Order or:orders){
        	 ViewObject vo = new ViewObject();
        	vo.set("orderNumber", or.getOrderNumber());
        	vo.set("orderSchedule", or.getorderSchedule());
        	
        	String sad=areaService.selectProvinceByCode(userService.selectAddressById(or.getReaddressId()).getProvince()).getName();
    		sad=sad+areaService.selectCityByCode(userService.selectAddressById(or.getReaddressId()).getCity()).getName();
    		sad=sad+areaService.selectAreaByCode(userService.selectAddressById(or.getReaddressId()).getArea()).getName();
    		sad=sad+userService.selectAddressById(or.getSendaddressId()).getDetailAddress();
    		vo.set("sendaddress",sad);	
    		
        	String redaddress=areaService.selectProvinceByCode(userService.selectAddressById(or.getReaddressId()).getProvince()).getName();
        	redaddress=redaddress+areaService.selectCityByCode(userService.selectAddressById(or.getReaddressId()).getCity()).getName();
        	redaddress=redaddress+areaService.selectAreaByCode(userService.selectAddressById(or.getReaddressId()).getArea()).getName();
        	redaddress=redaddress+userService.selectAddressById(or.getReaddressId()).getDetailAddress();
        	vo.set("readdress",redaddress);
        	vo.set("rename",userService.selectAddressById(or.getReaddressId()).getName());
        	if(or.getState()==0){
        		vo.set("state","未完成" );
        		vo.set("display","block");
            	vo.set("operation","确认收货" );
        	}
        	else{
        		vo.set("state","已完成" );
        		vo.set("display","none");
            	vo.set("operation","已收货" );
        	}
        
        	
        	ls.add(vo);
        }
		model.addAttribute("lists", ls);
		return "/customer/order/myorders";
	}

	
	// 当订单完成后修改
	@RequestMapping("/customer/host/finishorder/{orderNumber}")
	public String finishorder(Model model, @PathVariable long orderNumber) {

		boolean pull = orderService.finishOrder(orderNumber);

		if (pull == true) {
			Repertory repertory = repertoryService.selectByOrderId(orderNumber).get(0);
			if(repertory!=null)
			repertoryService.updateStatus(StockStatus.ARRIVAL.getValue(), repertory.getId());
			return "redirect:/customer/host/myorderspage";
		} else {

			return "redirect:/customer/host/myorderspage";
		}
	}
	
}
