package com.fort.webapp.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fort.module.employee.Employee;
import com.fort.service.employee.LoginService;
import com.util.FortObjectUtil;
import com.util.StringUtil;
import com.util.exception.ImgCodeAuthenticationException;
import com.util.exception.PasswordAuthenticationException;
import com.util.exception.UsernameAuthenticationException;

public class FortUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private LoginService loginService;
	
	public FortUsernamePasswordAuthenticationFilter(LoginService loginService) {
		this.loginService = loginService;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		HttpSession session = request.getSession();
		String username = request.getParameter("loginName");
		String password = request.getParameter("password");
		String imgCode = request.getParameter("imgCode");
		Object randObj = session.getAttribute("rand");
		String rand = FortObjectUtil.isEmpty(randObj) ? "" : randObj.toString();
		session.setAttribute("loginName", username);
		session.setAttribute("password", password);
		if(StringUtil.isBlank(username)) {
			throw new UsernameAuthenticationException("请输入账号");
		}else if(StringUtil.isBlank(password)) {
			throw new PasswordAuthenticationException("请输入密码");
		}else if(StringUtil.isBlank(imgCode)) {
			throw new ImgCodeAuthenticationException("请输入验证码");
		}else if(!imgCode.toUpperCase().equals(rand)) {
			throw new ImgCodeAuthenticationException("验证码错误");
		}
		
		Employee employee = loginService.login(username, password);
		
		if(employee == null) {
			throw new PasswordAuthenticationException("用户名密码错误");
		}
		
		if(employee.getStatus() == 0) {
			throw new UsernameAuthenticationException("您的账号已被锁定");
		}
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, "");
		return super.getAuthenticationManager().authenticate(token);
	}
}
