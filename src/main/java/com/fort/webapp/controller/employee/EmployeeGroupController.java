package com.fort.webapp.controller.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fort.module.employee.EmployeeGroup;
import com.fort.module.role.Role;
import com.fort.service.employee.EmployeeGroupService;
import com.fort.service.role.RoleService;
import com.util.FortObjectUtil;
import com.util.page.Page;
import com.util.page.SearchResult;

@Controller
@RequestMapping("/employeeGroup")
public class EmployeeGroupController {
	
	@Autowired
	private EmployeeGroupService employeeGroupService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/query")
	public String query(ModelMap model,
			@RequestParam(name="paramQuery",defaultValue="") String keyword,
			@RequestParam(name="startIndex",defaultValue="0") int startIndex,
			@RequestParam(name="roleId",defaultValue="0") int roleId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyword", keyword);
		map.put("roleId", roleId);
		SearchResult<EmployeeGroup> sr = employeeGroupService.query(map, startIndex, Page.DEFAULT_PAGE_SIZE);
		map.clear();
		List<Role> roleList = roleService.query(map);
		
		Role defaultRole = new Role();
		defaultRole.setName("请选择角色");
		roleList.add(0,defaultRole);
		
		model.put("list", sr.getList());
		model.put("page", sr.getPage());
		model.put("roleList", roleList);
		model.put("roleId", roleId);
		model.put("paramQuery", keyword);
		return "pages/employeeGroup/groupList";
	}
	
	@RequestMapping("/insertPage")
	public String insertPage(@ModelAttribute("employeeGroup") EmployeeGroup group,
			@ModelAttribute("roleList") ArrayList<Role> roleList) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Role> list = roleService.query(map);
		Role defaultRole = new Role();
		defaultRole.setName("请选择角色");
		roleList.add(defaultRole);
		if(FortObjectUtil.isNotEmpty(list)) {
			for(Role r : list) {
				roleList.add(r);
			}
		}
		return "pages/employeeGroup/groupInfo";
	}
	
	@RequestMapping("/edit")
	public String edit(@ModelAttribute("employeeGroup") EmployeeGroup group,
			@ModelAttribute("roleList") ArrayList<Role> roleList,
			@RequestParam(name="id",defaultValue="0") int id) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(id == 0) {
			throw new RuntimeException("无效的分组ID");
		}
		
		EmployeeGroup eg = employeeGroupService.queryById(id);
		if(eg == null) {
			throw new RuntimeException("该分组不存在或已被删除");
		}
		group.copy(eg);
		List<Role> list = roleService.query(map);
		Role defaultRole = new Role();
		defaultRole.setName("请选择角色");
		roleList.add(defaultRole);
		if(FortObjectUtil.isNotEmpty(list)) {
			for(Role r : list) {
				roleList.add(r);
			}
		}
		return "pages/employeeGroup/groupInfo";
	}
	
	@RequestMapping("/insert")
	public String insert(@ModelAttribute("employeeGroup") EmployeeGroup group) {
		if(GenericValidator.isBlankOrNull(group.getName())) {
			throw new RuntimeException("请输入分组名称");
		}
		if(!GenericValidator.maxLength(group.getMemo(), 20)) {
			throw new RuntimeException("描述信息不能超过20个字");
		}
		EmployeeGroup eg = employeeGroupService.queryByName(group.getName());
		if(FortObjectUtil.isNotEmpty(eg)) {
			throw new RuntimeException("分组名称重复");
		}
		group.setCreateTime(new Date());
		employeeGroupService.insert(group);
		return "redirect:/employeeGroup/query";
	}
	
	@RequestMapping("/update")
	public String update(@ModelAttribute("employeeGroup") EmployeeGroup group) {
		if(group.getId() == 0) {
			throw new RuntimeException("无效的分组ID");
		}
		if(GenericValidator.isBlankOrNull(group.getName())) {
			throw new RuntimeException("请输入分组名称");
		}
		
		if(!GenericValidator.maxLength(group.getMemo(), 20)) {
			throw new RuntimeException("描述信息不能超过20个字");
		}
		
		EmployeeGroup eg = employeeGroupService.queryByName(group.getName());
		if(!(FortObjectUtil.isEmpty(eg) || eg.getId() == group.getId())) {
			throw new RuntimeException("分组名称重复");
		}
		group.setUpdateTime(new Date());
		employeeGroupService.update(group);
		return "redirect:/employeeGroup/query";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam(name="id",defaultValue="0") int id) {
		if(id == 0) {
			throw new RuntimeException("无效的分组ID");
		}
		EmployeeGroup eg = employeeGroupService.queryById(id);
		if(eg == null) {
			throw new RuntimeException("该分组不存在或已被删除");
		}
		employeeGroupService.delete(id);
		return "redirect:/employeeGroup/query";
	}
	
	@RequestMapping(value="/deletes")
	public String deletes(@RequestParam(name="ids",defaultValue = "") String ids) {
		if(GenericValidator.isBlankOrNull(ids)) {
			throw new RuntimeException("无效的用户分组ID");
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
		employeeGroupService.delete(int_arr);
		return "redirect:/employeeGroup/query";
	}
	
	@RequestMapping(value="/checkName")
	@ResponseBody
	public int checkName(@RequestParam(name="name",defaultValue="") String name) {
		int groupId = 0;
		if(GenericValidator.isBlankOrNull(name)) {
			throw new RuntimeException("请输入分组名称");
		}
		EmployeeGroup group = employeeGroupService.queryByName(name);
		if(FortObjectUtil.isNotEmpty(group)) {
			groupId = group.getId();
		}
		return groupId;
	}
}
