package com.zhuoyue.controller;
import java.util.ArrayList;
import java.util.Date;
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

import com.zhuoyue.dao.OrderDAO;
import com.zhuoyue.dao.SortStationDAO;
import com.zhuoyue.model.Area;
import com.zhuoyue.model.City;
import com.zhuoyue.model.CommonlyAddress;
import com.zhuoyue.model.HostHolder;
import com.zhuoyue.model.Order;
import com.zhuoyue.model.Repertory;
import com.zhuoyue.model.SortStation;
import com.zhuoyue.model.User;
import com.zhuoyue.model.ViewObject;
import com.zhuoyue.service.AreaService;
import com.zhuoyue.service.RepertoryService;
import com.zhuoyue.service.UserService;
import com.zhuoyue.util.JsonUtil;
import com.zhuoyue.util.StockStatus;
/*
 * @author 兰心序
 * */
@Controller
public class StockController {
	private static final Logger logger = LoggerFactory
			.getLogger(StockController.class);
	@Autowired
	RepertoryService repertoryService;
	@Autowired
	HostHolder hostHolder;
	@Autowired
	UserService userService;
	@Autowired
	OrderDAO orderDAO;
	@Autowired
	SortStationDAO sortStationDAO;
	@Autowired
	AreaService areaService;
	//在当前库中的全部订单
	@RequestMapping("/admin/stockpage")
	public String stockpage(Model model,@RequestParam(value="curpage",required=false,defaultValue="1") int curpage,
										  @RequestParam(value="limit",required=false,defaultValue="10") int limit
											){
		int stationId = userService.selectUserMessageByUserId(hostHolder.get().getId()).getSorttationid();
		int count = repertoryService.selectCountByStation(stationId,  StockStatus.INSTOCK.getValue());
		model.addAttribute("count", count);
		if(count==0){
			 return "/admin/stock/stock";
		}
		int offset = (curpage-1)*limit;
		List<Repertory> list = repertoryService.selectByStation(stationId, StockStatus.INSTOCK.getValue(), 
				offset, limit);
		List<ViewObject> vos = new ArrayList();
		for(Repertory repertory : list){
			Order order = orderDAO.getByNumber(repertory.getOrderId());
			ViewObject vo = new ViewObject();
			vo.set("id", repertory.getId());
			vo.set("ordernumber",order.getOrderNumber());
			
			StockStatus status = StockStatus.valueof(repertory.getStatus());
			vo.set("status",status.getValueString());
			
			User u  = userService.selectById(order.getUserId());
			vo.set("username",u.getUsername());
			
			CommonlyAddress from = userService.selectAddressById(order.getSendaddressId());
			CommonlyAddress to = userService.selectAddressById(order.getReaddressId());
			vo.set("fromMobile", from.getMobile());
			vo.set("toMobile", to.getMobile());
			
			StringBuilder sb = new StringBuilder();
			sb.append(areaService.selectProvinceByCode(from.getProvince()).getName());
			sb.append(areaService.selectCityByCode(from.getCity()).getName());
			sb.append(areaService.selectAreaByCode(from.getArea()).getName());
			sb.append(from.getDetailAddress());
			vo.set("from", sb.toString());
			
			sb = new StringBuilder();
			sb.append(areaService.selectProvinceByCode(to.getProvince()).getName());
			sb.append(areaService.selectCityByCode(to.getCity()).getName());
			sb.append(areaService.selectAreaByCode(to.getArea()).getName());
			sb.append(to.getDetailAddress());
			vo.set("to", sb.toString());
			if(repertory.getNextStationCode()==0){
				vo.set("next","配送给客户");
			}else
			{
			SortStation sortStation = sortStationDAO.selectById(repertory.getNextStationCode());
				if(sortStation!=null)
				{
					vo.set("next", sortStation.getStationName());
				}
			}
			vos.add(vo);
		}
		model.addAttribute("vos", vos);
		return "/admin/stock/stock";
	}
	/*
	@RequestMapping("/admin/outstock")
	@ResponseBody
	public String outstock(){
		
	}*/
	@RequestMapping("/admin/outstock")
	@ResponseBody
	public String outstock(@RequestParam("repertoryid") int repertoryid){
		int code = repertoryService.outstock(repertoryid);
		return JsonUtil.getJSONString(code,String.valueOf(repertoryid));
	}
	
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
	public String intock(@RequestParam("ordernumber") long ordernumber){
		Order order = orderDAO.getByNumber(ordernumber);
		if(order==null){
			return JsonUtil.getJSONString(0,"该订单不存在");
		}
		int adminId = hostHolder.get().getId();
		int stationId = userService.selectUserMessageByUserId(adminId).getSorttationid();
		Repertory repertory =  repertoryService.selectByIdAndStation(stationId, ordernumber);
		if(repertory!=null&&repertory.getStatus()==StockStatus.INSTOCK.getValue()){
			return JsonUtil.getJSONString(0,"不能重复入库");
		}
		//当前分拣站
		SortStation sortStation = sortStationDAO.selectById(stationId);
		//当前分拣站地区码
		int sortstationAreaCode = sortStation.getArea();
		CommonlyAddress commonlyAddress = userService.selectAddressById(order.getReaddressId());
		//目的地地区码 
		int destnyareacode = commonlyAddress.getArea();
		
		repertory = new Repertory();
		if(destnyareacode==sortstationAreaCode)
		{//在3级分拣站，且下一站是目的地
			repertory.setNextStationCode(0);
		}else
		{	//当前是3级分拣站
			if(sortStation.getRank()==3)
			{
				Area area = areaService.selectAreaByCode(sortstationAreaCode); 
				int citycode = area.getCitycode();
				City city = areaService.selectCityByCode(citycode);
				SortStation next = sortStationDAO.selectByRankAndCity(2, city.getCode());
				repertory.setNextStationCode(next.getId());
			}
			else
			{//当前是2级分拣站
				//这个if代表是送货下发的流程，不是揽货上交的流程
				if(sortStation.getCity()==commonlyAddress.getCity())
				{	//stationId字段
					SortStation next = sortStationDAO.selectByRankAndArea(3, commonlyAddress.getArea());
					repertory.setNextStationCode(next.getId());
				}else
				{
					SortStation next = sortStationDAO.selectByRankAndCity(2, commonlyAddress.getCity());
					repertory.setNextStationCode(next.getId());
				}
			}
		}
		repertory.setDate(new Date());
		repertory.setOrderId(ordernumber);
		repertory.setUserId(adminId);
		repertory.setSortstationId(stationId);
		repertory.setStatus(StockStatus.INSTOCK.getValue());
		int newid = repertoryService.instock(repertory);
		if(newid<=0){
			return JsonUtil.getJSONString(0,"入库失败");
		}
		Map<String,Object> map = new HashMap();
		map.put("msg", "入库成功");
		map.put("newid", newid);
		map.put("ordernumber", order.getOrderNumber());
		map.put("customername", userService.selectById(order.getUserId()).getUsername());
	/*	String from = areaService.selectAreaById(userService.selectAddressById(order.getSendaddressId()).getArea()).getName();
		String to = areaService.selectAreaById(userService.selectAddressById(order.getReaddressId()).getArea()).getName();
		System.out.println(from);
		map.put("from", from);
		map.put("to", to);*/
		return JsonUtil.getJSONString(1, map);
	}
	
	@RequestMapping("/admin/querybyorder")
	@ResponseBody
	public String querybyorder(@RequestParam("ordernumber") long ordernumber){
		int stationId = userService.selectUserMessageByUserId(hostHolder.get().getId()).getSorttationid();
		Repertory repertory = repertoryService.selectByIdAndStation(stationId, ordernumber);
		if(repertory==null){
			return JsonUtil.getJSONString(0,String.valueOf(ordernumber));
		}
		Order order = orderDAO.getByNumber(repertory.getOrderId());
		ViewObject vo = new ViewObject();
		vo.set("id", repertory.getId());
		vo.set("ordernumber",order.getOrderNumber());
		
		StockStatus status = StockStatus.valueof(repertory.getStatus());
		vo.set("status",status.getValueString());
		
		User u  = userService.selectById(order.getUserId());
		vo.set("username",u.getUsername());
		
		CommonlyAddress from = userService.selectAddressById(order.getSendaddressId());
		CommonlyAddress to = userService.selectAddressById(order.getReaddressId());
		vo.set("fromMobile", from.getMobile());
		vo.set("toMobile", to.getMobile());
		
		StringBuilder sb = new StringBuilder();
		sb.append(areaService.selectProvinceByCode(from.getProvince()).getName());
		sb.append(areaService.selectCityByCode(from.getCity()).getName());
		sb.append(areaService.selectAreaByCode(from.getArea()).getName());
		sb.append(from.getDetailAddress());
		vo.set("from", sb.toString());
		
		sb = new StringBuilder();
		sb.append(areaService.selectProvinceByCode(to.getProvince()).getName());
		sb.append(areaService.selectCityByCode(to.getCity()).getName());
		sb.append(areaService.selectAreaByCode(to.getArea()).getName());
		sb.append(to.getDetailAddress());
		vo.set("to", sb.toString());
		if(repertory.getNextStationCode()==0){
			vo.set("next","配送给客户");
		}else
		{
		SortStation sortStation = sortStationDAO.selectById(repertory.getNextStationCode());
			if(sortStation!=null)
			{
				vo.set("next", sortStation.getStationName());
			}
		}
		return JsonUtil.getJSONString(1, vo.getVOMap());
	}
}
