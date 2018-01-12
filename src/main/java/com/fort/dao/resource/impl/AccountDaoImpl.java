package com.fort.dao.resource.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.fort.dao.base.BaseDao;
import com.fort.dao.resource.AccountDao;
import com.fort.module.resource.Account;

@Repository("accountDao")
public class AccountDaoImpl extends BaseDao implements AccountDao {

	@Override
	public List<Account> queryByResId(int resId) {
		try {
			return getSqlSession().selectList("account.queryByResId", resId);
		}catch (Exception e) {
			throw new RuntimeException("根据资源ID查询账号信息时连接数据库超时");
		}
	}

	@Override
	public Account queryById(int id) {
		try {
			return getSqlSession().selectOne("account.queryById", id);
		}catch (Exception e) {
			throw new RuntimeException("根据账号ID查询账号信息时连接数据库超时");
		}
	}

	@Override
	public int insert(Account a) {
		try {
			return getSqlSession().insert("account.insert", a);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("添加账号信息时连接数据库超时");
		}
	}

	@Override
	public int update(Account a) {
		try {
			return getSqlSession().update("account.update", a);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改账号信息时连接数据库超时");
		}
	}

	@Override
	public int delete(int id) {
		try {
			return getSqlSession().delete("account.delete", id);
		}catch (Exception e) {
			throw new RuntimeException("删除账号信息时连接数据库超时");
		}
	}

}
