package com.fort.dao.employee.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.fort.dao.base.BaseDao;
import com.fort.dao.employee.EmployeeDao;
import com.fort.module.employee.Employee;

@Repository("employeeDao")
public class EmployeeDaoImpl extends BaseDao implements EmployeeDao {

	@Override
	public List<Employee> query(Map<String, Object> map,RowBounds rb) {
		try {
			return getSqlSession().selectList("employee.query", map, rb);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件查询用户信息时连接数据库超时");
		}
	}
	
	@Override
	public int count(Map<String,Object> map) {
		try {
			return getSqlSession().selectOne("employee.count", map);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件统计用户总记录数时连接数据库超时");
		}
	}

	@Override
	public List<Employee> queryByLoginName(String loginName) {
		try {
			return getSqlSession().selectList("employee.queryByLoginName", loginName);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件查询用户信息时连接数据库超时");
		}
	}

	@Override
	public Employee queryById(int empId) {
		try {
			return getSqlSession().selectOne("employee.queryById", empId);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("根据用户ID查询用户信息时连接数据库超时");
		}
	}

	@Override
	public void insert(Employee employee) {
		try {
			getSqlSession().insert("employee.insert",employee);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("新建用户信息时连接数据库超时");
		}
	}

	@Override
	public int update(Employee employee) {
		try {
			return getSqlSession().update("employee.update",employee);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("修改用户信息时连接数据库超时");
		}
	}

	@Override
	public int delete(int empId) {
		try {
			return getSqlSession().delete("employee.delete",empId);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("删除用户信息时连接数据库超时");
		}
	}
	
	@Override
	public int updateStatus(Map<String,Object> map) {
		try {
			return getSqlSession().update("employee.updateStatus",map);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("更新用户状态时连接数据库超时");
		}
	}

}
