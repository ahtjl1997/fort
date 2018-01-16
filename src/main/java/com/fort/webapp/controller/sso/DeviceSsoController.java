package com.fort.webapp.controller.sso;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fort.module.employee.Employee;
import com.fort.module.rule.RuleInfo;
import com.fort.service.sso.SsoService;
import com.util.page.Page;
import com.util.page.SearchResult;

@RequestMapping("/sso")
@Controller
public class DeviceSsoController {
	
	@Autowired
	private SsoService ssoService;
	
	@RequestMapping("/query")
	public String query(ModelMap model,
			@RequestParam(name="paramQuery",defaultValue="") String keyword,
			@RequestParam(name="startIndex",defaultValue="0") int startIndex,
			@RequestParam(name="status",defaultValue="-1") int status,
			@RequestParam(name="typeId",defaultValue="-1") int typeId,
			@RequestParam(name="osId",defaultValue="-1") int osId) {
		Employee employee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyword", keyword);
		map.put("empId", employee.getId());
		if(status != -1) {
			map.put("status", status);
		}
		if(typeId != -1) {
			map.put("typeId", typeId);
		}
		if(osId != -1) {
			map.put("osId", osId);
		}
		SearchResult<RuleInfo> sr = ssoService.query(map, startIndex, Page.DEFAULT_PAGE_SIZE);
		model.put("paramQuery", keyword);
		model.put("typeId", typeId);
		model.put("status", status);
		model.put("osId", osId);
		model.put("list", sr.getList());
		model.put("page", sr.getPage());
		return "pages/sso/resourceList";
	}
}
