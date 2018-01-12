package com.fort.dao.employee.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.fort.dao.base.BaseDao;
import com.fort.dao.employee.EmployeeGroupDao;
import com.fort.module.employee.EmployeeGroup;

@Repository("employeeGroupDao")
public class EmployeeGroupDaoImpl extends BaseDao implements EmployeeGroupDao {

	@Override
	public List<EmployeeGroup> query(Map<String, Object> map, RowBounds rb) {
		try {
			return getSqlSession().selectList("employeeGroup.query", map, rb);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件分页查询用户分组信息时连接数据库超时");
		}
	}
	
	@Override
	public List<EmployeeGroup> query(Map<String,Object> map){
		try {
			return getSqlSession().selectList("employeeGroup.query", map);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件查询用户分组信息时连接数据库超时");
		}
	}

	@Override
	public int count(Map<String, Object> map) {
		try {
			return getSqlSession().selectOne("employeeGroup.count", map);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件统计用户分组总记录数时连接数据库超时");
		}
	}

	@Override
	public List<EmployeeGroup> queryByName(String name) {
		try {
			return getSqlSession().selectList("employeeGroup.queryByName", name);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("根据用户分组名称查询用户分组信息时连接数据库超时");
		}
	}

	@Override
	public EmployeeGroup queryById(int groupId) {
		try {
			return getSqlSession().selectOne("employeeGroup.queryById", groupId);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("根据用户分组ID查询用户分组信息时连接数据库超时");
		}
	}

	@Override
	public void insert(EmployeeGroup group) {
		try {
			getSqlSession().insert("employeeGroup.insert", group);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("新建用户分组信息时连接数据库超时");
		}
	}

	@Override
	public int update(EmployeeGroup group) {
		try {
			return getSqlSession().update("employeeGroup.update", group);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("修改用户分组信息时连接数据库超时");
		}
	}

	@Override
	public int delete(int groupId) {
		try {
			return getSqlSession().delete("employeeGroup.delete", groupId);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("删除用户分组信息时连接数据库超时");
		}
	}

}
