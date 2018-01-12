package com.fort.service.employee.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fort.dao.employee.LoginDao;
import com.fort.module.employee.Employee;
import com.fort.module.role.Role;
import com.fort.service.employee.LoginService;

/**
 * 用户登录Service接口实现类
 * @author zhangzhigong
 *
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginDao loginDao;
	
	@Override
	public Employee login(String loginName, String password) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loginName", loginName);
		map.put("password", password);
		try {
			return loginDao.login(map);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("根据用户名和密码查询用户信息时出现错误，" + e.getMessage()); 
		}
	}

	@Override
	public Role queryByRole(int empId) {
		try {
			return loginDao.queryRole(empId);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("根据用户ID查询权限时出现错误，" + e.getMessage()); 
		}
	}

}
