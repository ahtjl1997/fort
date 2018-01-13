package com.fort.webapp.controller.rule;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fort.module.rule.Rule;
import com.fort.service.rule.RuleService;
import com.util.FortObjectUtil;
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
	public String edit(@ModelAttribute("rule") Rule rule,@RequestParam(name="id",defaultValue="0") int ruleId) {
		if(ruleId == 0) {
			throw new RuntimeException("无效的授权ID");
		}
		Rule r = ruleService.queryById(ruleId);
		if(FortObjectUtil.isEmpty(r)) {
			throw new RuntimeException("该授权不存在或已被删除");
		}
		rule.copy(r);
		return "pages/rule/ruleInfo";
	}
	
	@RequestMapping("/insert")
	public String insert(@RequestParam(name="name",defaultValue="") String name,
			@RequestParam(name="memo",defaultValue="") String memo,
			@RequestParam(name="status",defaultValue="0") int status) {
		if(GenericValidator.isBlankOrNull(name)) {
			throw new RuntimeException("请输入授权名称");
		}
		if(!(status == 0 || status == 1)) {
			throw new RuntimeException("状态信息错误");
		}
		
		Rule rule = ruleService.queryByName(name);
		if(FortObjectUtil.isNotEmpty(rule)) {
			throw new RuntimeException("授权名称重复，请重新输入");
		}
		
		Rule r = new Rule();
		r.setName(name);
		r.setStatus(status);
		r.setMemo(memo);
		r.setCreateTime(new Date());
		ruleService.insert(r);
		return "redirect:/rule/query";
	}
	
	@RequestMapping("/update")
	public String update(@RequestParam(name="id",defaultValue="0") int ruleId,
			@RequestParam(name="name",defaultValue="") String name,
			@RequestParam(name="memo",defaultValue="") String memo,
			@RequestParam(name="status",defaultValue="0") int status) {
		
		if(ruleId == 0) {
			throw new RuntimeException("无效的授权ID");
		}
		
		if(GenericValidator.isBlankOrNull(name)) {
			throw new RuntimeException("请输入授权名称");
		}
		if(!(status == 0 || status == 1)) {
			throw new RuntimeException("状态信息错误");
		}
		
		Rule rule = ruleService.queryById(ruleId);
		if(FortObjectUtil.isEmpty(rule)) {
			throw new RuntimeException("该授权不存在或已被删除");
		}
		
		rule = ruleService.queryByName(name);
		if(!(FortObjectUtil.isEmpty(rule) || rule.getId() == ruleId)) {
			throw new RuntimeException("授权名称重复，请重新输入");
		}
		rule.setId(ruleId);
		rule.setName(name);
		rule.setMemo(memo);
		rule.setStatus(status);
		rule.setUpdateTime(new Date());
		
		ruleService.update(rule);
		
		return "redirect:/rule/query";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam(name="id",defaultValue = "0") int id) {
		if(id == 0) {
			throw new RuntimeException("无效的ID信息");
		}
		Rule rule = ruleService.queryById(id);
		if(FortObjectUtil.isEmpty(rule)) {
			throw new RuntimeException("该授权不存在或已被删除");
		}
		ruleService.delete(id);
		return "redirect:/rule/query";
	}
	
	@RequestMapping("/deletes")
	public String deletes(@RequestParam(name="ids",defaultValue = "") String ids) {
		if(GenericValidator.isBlankOrNull(ids)) {
			throw new RuntimeException("无效的授权ID");
		}
		String[] idArr = {};
		if(ids.indexOf(',') == -1) {
			idArr = new String[] {ids};
		}else {
			idArr = ids.split(",");
		}
		String idStr = "";
		int[] int_arr = new int[idArr.length];
		for(int index = 0; index < idArr.length; index++) {
			idStr = idArr[index];
			if(GenericValidator.isInt(idStr)){
				int_arr[index] = Integer.valueOf(idStr);
			}
		}
		ruleService.delete(int_arr);
		return "redirect:/rule/query";
	}
	
	@RequestMapping(value="/status")
	public String status(@RequestParam(name="id",defaultValue="0") int id,
			@RequestParam(name="status",defaultValue="0") int status) {
		if(id == 0) {
			throw new RuntimeException("无效的授权ID");
		}
		if(!(status == 0 || status == 1)) {
			throw new RuntimeException("无效的状态信息");
		}
		Rule rule = ruleService.queryById(id);
		if(FortObjectUtil.isEmpty(rule)) {
			throw new RuntimeException("该授权不存在或已被删除");
		}
		ruleService.updateStatus(id, status);
		return "redirect:/rule/query";
	}
	
	@RequestMapping(value="/statuss")
	public String statuss(@RequestParam(name="ids",defaultValue="0") String ids,
			@RequestParam(name="status",defaultValue="0") int status) {
		if(GenericValidator.isBlankOrNull(ids)) {
			throw new RuntimeException("无效的授权ID");
		}
		
		if(!(status == 0 || status == 1)) {
			throw new RuntimeException("无效的状态信息");
		}
		
		String[] idArr = {};
		if(ids.indexOf(',') == -1) {
			idArr = new String[] {ids};
		}else {
			idArr = ids.split(",");
		}
		String idStr = "";
		int[] int_arr = new int[idArr.length];
		for(int index = 0; index < idArr.length; index++) {
			idStr = idArr[index];
			if(GenericValidator.isInt(idStr)){
				int_arr[index] = Integer.valueOf(idStr);
			}
		}
		ruleService.updateStatus(int_arr, status);
		return "redirect:/rule/query";
	}
	
	@RequestMapping("/checkName")
	@ResponseBody
	public int checkName(@RequestParam(name="name",defaultValue="") String name) {
		int ruleId = 0;
		if(GenericValidator.isBlankOrNull(name)) {
			throw new RuntimeException("请输入授权名称");
		}
		Rule rule = ruleService.queryByName(name);
		if(FortObjectUtil.isNotEmpty(rule)) {
			ruleId = rule.getId();
		}
		return ruleId;
	}
}
