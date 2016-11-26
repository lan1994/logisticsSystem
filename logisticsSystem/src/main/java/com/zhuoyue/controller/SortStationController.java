package com.zhuoyue.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zhuoyue.model.Province;
import com.zhuoyue.model.SortStation;
import com.zhuoyue.model.UserMessage;
import com.zhuoyue.service.AreaService;
import com.zhuoyue.service.SortStationService;
import com.zhuoyue.service.UserService;

/*
 * @author 吴兵兵
 * 
 */
@Controller
public class SortStationController {
	private static final Logger logger = LoggerFactory
			.getLogger(SortStationController.class);
	@Autowired
	AreaService areaService;
	@Autowired
	UserService userService;
	@Autowired
	SortStationService sortstationService;
	//初始化新增二级分拣中心页面
	@RequestMapping("/admin/initaddtationview/{rank}")
	public String initStationView(Model model,@PathVariable("rank")int rank){
		List<Province> provincelist = areaService.selectAllProvince();
		String url = "";
		if(rank==2){
		 model.addAttribute("provincelist", provincelist);	
		 url= "/admin/managerstation/addstation";
		}
		else if(rank==3){
	     model.addAttribute("provincelist", provincelist);	
		 url = "/admin/managerstation/addareastation";	
		}
		return url;
	}
		
	//二、三级分拣中心信息数据表单提交
	@RequestMapping("/admin/addstation")
	public String AddStation(RedirectAttributes model,SortStation station){  
		//System.out.println("执行addstation");
		if(station.getStationName().equals("")){
			model.addFlashAttribute("msg", "分拣中心名称不能为空！");
			return "redirect:/admin/initaddtationview/"+station.getRank()+"?id=4";
		}
		else if(station.getManager().equals("")){
			model.addFlashAttribute("msg", "分拣中心负责人不能为空！");
			return "redirect:/admin/initaddtationview/"+station.getRank()+"?id=4";
		}
		else if(station.getMobile().equals("")){
			model.addFlashAttribute("msg", "手机号码不能为空！");
			return "redirect:/admin/initaddtationview/"+station.getRank()+"?id=4";
		}
		else if(station.getAddress().equals("")){
			model.addFlashAttribute("msg", "分拣站地址不能为空！");
			return "redirect:/admin/initaddtationview/"+station.getRank()+"?id=4";
		}
	else{
	   if(sortstationService.AddSortStation(station)){
				System.out.println(station.getStationId()+station.getAdminId());
				if(station.getAdminId()==0){
				  model.addFlashAttribute("msg","分拣站建设成功！");
				  return "redirect:/admin/managerstation/"+station.getRank()+"?id=4";
				}
				else{
				UserMessage userMessage = userService.selectByUserId(station.getAdminId());
				userMessage.setSorttationid(station.getId());
				userService.UpdateUserMessage(userMessage);   //修改usermessage表中的stationid字段
				model.addFlashAttribute("msg","分拣站建设成功！");
				return "redirect:/admin/managerstation/"+station.getRank()+"?id=4";
				}
				}
	   else{
		        System.out.println("分拣站已经存在，请重新设立！");
			    model.addFlashAttribute("msg","分拣站创建失败(可能已经存在)！");
			    return "redirect:/admin/initaddtationview/"+station.getRank()+"?id=4";
				}
		}
	}
	//管理（查询）二级分拣中心
	@RequestMapping("/admin/managerstation/{rank}")
	public String ManagerStation(Model model,@PathVariable("rank")int rank){
		List<SortStation> stationlist=sortstationService.selectStationinfo(rank);
		List<UserMessage> userlist = new ArrayList<UserMessage>();
		for(int i=0;i<stationlist.size();i++){
			//System.out.println(stationlist.get(i).getAdminId());
			int id = stationlist.get(i).getAdminId();
			if(id==0){
				model.addAttribute("tip", "未设置");
			}
			else{
			UserMessage usermessage = userService.selectByUserId(id);
			userlist.add(usermessage);
			}
		}
		model.addAttribute("userlist", userlist);
	    model.addAttribute("stationlist", stationlist);
		return "/admin/managerstation/managerstation";
	}
	//管理（更新）二级分拣中心
	@RequestMapping("/admin/updatestation")
	public String UpdateStation(Model model,@RequestParam("sid")int id){
		//System.out.println(stationId);
		SortStation station = sortstationService.selectStationById(id);
		List<UserMessage> userlist = userService.getAdminInfo(station.getRank(), station.getCity());
		UserMessage admin = userService.selectUserMessageByUserId(station.getAdminId());
		//System.out.println(user.getName());
		model.addAttribute("admin", admin);
		model.addAttribute("userlist", userlist);
		model.addAttribute("station", station);
		return "/admin/managerstation/updatestation";
	}
	//保存修改内容
	@RequestMapping("/admin/saveupdatestation")
	public String SaveUpdateStation(RedirectAttributes model,SortStation station){
		System.out.println(station.getAdminId()+station.getStationName()+station.getStationId());		
			if(station.getAdminId()==0){
			  System.out.println(station.getStationName());
			  sortstationService.updateStation(station);
			  model.addFlashAttribute("msg", "分拣站更新成功！");
			  return "redirect:/admin/managerstation?rank="+station.getRank()+"?id=4";
			}
			else{
			  System.out.println(station.getAdminId());
			  UserMessage user = userService.selectByUserId(station.getAdminId());
			  sortstationService.updateStation(station);
			  user.setSorttationid(station.getId());
			  userService.UpdateUserMessage(user);
			  model.addFlashAttribute("msg", "分拣站更新成功！");
			  return "redirect:/admin/managerstation/"+station.getRank()+"?id=4";
		}
	}
	//管理（删除）分拣中心
	@RequestMapping("/admin/delstation")
	public String DeleteStation(RedirectAttributes model,@RequestParam("sid")int id){
		System.out.println("删除操作");
		SortStation sortStation = sortstationService.selectStationById(id);
		if(sortStation.getAdminId()==0){
			System.out.println("直接删除");
			//如果未设置管理员，将其直接删除
			sortstationService.delStation(id);
		}
		else{
			System.out.println("需要修改usermessage表中的stationid,将其修改为0");
			//如果已经设置了管理员，则需要修改usermessage表中的stationid,将其修改为0
			sortstationService.delStation(id);
			UserMessage userMessage = userService.selectByUserId(sortStation.getAdminId());
			userMessage.setSorttationid(0);
			userService.UpdateUserMessage(userMessage);
		}
		model.addFlashAttribute("msg", "二级分拣站删除成功！");
		return "redirect:/admin/managerstation/"+sortStation.getRank()+"?id=4";
	}	
}
