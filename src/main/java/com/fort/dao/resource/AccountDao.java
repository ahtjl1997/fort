package com.fort.dao.resource;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.fort.module.resource.Account;

public interface AccountDao {
	
	public List<Account> queryByResId(int resId);
	
	public Account queryById(int id);
	
	public int insert(Account a);
	
	public int update(Account a);
	
	public int delete(int id);
	
	public List<Account> query(Map<String,Object> map,RowBounds rb);
	
	public int count(Map<String,Object> map);
}
