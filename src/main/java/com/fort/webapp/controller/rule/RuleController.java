package com.fort.webapp.controller.rule;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fort.module.employee.Employee;
import com.fort.module.employee.EmployeeGroup;
import com.fort.module.resource.Account;
import com.fort.module.resource.Resource;
import com.fort.module.role.Role;
import com.fort.module.rule.Rule;
import com.fort.service.employee.EmployeeGroupService;
import com.fort.service.employee.EmployeeService;
import com.fort.service.resource.ResourceService;
import com.fort.service.role.RoleService;
import com.fort.service.rule.RuleService;
import com.util.FortObjectUtil;
import com.util.page.Page;
import com.util.page.SearchResult;

@RequestMapping("/rule")
@Controller
public class RuleController {
	
	@Autowired
	private RuleService ruleService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private EmployeeGroupService employeeGroupService;
	
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping("/query")
	public String query(ModelMap model,
			@RequestParam(name="paramQuery",defaultValue="") String keyword,
			@RequestParam(name="startIndex",defaultValue="0") int startIndex,
			@RequestParam(name="status",defaultValue="-1") int status) {
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
	public String edit(@ModelAttribute("rule") Rule rule,@RequestParam(name="id",defaultValue="0") int ruleId,ModelMap model) {
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
			@RequestParam(name="status",defaultValue="0") int status,
			@RequestParam(name="users",defaultValue="[]") String users,
			@RequestParam(name="resources",defaultValue="[]") String resources) {
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
			@RequestParam(name="status",defaultValue="0") int status,
			@RequestParam(name="users",defaultValue="[]") String users,
			@RequestParam(name="resources",defaultValue="[]") String resources) {
		
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
	
	@RequestMapping("/queryUser")
	public String queryUser(ModelMap model,
			@RequestParam(name="paramQuery",defaultValue="") String keyword,
			@RequestParam(name="startIndex",defaultValue="0") int startIndex,
			@RequestParam(name="roleId",defaultValue="0") int roleId,
			@RequestParam(name="groupId",defaultValue="0") int groupId,
			@RequestParam(name="status",defaultValue="-1") int status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyword", keyword);
		map.put("roleId", roleId);
		map.put("groupId", groupId);
		map.put("status", status);
		
		SearchResult<Employee> sr = employeeService.query(map, startIndex, Page.DEFAULT_PAGE_SIZE);
		map.clear();
		
		List<Role> roleList = roleService.query(map);
		List<EmployeeGroup> groupList = employeeGroupService.query(map);
		
		Role defaultRole = new Role();
		defaultRole.setName("请选择角色");
		roleList.add(0,defaultRole);
		
		EmployeeGroup defaultGroup = new EmployeeGroup();
		defaultGroup.setName("请选择分组");
		groupList.add(0,defaultGroup);
		
		model.put("list", sr.getList());
		model.put("page", sr.getPage());
		model.put("paramQuery", keyword);
		model.put("roleList", roleList);
		model.put("groupList", groupList);
		model.put("status", status);
		model.put("paramQuery", keyword);
		model.put("roleId", roleId);
		model.put("groupId", groupId);
		return "pages/rule/userList";
	}
	
	@RequestMapping("/queryResource")
	public String queryResource(ModelMap model,
			@RequestParam(name="paramQuery",defaultValue="") String keyword,
			@RequestParam(name="startIndex",defaultValue="0") int startIndex,
			@RequestParam(name="status",defaultValue="-1") int status,
			@RequestParam(name="typeId",defaultValue="-1") int typeId,
			@RequestParam(name="osId",defaultValue="-1") int osId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyword", keyword);
		if(status != -1) {
			map.put("status", status);
		}
		if(typeId != -1) {
			map.put("typeId", typeId);
		}
		if(osId != -1) {
			map.put("osId", osId);
		}
		
		SearchResult<Resource> sr = resourceService.query(map, startIndex, Page.DEFAULT_PAGE_SIZE);
		model.put("paramQuery", keyword);
		model.put("typeId", typeId);
		model.put("status", status);
		model.put("osId", osId);
		model.put("list", sr.getList());
		model.put("page", sr.getPage());
		return "pages/rule/resourceList";
	}
	
	@RequestMapping("/queryAccountByResId")
	@ResponseBody
	public List<Account> queryAccountByResId(@RequestParam("resId") int resId){
		if(resId == 0) {
			throw new RuntimeException("无效的设备ID");
		}
		return resourceService.queryAccountByResId(resId);
	}
	
	@RequestMapping("/queryAccount")
	public String queryAccount(ModelMap model,
			@RequestParam(name="paramQuery",defaultValue="") String keyword,
			@RequestParam(name="startIndex",defaultValue="0") int startIndex,
			@RequestParam(name="resId",defaultValue="0") int resId) {
		if(resId == 0) {
			throw new RuntimeException("无效的设备ID");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyword", keyword);
		map.put("resId", resId);
		SearchResult<Account> sr = resourceService.queryAccount(map, startIndex, 5);
		model.put("list", sr.getList());
		model.put("page", sr.getPage());
		model.put("paramQuery", keyword);
		model.put("resId", resId);
		return "pages/rule/accountList";
	}
	
	@RequestMapping("/queryProtocol")
	@ResponseBody
	public Set<String> queryProrocol(@RequestParam(name="resId",defaultValue="0") int resId) {
		if(resId == 0) {
			throw new RuntimeException("无效的设备ID");
		}
		
		Resource r = resourceService.queryById(resId);
		if(FortObjectUtil.isEmpty(r)) {
			throw new RuntimeException("该设备不存在或已被删除");
		}
		Set<String> set = new HashSet<String>();
		if(r.getUseRdp() == 1) {
			set.add("RDP");
		}
		if(r.getUseSsh() == 1) {
			set.add("SSH");
		}
		if(r.getUseSftp() == 1) {
			set.add("SFTP");
		}
		return set;
	}
}
