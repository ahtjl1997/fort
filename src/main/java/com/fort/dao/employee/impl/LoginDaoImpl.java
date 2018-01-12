package com.fort.dao.employee.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.fort.dao.base.BaseDao;
import com.fort.dao.employee.LoginDao;
import com.fort.module.employee.Employee;
import com.fort.module.role.Role;

/**
 * 用户登录DAO接口实现类
 * @author zhangzhigong
 *
 */
@Repository("loginDao")
public class LoginDaoImpl extends BaseDao implements LoginDao {

	@Override
	public Employee login(Map<String, Object> map) {
		try {
			return getSqlSession().selectOne("login.login", map);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("根据用户名和密码查询用户信息时连接数据库超时");
		}
	}

	@Override
	public Role queryRole(int empId) {
		try {
			return getSqlSession().selectOne("login.queryRole", empId);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("根据用户ID查询权限时连接数据库超时");
		}
	}

}
