package com.fort.dao.rule.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.fort.dao.base.BaseDao;
import com.fort.dao.rule.RuleDao;
import com.fort.module.rule.Rule;

@Repository("ruleDao")
public class RuleDaoImpl extends BaseDao implements RuleDao {

	@Override
	public List<Rule> query(Map<String, Object> map,RowBounds rb) {
		try {
			return getSqlSession().selectList("rule.query", map, rb);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件查询授权时连接数据库超时");
		}
	}

	@Override
	public int count(Map<String, Object> map) {
		try {
			return getSqlSession().selectOne("rule.count", map);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件同级授权总记录数时连接数据库超时");
		}
	}

	@Override
	public Rule queryById(int id) {
		try {
			return getSqlSession().selectOne("rule.queryById", id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("根据ID查询授权信息时连接数据库超时");
		}
	}

	@Override
	public List<Rule> queryByName(String name) {
		try {
			return getSqlSession().selectList("rule.queryByName", name);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("根据名称查询授权信息时连接数据库超时");
		}
	}

	@Override
	public int insert(Rule r) {
		try {
			return getSqlSession().insert("rule.insert", r);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("新建授权信息时连接数据库超时");
		}
	}

	@Override
	public int update(Rule r) {
		try {
			return getSqlSession().update("rule.update", r);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改授权信息时连接数据库超时");
		}
	}

	@Override
	public int updateStatus(Map<String, Object> map) {
		try {
			return getSqlSession().update("rule.updateStatus", map);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改授权状态时连接数据库超时");
		}
	}

	@Override
	public int delete(int id) {
		try {
			return getSqlSession().delete("rule.delete", id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("根据ID删除授权时连接数据库超时");
		}
	}

}
