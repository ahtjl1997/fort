package com.fort.dao.employee;

import java.util.Map;

import com.fort.module.employee.Employee;
import com.fort.module.role.Role;

/**
 * 用户登录DAO接口
 * 
 * @author zhangzhigong
 *
 */
public interface LoginDao {
	
	/**
	 * 根据用户名密码查询用户信息
	 * @param map
	 * @return
	 */
	public Employee login(Map<String,Object> map);
	
	/**
	 * 根据用户ID查询权限信息
	 * 
	 * @param empId
	 * @return
	 */
	public Role queryRole(int empId);
}
