package com.fort.dao.rule.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.fort.dao.base.BaseDao;
import com.fort.dao.rule.RuleUserDao;
import com.fort.module.employee.Employee;

@Repository("ruleUserDao")
public class RuleUserDaoImpl extends BaseDao implements RuleUserDao {

	@Override
	public List<Employee> query(int ruleId) {
		try {
			return getSqlSession().selectList("ruleUser.query", ruleId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("根据授权ID查询授权用户信息时连接数据库超时");
		}
	}

	@Override
	public int insert(Map<String, Integer> map) {
		try {
			return getSqlSession().insert("ruleUser.insert", map);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("新建授权用户信息时连接数据库超时");
		}
	}

	@Override
	public int delete(Map<String, Integer> map) {
		try {
			return getSqlSession().delete("ruleUser.delete", map);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("删除授权用户信息时连接数据库超时");
		}
	}

}
