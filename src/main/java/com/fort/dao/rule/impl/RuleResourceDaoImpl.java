package com.fort.dao.rule.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.fort.dao.base.BaseDao;
import com.fort.dao.rule.RuleResourceDao;
import com.fort.module.rule.RuleInfo;

@Repository("ruleResourceDao")
public class RuleResourceDaoImpl extends BaseDao implements RuleResourceDao {

	@Override
	public List<RuleInfo> query(int ruleId) {
		try {
			return getSqlSession().selectList("ruleResource.query", ruleId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("根据授权ID查询授权资源信息时连接数据库超时");
		}
	}

	@Override
	public int insert(RuleInfo ruleInfo) {
		try {
			return getSqlSession().insert("ruleResource.insert", ruleInfo);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("新建授权资源信息时连接数据库超时");
		}
	}

	@Override
	public int delete(Map<String, Integer> map) {
		try {
			return getSqlSession().delete("ruleResource.delete", map);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("删除授权资源信息时连接数据库超时");
		}
	}

	@Override
	public int update(RuleInfo ruleInfo) {
		try {
			return getSqlSession().update("ruleResource.update", ruleInfo);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改授权资源信息时连接数据库超时");
		}
	}

}
