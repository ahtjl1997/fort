package com.fort.webapp.controller.home;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fort.module.employee.Employee;

@Controller
public class HomeController {
	
	@RequestMapping("/home")
	public String home(ModelMap model) {
		Employee employee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.put("ONLINE_USER", employee);
		return "pages/layout/main";
	}
}
