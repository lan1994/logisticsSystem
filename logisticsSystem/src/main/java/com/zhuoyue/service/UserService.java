package com.zhuoyue.service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuoyue.dao.CommonlyAddressDAO;
import com.zhuoyue.dao.LoginTicketDAO;
import com.zhuoyue.dao.UserDAO;
import com.zhuoyue.dao.UserMessageDAO;
import com.zhuoyue.model.CommonlyAddress;
import com.zhuoyue.model.HostHolder;
import com.zhuoyue.model.LoginTicket;
import com.zhuoyue.model.User;
import com.zhuoyue.model.UserMessage;
import com.zhuoyue.util.LogisticsSystemUtil;
/*
 * @author 兰心序
 * */
@Service
public class UserService {
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	LoginTicketDAO loginTicketDAO;
	
	@Autowired
	UserMessageDAO userMessageDAO;
	
	@Autowired
	CommonlyAddressDAO commonlyAddressDAO;
	
	@Autowired
	HostHolder hostHolder;
	public int selectAddressCountByUserId(int userId){
		return commonlyAddressDAO.selectAddressCountByUserId(userId);
	}
	
	public void uptateAddress(CommonlyAddress commonlyAddress){
		commonlyAddressDAO.updateAddress(commonlyAddress);
	}
	
	public int addAddress(CommonlyAddress commonlyAddress){
		return commonlyAddressDAO.addAddress(commonlyAddress);
	}
	
	public List<CommonlyAddress> selectByUserId(int userId,int offset,int limit){
		return commonlyAddressDAO.selectByUserId(userId, offset, limit);
	}
	
	public CommonlyAddress selectAddressById(int id){
		return commonlyAddressDAO.selectById(id);
	}
	
	public User selectByName(String username){
		return userDAO.selectByName(username);
	}
	
	public User selectById(int id){
		return userDAO.selectById(id);
	}
	
	public Map<String,Object> login(String username,String password){
		 Map<String, Object> map = new HashMap<String,Object>();
		if (StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }
		User user = userDAO.selectByName(username);
		if(user==null){
			map.put("msg", "用户名不存在");
			return map;
		}
		if(!user.getPassword().equals(LogisticsSystemUtil.MD5(password+user.getSalt()))){
			map.put("msg", "密码不正确");
			return map;
		}
		String ticket = addTicket(user.getId());
		map.put("ticket",ticket);
		map.put("right", user.getUright());
		return map;
	}
	
	
	public Map<String, Object> register(String username,String password){
		Map<String, Object> map = new HashMap<String,Object>();
		if (StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }
		User user = userDAO.selectByName(username);
		if(user!=null){
			map.put("msg", "用户名已存在");
			return map;
		}
		user = new User();
		user.setUsername(username);
		user.setSalt(UUID.randomUUID().toString().substring(0, 5));
		user.setPassword(LogisticsSystemUtil.MD5(password+user.getSalt()));
		userDAO.addUser(user);
		
		String ticket = addTicket(user.getId());
		map.put("ticket", ticket);
		return map;
	}
	
	
	public void updateTicketStatus(String ticket,int status){
		loginTicketDAO.updateStatus(ticket, status);
	}
	
	public UserMessage selectUserMessageByUserId(int userid){
		return userMessageDAO.selectByUserId(userid);
	}
	
	public void UpdateUserMessage(UserMessage userMessage){
		int flag = userMessageDAO.isExist(userMessage.getUserid());
		if(flag>0){
		userMessageDAO.updateUserMessage(userMessage);
		}else{
			userMessageDAO.addUserMessage(userMessage);
		}
	}
	
	public String addTicket(int userid){
		LoginTicket ticket = new LoginTicket();
		ticket.setUserId(userid);
		Date date = new Date();
		date.setTime(date.getTime()+ 1000*3600*24);
		ticket.setExpired(date);
		ticket.setStatus(0);
		ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
		loginTicketDAO.addTicket(ticket);
		return ticket.getTicket();
	}
	
	public Map<String,Object> deleteAddressById(int id){
		Map<String, Object> map = new HashMap<String, Object>();
		CommonlyAddress commonlyAddress = commonlyAddressDAO.selectById(id);
		if(commonlyAddress==null){
			map.put("msg", "不存在该地址");
			return map;
		}
		if(commonlyAddress.getUserId()!=hostHolder.get().getId()){
			map.put("msg", "您不能删除该内容");
			return map;
		}
		int del = commonlyAddressDAO.deleteById(id);
		if(del>0){
			return map;
		}
		map.put("msg", "删除失败");
		return map;
	}
}
