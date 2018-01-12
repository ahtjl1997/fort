package com.fort.service.employee;

import com.fort.module.employee.Employee;
import com.fort.module.role.Role;

/**
 * 用户登录Service接口
 * @author zhangzhigong
 *
 */
public interface LoginService {
	
	/**
	 * 根据用户登录名和密码查询用户信息
	 * @param loginName 登录名
	 * @param password 密码
	 * @return
	 */
	public Employee login(String loginName,String password);
	
	/**
	 * 根据用户ID查询权限信息
	 * 
	 * @param empId
	 * @return
	 */
	public Role queryByRole(int empId);
}
