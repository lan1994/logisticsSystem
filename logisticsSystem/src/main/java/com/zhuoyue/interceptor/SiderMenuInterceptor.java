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
		// TODO Auto-generated method stub
		
		String menuPath=request.getRequestURI();
		String m=menuPath.substring(1,menuPath.length());
//		System.out.println(menuPath);
//		System.out.println(m);
		int parentMenu_id=menuService.getParentMenu_id(m);
		if(parentMenu_id==0){//如果请求的路径是顶部导航
			int Menu_id=menuService.getMenuId(m);
			List<MenuInfo> siderlist=menuService.getSecondMenu(Menu_id);
			modelAndView.addObject("siderlist",siderlist);
		}
		else{//如果请求的路径是侧面导航
			List<MenuInfo> siderlist=menuService.getSecondMenu(parentMenu_id);
			modelAndView.addObject("siderlist",siderlist);
		}

		
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
