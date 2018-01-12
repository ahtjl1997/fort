package com.fort.webapp.controller.employee;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fort.config.AppConfig;
import com.fort.module.employee.Employee;
import com.fort.module.employee.EmployeeGroup;
import com.fort.module.role.Role;
import com.fort.service.employee.EmployeeGroupService;
import com.fort.service.employee.EmployeeService;
import com.fort.service.role.RoleService;
import com.util.FortObjectUtil;
import com.util.StringUtil;
import com.util.page.Page;
import com.util.page.SearchResult;
import com.util.regex.RegexUtil;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private EmployeeGroupService employeeGroupService;
	
	@RequestMapping("/query")
	public String query(ModelMap model,
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
		return "pages/employee/employeeList";
	}
	
	@RequestMapping("/insertPage")
	public String insertPage(@ModelAttribute("employee") Employee employee,
			@ModelAttribute("roleList") ArrayList<Role> roleList,
			@ModelAttribute("empGroupList") ArrayList<EmployeeGroup> empGeoupList) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Role> roleArr = roleService.query(map);
		Role defaultRole = new Role();
		defaultRole.setName("请选择角色");
		roleList.add(defaultRole);
		if(FortObjectUtil.isNotEmpty(roleArr)) {
			for(Role role : roleArr) {
				roleList.add(role);
			}
		}
		List<EmployeeGroup> groupArr = employeeGroupService.query(map);
		EmployeeGroup defaultGroup = new EmployeeGroup();
		defaultGroup.setName("请选择分组");
		empGeoupList.add(defaultGroup);
		if(FortObjectUtil.isNotEmpty(groupArr)) {
			for(EmployeeGroup group : groupArr) {
				empGeoupList.add(group);
			}
		}
		return "pages/employee/employeeInfo";
	}
	
	@RequestMapping("/edit")
	public String edit(@ModelAttribute("employee") Employee employee,
			@RequestParam(name="id",defaultValue="0")int id,
			@ModelAttribute("roleList") ArrayList<Role> roleList,
			@ModelAttribute("empGroupList") ArrayList<EmployeeGroup> empGeoupList) {
		if(id == 0) {
			throw new RuntimeException("无效的用户ID");
		}
		Employee emp = employeeService.queryById(id);
		if(emp == null) {
			throw new RuntimeException("该用户不存在或已被删除");
		}
		employee.copy(emp);
		Map<String,Object> map = new HashMap<String,Object>();
		List<Role> roleArr = roleService.query(map);
		Role defaultRole = new Role();
		defaultRole.setName("请选择角色");
		roleList.add(defaultRole);
		if(!(roleArr == null || roleArr.isEmpty())) {
			for(Role role : roleArr) {
				roleList.add(role);
			}
		}
		List<EmployeeGroup> groupArr = employeeGroupService.query(map);
		EmployeeGroup defaultGroup = new EmployeeGroup();
		defaultGroup.setName("请选择分组");
		empGeoupList.add(defaultGroup);
		if(FortObjectUtil.isNotEmpty(groupArr)) {
			for(EmployeeGroup group : groupArr) {
				empGeoupList.add(group);
			}
		}
		return "pages/employee/employeeInfo";
	}
	
	@RequestMapping("/update")
	public String update(@ModelAttribute("employee") Employee employee,HttpServletRequest request) {
		if(employee.getId() == 0) {
			throw new RuntimeException("无效的用户ID");
		}
		
		String timeOut = request.getParameter("timeOut");
		if(StringUtil.isNotBlank(timeOut) && RegexUtil.test(timeOut, RegexUtil.YYYY_MM_DD_REGEX)) {
			try {
				employee.setPwdTimeOut(AppConfig.YYYY_MM_DD_FORMAT.parse(timeOut));
			} catch (ParseException e) {
				e.printStackTrace();
				throw new RuntimeException("密码期限格式错误");
			}
		}
		
		Employee emp = employeeService.queryById(employee.getId());
		if(emp == null) {
			throw new RuntimeException("该用户不存在或已被删除");
		}
		
		Employee tmp = employeeService.queryByLoginName(employee.getUsername());
		if(!(tmp == null || (tmp.getUsername().equals(emp.getUsername()) 
				&& tmp.getId() == emp.getId()))) {
			throw new RuntimeException("登录名重复");
		}
		
		if("admin".equals(emp.getUsername())) {
			employee.setRoleId(emp.getRoleId());
			employee.setStatus(1);
		}
		
		emp.copy(employee);
		emp.setUpdateTime(new Date());
		employeeService.update(emp);
		return "redirect:/employee/query";
	}
	
	@RequestMapping("/insert")
	public String insert(@ModelAttribute("employee") Employee employee,HttpServletRequest request) {
		String timeOut = request.getParameter("timeOut");
		if(StringUtil.isNotBlank(timeOut) && RegexUtil.test(timeOut, RegexUtil.YYYY_MM_DD_REGEX)) {
			try {
				employee.setPwdTimeOut(AppConfig.YYYY_MM_DD_FORMAT.parse(timeOut));
			} catch (ParseException e) {
				e.printStackTrace();
				throw new RuntimeException("密码期限格式错误");
			}
		}
		if(StringUtil.isBlank(employee.getName())) {
			throw new RuntimeException("请输入姓名");
		}
		if(StringUtil.isBlank(employee.getUsername())) {
			throw new RuntimeException("请输入登录名");
		}
		Employee emp = employeeService.queryByLoginName(employee.getUsername());
		if(emp != null) {
			throw new RuntimeException("登录名重复");
		}
		employeeService.insert(employee);
		return "redirect:/employee/query";
	}
	
	@RequestMapping(value="/delete")
	public String delete(@RequestParam(name="id",defaultValue = "0") int id) {
		if(id == 0) {
			throw new RuntimeException("无效的用户ID");
		}
		Employee emp = employeeService.queryById(id);
		if(emp == null) {
			throw new RuntimeException("该用户不存在或已被删除");
		}
		if(!"admin".equals(emp.getUsername())) {
			employeeService.delete(id);
		}
		return "redirect:/employee/query";
	}
	
	@RequestMapping(value="/deletes")
	public String deletes(@RequestParam(name="ids",defaultValue = "") String ids) {
		if(GenericValidator.isBlankOrNull(ids)) {
			throw new RuntimeException("无效的用户ID");
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
		employeeService.delete(int_arr);
		return "redirect:/employee/query";
	}
	
	@RequestMapping(value="/status")
	public String status(@RequestParam(name="id",defaultValue="0") int id,
			@RequestParam(name="status",defaultValue="0") int status) {
		if(id == 0) {
			throw new RuntimeException("无效的用户ID");
		}
		Employee emp = employeeService.queryById(id);
		if(emp == null) {
			throw new RuntimeException("该用户不存在或已被删除");
		}
		if(!(status == 0 || status == 1)) {
			throw new RuntimeException("无效的状态信息");
		}
		if(!"admin".equals(emp.getUsername())) {
			employeeService.updateStatus(id, status);
		}
		return "redirect:/employee/query";
	}
	
	@RequestMapping(value="/statuss")
	public String statuss(@RequestParam(name="ids",defaultValue="0") String ids,
			@RequestParam(name="status",defaultValue="0") int status) {
		if(GenericValidator.isBlankOrNull(ids)) {
			throw new RuntimeException("无效的用户ID");
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
		employeeService.updateStatus(int_arr, status);
		return "redirect:/employee/query";
	}
	
	@RequestMapping(value="/checkName")
	@ResponseBody
	public int checkName(@RequestParam(name="userName",defaultValue="") String userName) {
		int empId = 0;
		if(GenericValidator.isBlankOrNull(userName)) {
			throw new RuntimeException("请输入登录账号");
		}
		Employee emp = employeeService.queryByLoginName(userName);
		if(FortObjectUtil.isNotEmpty(emp)) {
			empId = emp.getId();
		}
		return empId;
	}
}
