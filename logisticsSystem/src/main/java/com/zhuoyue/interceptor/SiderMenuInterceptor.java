package com.zhuoyue.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zhuoyue.model.MenuInfo;
import com.zhuoyue.service.MenuService;

@Component
public class SiderMenuInterceptor implements HandlerInterceptor {
	@Autowired
	MenuService menuService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String id = request.getParameter("id");
		int pid = 1;
		if(id!=null){
			pid = Integer.parseInt(id);
		}
		List<MenuInfo> list = menuService.getSecondMenu(pid);
		modelAndView.addObject("siderlist",list);
		modelAndView.addObject("pid", pid);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
