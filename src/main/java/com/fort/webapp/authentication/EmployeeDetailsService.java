package com.fort.webapp.authentication;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fort.config.RoleConfig;
import com.fort.module.employee.Employee;
import com.fort.module.role.Role;
import com.fort.service.employee.EmployeeService;
import com.fort.service.employee.LoginService;
import com.util.FortObjectUtil;

public class EmployeeDetailsService implements UserDetailsService {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private LoginService loginService;
	
	public EmployeeDetailsService() {
		
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeService.queryByLoginName(username);
		if(employee == null) {
			throw new RuntimeException("查询用户信息时出现异常");
		}
		Role role = loginService.queryByRole(employee.getId());
		if(role == null) {
			throw new RuntimeException("用户未分配权限");
		}
		
		//处理权限信息
		Collection<GrantedAuthority> roleList = new ArrayList<GrantedAuthority>();
		roleList.add(getGrantedAuthority(RoleConfig.LOGIN_USER));
		//用户管理权限
		if(role.getViewEmp() == 1) {
			roleList.add(getGrantedAuthority(RoleConfig.VIEW_EMP));
		}
		if(role.getNewEmp() == 1) {
			roleList.add(getGrantedAuthority(RoleConfig.NEW_EMP));
		}
		if(role.getUpdateEmp() == 1) {
			roleList.add(getGrantedAuthority(RoleConfig.UPDATE_EMP));
		}
		if(role.getDelEmp() == 1) {
			roleList.add(getGrantedAuthority(RoleConfig.DEL_EMP));
		}
		if(role.getImportEmp() == 1) {
			roleList.add(getGrantedAuthority(RoleConfig.IMPORT_EMP));
		}
		if(role.getExportEmp() == 1) {
			roleList.add(getGrantedAuthority(RoleConfig.EXPORT_EMP));
		}
		//用户分组管理权限
		if(role.getViewEmpGroup() == 1) {
			roleList.add(getGrantedAuthority(RoleConfig.VIEW_EMP_GROUP));
		}
		if(role.getNewEmpGroup() == 1) {
			roleList.add(getGrantedAuthority(RoleConfig.NEW_EMP_GROUP));
		}
		if(role.getUpdateEmpGroup() == 1) {
			roleList.add(getGrantedAuthority(RoleConfig.UPDATE_EMP_GROUP));
		}
		if(role.getDelEmpGroup() == 1) {
			roleList.add(getGrantedAuthority(RoleConfig.DEL_EMP_GROUP));
		}
		
		//角色管理权限
		if(role.getViewRole() == 1) {
			roleList.add(getGrantedAuthority(RoleConfig.VIEW_ROLE));
		}
		if(role.getNewRole() == 1) {
			roleList.add(getGrantedAuthority(RoleConfig.NEW_ROLE));
		}
		if(role.getUpdateRole() == 1) {
			roleList.add(getGrantedAuthority(RoleConfig.UPDATE_ROLE));
		}
		if(role.getDelRole() == 1) {
			roleList.add(getGrantedAuthority(RoleConfig.DEL_ROLE));
		}
		
		//资源管理
		if(role.getNewRes() == 1) {
			roleList.add(getGrantedAuthority(RoleConfig.NEW_RES));
		}
		if(role.getUpdateRes() == 1) {
			roleList.add(getGrantedAuthority(RoleConfig.UPDATE_RES));
		}
		if(role.getDelRes() == 1) {
			roleList.add(getGrantedAuthority(RoleConfig.DEL_RES));
		}
		if(role.getViewRes() == 1) {
			roleList.add(getGrantedAuthority(RoleConfig.VIEW_RES));
		}
		
		//授权管理
		if(role.getViewEmp() == 1 && role.getViewRes() == 1) {
			roleList.add(getGrantedAuthority(RoleConfig.VIEW_RULE));
		}
		
		if(FortObjectUtil.isEmpty(employee)) {
			throw new RuntimeException("用户名密码错误");
		}
		employee.setRole(role);
		employee.setAuthorities(roleList);
		employee.setPassword("");
		return employee;
	}

	private GrantedAuthority getGrantedAuthority(String role) {
		return new SimpleGrantedAuthority("ROLE_" + role);
	}
}
