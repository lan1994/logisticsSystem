package com.zhuoyue.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.zhuoyue.model.HostHolder;
/*
 * @author 兰心序
 * */
@Component
public class AdminInterceptor implements HandlerInterceptor{

	@Autowired
	HostHolder hostHolder;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if(hostHolder.get()!=null&&hostHolder.get().getUright()==0){
			request.setAttribute("msg", "权限不足");
			request.getRequestDispatcher("/loginpage?next="+request.getRequestURI()).forward(request, response);
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
	
	
}
