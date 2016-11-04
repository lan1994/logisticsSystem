package com.zhuoyue.controller;

import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhuoyue.service.UserService;
/*
 * @author 兰心序
 * */
@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory
			.getLogger(LoginController.class);

	@Autowired
	UserService userService;

	@RequestMapping(path = { "/loginpage" })
	public String loginpage() {
		return "login";
	}

	@RequestMapping("/registpage")
	public String registpage() {
		return "register";
	}

	@RequestMapping(path = { "/login" })
	public String login(
			Model model,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam(value = "next", required = false) String next,
			@RequestParam(value = "remember", required = false) boolean remember,
			HttpServletResponse response) {
		try {
			Map<String, Object> map = userService.login(username, password);
			if (!map.containsKey("ticket")) {
				model.addAttribute("msg", map.get("msg"));
				return "login";
			} else {
				Cookie cookie = new Cookie("t", map.get("ticket").toString());
				cookie.setPath("/");
				if (remember) {
					cookie.setMaxAge(24 * 3600 * 5);
				}
				response.addCookie(cookie);
				if (map.get("right").equals(0))
					return next == null || next.equals("") ? "redirect:/customer/home"
							: "redirect:" + next;
				else {
					return "redirect:/admin/home";
				}
			}
		} catch (Exception e) {
			logger.error("登陆异常" + e.getMessage());
			model.addAttribute("msg", "服务器错误");
			return "login";
		}
	}

	@RequestMapping(path = { "/reg" })
	public String reg(Model model, @RequestParam("username") String username,
			@RequestParam("password") String password,
			HttpServletResponse response) {
		try {
			Map<String, Object> map = userService.register(username, password);
			if (map.containsKey("ticket")) {
				Cookie cookie = new Cookie("t", map.get("ticket").toString());
				cookie.setPath("/");
				response.addCookie(cookie);
				return "/customer/index";
			} else {
				model.addAttribute("msg", map.get("msg").toString());
				return "register";
			}
		} catch (Exception e) {
			logger.error("注册异常" + e.getMessage());
			model.addAttribute("msg", "服务器异常");
			return "register";
		}

	}

	@RequestMapping(path = { "/logout" })
	public String logout(@CookieValue("t") String ticket) {
		userService.updateTicketStatus(ticket, 1);
		return "redirect:/loginpage";
	}

}
