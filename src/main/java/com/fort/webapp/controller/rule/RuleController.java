package com.fort.webapp.controller.rule;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fort.module.rule.Rule;
import com.fort.service.rule.RuleService;
import com.util.page.Page;
import com.util.page.SearchResult;

@RequestMapping("/rule")
@Controller
public class RuleController {
	
	@Autowired
	private RuleService ruleService;
	
	@RequestMapping("/query")
	public String query(ModelMap model,
			@RequestParam(name="paramQuery",defaultValue="") String keyword,
			@RequestParam(name="startIndex",defaultValue="0") int startIndex,
			@RequestParam(name="startIndex",defaultValue="-1") int status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyword", keyword);
		if(status != -1) {
			map.put("status", status);
		}
		
		SearchResult<Rule> sr = ruleService.query(map, startIndex, Page.DEFAULT_PAGE_SIZE);
		
		model.put("list", sr.getList());
		model.put("page", sr.getPage());
		model.put("paramQuery", keyword);
		model.put("status", status);
		return "pages/rule/ruleList";
	}
	
	@RequestMapping("/insertPage")
	public String insertPage(@ModelAttribute("rule") Rule rule) {
		return "pages/rule/ruleInfo";
	}
	
	@RequestMapping("/edit")
	public String edit(@ModelAttribute("rule") Rule rule) {
		return "pages/rule/ruleInfo";
	}
}
