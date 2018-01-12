package com.fort.dao.role.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.fort.dao.base.BaseDao;
import com.fort.dao.role.RoleDao;
import com.fort.module.role.Role;

/**
 * 角色DAO接口实现类
 * @author zhangzhigong
 *
 */
@Repository("roleDao")
public class RoleDaoImpl extends BaseDao implements RoleDao {

	@Override
	public List<Role> queryByName(String name) {
		try {
			return getSqlSession().selectList("role.queryByName", name);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("根据角色名称查询角色信息时连接数据库超时");
		}
	}

	@Override
	public Role queryById(int roleId) {
		try {
			return getSqlSession().selectOne("role.queryById", roleId);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("根据角色ID查询角色信息时连接数据库超时");
		}
	}

	@Override
	public List<Role> query(Map<String, Object> map, RowBounds rb) {
		try {
			return getSqlSession().selectList("role.query", map, rb);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件分页查询角色信息时连接数据库超时");
		}
	}

	@Override
	public int count(Map<String, Object> map) {
		try {
			return getSqlSession().selectOne("role.count", map);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件统计角色总记录数时连接数据库超时");
		}
	}
	
	@Override
	public List<Role> query(Map<String,Object> map){
		try {
			return getSqlSession().selectList("role.query", map);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件查询角色信息时连接数据库超时");
		}
	}

	@Override
	public void insert(Role role) {
		try {
			getSqlSession().insert("role.insert", role);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("新建角色信息时连接数据库超时");
		}
	}

	@Override
	public int update(Role role) {
		try {
			return getSqlSession().update("role.update", role);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("修改角色信息时连接数据库超时");
		}
	}

	@Override
	public int delete(int roleId) {
		try {
			return getSqlSession().delete("role.delete", roleId);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("删除角色信息时连接数据库超时");
		}
	}

}
