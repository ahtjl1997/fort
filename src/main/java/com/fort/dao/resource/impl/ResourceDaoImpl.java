package com.fort.dao.resource.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.fort.dao.base.BaseDao;
import com.fort.dao.resource.ResourceDao;
import com.fort.module.resource.Resource;

@Repository("resourceDao")
public class ResourceDaoImpl extends BaseDao implements ResourceDao {

	@Override
	public List<Resource> query(Map<String, Object> map, RowBounds rb) {
		try {
			return getSqlSession().selectList("resource.query", map, rb);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件分页查询资源信息时连接数据库超时");
		}
	}

	@Override
	public int count(Map<String, Object> map) {
		try {
			return getSqlSession().selectOne("resource.count", map);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件查询资源总记录数时连接数据库超时");
		}
	}

	@Override
	public List<Resource> queryByName(String name) {
		try {
			return getSqlSession().selectList("resource.queryByName", name);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("根据资源名称查询资源信息时连接数据库超时");
		}
	}

	@Override
	public Resource queryById(int resId) {
		try {
			return getSqlSession().selectOne("resource.queryById", resId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("根据资源ID查询资源信息时连接数据库超时");
		}
	}

	@Override
	public int insert(Resource r) {
		try {
			return getSqlSession().insert("resource.insert", r);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("添加资源信息时连接数据库超时");
		}
	}

	@Override
	public int update(Resource r) {
		try {
			return getSqlSession().update("resource.update", r);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改资源信息时连接数据库超时");
		}
	}

	@Override
	public int delete(int resId) {
		try {
			return getSqlSession().delete("resource.delete", resId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("删除资源信息时连接数据库超时");
		}
	}
	
	@Override
	public int updateStatus(Map<String,Object> map) {
		try {
			return getSqlSession().update("resource.updateStatus", map);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改资源状态时连接数据库超时");
		}
	}

}
