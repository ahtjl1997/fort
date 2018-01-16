package com.fort.webapp.controller.rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.validator.GenericValidator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import com.fort.module.rule.RuleInfo;
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
	public String edit(@ModelAttribute("rule") Rule rule,@RequestParam(name="id",defaultValue="0") int ruleId,ModelMap model) throws JSONException {
		if(ruleId == 0) {
			throw new RuntimeException("无效的授权ID");
		}
		Rule r = ruleService.queryById(ruleId);
		if(FortObjectUtil.isEmpty(r)) {
			throw new RuntimeException("该授权不存在或已被删除");
		}
		rule.copy(r);
		
		List<RuleInfo> resList = ruleService.queryResource(ruleId);
		List<Employee> empList = ruleService.queryUser(ruleId);
		JSONArray resArr = new JSONArray();
		JSONArray userArr = new JSONArray();
		if(FortObjectUtil.isNotEmpty(resList)) {
			for(RuleInfo ri : resList) {
				JSONObject json = new JSONObject();
				json.put("resId", ri.getId());
				json.put("name", ri.getName());
				json.put("ip", ri.getIp());
				json.put("type", ri.getTypeName());
				json.put("os", ri.getOsName());
				Collection<Account> accList = ri.getAccountList();
				JSONArray accArr = new JSONArray();
				Set<String> protocols = new HashSet<String>();
				if(FortObjectUtil.isNotEmpty(accList)) {
					for(Account acc : accList) {
						JSONObject accJson = new JSONObject();
						accJson.put("id", acc.getId());
						accJson.put("account", acc.getName());
						accArr.put(accJson);
					}
				}
				
				if(ri.getUseRdp() == 1) {
					protocols.add("RDP");
				}
				if(ri.getUseSsh() == 1) {
					protocols.add("SSH");
				}
				if(ri.getUseSftp() == 1) {
					protocols.add("SFTP");
				}
				
				json.put("accounts", accArr);
				json.put("protocols", protocols);
				resArr.put(json);
			}
		}
		
		if(FortObjectUtil.isNotEmpty(empList)) {
			for(Employee e : empList) {
				JSONObject user = new JSONObject();
				user.put("id", e.getId());
				user.put("name", e.getName());
				user.put("username", e.getUsername());
				userArr.put(user);
			}
		}
		model.put("users", userArr.toString());
		model.put("resources", resArr.toString());
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
		
		int[] empIdArr = {};
		try {
			JSONArray userArr = new JSONArray(users);
			int userLen = userArr.length();
			if(userLen > 0) {
				empIdArr = new int[userLen];
				for(int index = 0;index < userLen;index++) {
					JSONObject user = userArr.getJSONObject(index);
					empIdArr[index] = user.getInt("id");
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException("用户信息错误");
		}
		
		List<RuleInfo> ruleInfoList = new ArrayList<RuleInfo>();
		try {
			JSONArray resArr = new JSONArray(resources);
			int resLen = resArr.length();
			if(resLen > 0) {
				for(int index = 0; index < resLen; index++) {
					JSONObject res = resArr.getJSONObject(index);
					RuleInfo r = new RuleInfo();
					r.setId(res.getInt("resId"));
					List<Account> list = new ArrayList<Account>();
					JSONArray accounts = res.getJSONArray("accounts");
					JSONArray protocols = res.getJSONArray("protocols");
					for(int i = 0;i < accounts.length();i++) {
						JSONObject accJson = accounts.getJSONObject(i);
						Account acc = new Account();
						acc.setId(accJson.getInt("id"));
						list.add(acc);
					}
					r.setAccountList(list);
					
					int plen = protocols.length();
					for(int i = 0;i < plen;i++) {
						String protocol = protocols.getString(i);
						if("RDP".equals(protocol)) {
							r.setUseRdp(1);
						}else if("SSH".equals(protocol)) {
							r.setUseSsh(1);
						}else if("SFTP".equals(protocol)) {
							r.setUseSftp(1);
						}
					}
					
					ruleInfoList.add(r);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException("设备信息错误");
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
		int ruleId = ruleService.insert(r);
		ruleService.insertResource(ruleId, ruleInfoList);
		ruleService.insertUser(ruleId, empIdArr);
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
		
		int[] empIdArr = {};
		try {
			JSONArray userArr = new JSONArray(users);
			int userLen = userArr.length();
			if(userLen > 0) {
				empIdArr = new int[userLen];
				for(int index = 0;index < userLen;index++) {
					JSONObject user = userArr.getJSONObject(index);
					empIdArr[index] = user.getInt("id");
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException("用户信息错误");
		}
		
		List<RuleInfo> ruleInfoList = new ArrayList<RuleInfo>();
		try {
			JSONArray resArr = new JSONArray(resources);
			int resLen = resArr.length();
			if(resLen > 0) {
				for(int index = 0; index < resLen; index++) {
					JSONObject res = resArr.getJSONObject(index);
					RuleInfo r = new RuleInfo();
					r.setId(res.getInt("resId"));
					List<Account> list = new ArrayList<Account>();
					JSONArray accounts = res.getJSONArray("accounts");
					JSONArray protocols = res.getJSONArray("protocols");
					for(int i = 0;i < accounts.length();i++) {
						JSONObject accJson = accounts.getJSONObject(i);
						Account acc = new Account();
						acc.setId(accJson.getInt("id"));
						list.add(acc);
					}
					r.setAccountList(list);
					
					int plen = protocols.length();
					for(int i = 0;i < plen;i++) {
						String protocol = protocols.getString(i);
						if("RDP".equals(protocol)) {
							r.setUseRdp(1);
						}else if("SSH".equals(protocol)) {
							r.setUseSsh(1);
						}else if("SFTP".equals(protocol)) {
							r.setUseSftp(1);
						}
					}
					
					ruleInfoList.add(r);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException("设备信息错误");
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
		ruleService.insertResource(ruleId, ruleInfoList);
		ruleService.insertUser(ruleId, empIdArr);
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
