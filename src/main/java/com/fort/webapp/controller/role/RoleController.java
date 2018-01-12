package com.fort.webapp.controller.role;

import java.util.Date;
import java.util.HashMap;
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

import com.fort.module.role.Role;
import com.fort.service.role.RoleService;
import com.util.FortObjectUtil;
import com.util.page.Page;
import com.util.page.SearchResult;

@Controller
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/query")
	public String query(ModelMap model,
			@RequestParam(name="paramQuery",defaultValue="") String keyword,
			@RequestParam(name="startIndex",defaultValue="0")
			int startIndex) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyword", keyword);
		SearchResult<Role> sr = roleService.query(map, startIndex, Page.DEFAULT_PAGE_SIZE);
		model.put("list", sr.getList());
		model.put("page", sr.getPage());
		model.put("paramQuery", keyword);
		return "pages/role/roleList";
	}
	
	@RequestMapping("/insertPage")
	public String insertPage(@ModelAttribute("role") Role role) {
		return "pages/role/roleInfo";
	}
	
	@RequestMapping("/edit")
	public String edit(@ModelAttribute("role") Role role,@RequestParam(name="id",defaultValue="0") int roleId) {
		if(roleId == 0) {
			throw new RuntimeException("无效的角色ID");
		}
		Role r = roleService.queryById(roleId);
		if(r == null) {
			throw new RuntimeException("该角色不存在或已被删除");
		}
		role.copy(r);
		return "pages/role/roleInfo";
	}
	
	@RequestMapping("/insert")
	public String insert(@ModelAttribute("role") Role role,HttpServletRequest request) {
		if(GenericValidator.isBlankOrNull(role.getName())) {
			throw new RuntimeException("请输入角色名称");
		}
		Role r = roleService.queryByName(role.getName());
		if(FortObjectUtil.isNotEmpty(r)) {
			throw new RuntimeException("角色名称重复");
		}
		role.setCreateTime(new Date());
		roleService.insert(role);
		return "redirect:/role/query";
	}
	
	@RequestMapping("/update")
	public String update(@ModelAttribute("role") Role role,HttpServletRequest request) {
		if(role.getId() == 0) {
			throw new RuntimeException("无效的角色ID");
		}
		Role r = roleService.queryById(role.getId());
		if(r == null) {
			throw new RuntimeException("该角色不存在或已被删除");
		}
		r = roleService.queryByName(role.getName());
		if(!(FortObjectUtil.isEmpty(r) || r.getId() == role.getId())) {
			throw new RuntimeException("角色名称重复");
		}
		roleService.update(role);
		return "redirect:/role/query";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam(name="id",defaultValue = "0") int id) {
		if(id == 0) {
			throw new RuntimeException("无效的角色ID");
		}
		Role r = roleService.queryById(id);
		if(r == null) {
			throw new RuntimeException("该角色不存在或已被删除");
		}
		if(!"系统管理员".equals(r.getName())) {
			roleService.delete(id);
		}
		return "redirect:/role/query";
	}
	
	@RequestMapping(value="/deletes")
	public String deletes(@RequestParam(name="ids",defaultValue = "") String ids) {
		if(GenericValidator.isBlankOrNull(ids)) {
			throw new RuntimeException("无效的角色ID");
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
		roleService.delete(int_arr);
		return "redirect:/role/query";
	}
	
	@RequestMapping("/checkName")
	@ResponseBody
	public int checkName(@RequestParam(name="name",defaultValue="") String name) {
		int roleId = 0;
		if(GenericValidator.isBlankOrNull(name)) {
			throw new RuntimeException("请输入角色名称");
		}
		Role role = roleService.queryByName(name);
		if(FortObjectUtil.isNotEmpty(role)) {
			roleId = role.getId();
		}
		return roleId;
	}
}
