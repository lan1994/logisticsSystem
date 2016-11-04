package com.zhuoyue.interceptor;

import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.zhuoyue.dao.LoginTicketDAO;
import com.zhuoyue.model.HostHolder;
import com.zhuoyue.model.LoginTicket;
import com.zhuoyue.model.User;
import com.zhuoyue.service.UserService;
/*
 * @author 兰心序
 * */
@Component
public class PassportInterceptor implements HandlerInterceptor {

	@Autowired
	HostHolder hostHolder;
	@Autowired
	UserService userService;
	@Autowired
	LoginTicketDAO loginTicketDAO;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (request.getCookies().length == 0) {
			return true;
		}
		String ticket = null;
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals("t")) {
				ticket = cookie.getValue();
				break;
			}
		}
		if (ticket != null) {
			LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
			if (loginTicket == null || loginTicket.getStatus() == 1
					|| loginTicket.getExpired().before(new Date())) {
				return true;
			}
			User user = userService.selectById(loginTicket.getUserId());
			hostHolder.setUser(user);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (hostHolder.get() != null && modelAndView != null) {
			modelAndView.addObject("user", hostHolder.get());
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		hostHolder.clear();
	}

}
