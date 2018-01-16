package com.fort.dao.rule.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.fort.dao.base.BaseDao;
import com.fort.dao.rule.RuleAccountDao;
import com.fort.module.resource.Account;

@Repository("ruleAccountDao")
public class RuleAccountDaoImpl extends BaseDao implements RuleAccountDao {

	@Override
	public List<Account> query(int relId) {
		try {
			return getSqlSession().selectList("ruleAccount.query", relId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询授权账号时连接数据库超时");
		}
	}

	@Override
	public int insert(Map<String, Integer> map) {
		try {
			return getSqlSession().insert("ruleAccount.insert", map);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("新建授权账号时连接数据库超时");
		}
	}

	@Override
	public int delete(Map<String, Integer> map) {
		try {
			return getSqlSession().delete("ruleAccount.delete", map);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("删除授权账号时连接数据库超时");
		}
	}

}
