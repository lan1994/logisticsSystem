package com.zhuoyue.service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuoyue.dao.LoginTicketDAO;
import com.zhuoyue.dao.UserDAO;
import com.zhuoyue.dao.UserMessageDAO;
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
		userMessageDAO.updateUserMessage(userMessage);
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
}
