package com.fort.dao.resource;

import java.util.List;

import com.fort.module.resource.Account;

public interface AccountDao {
	
	public List<Account> queryByResId(int resId);
	
	public Account queryById(int id);
	
	public int insert(Account a);
	
	public int update(Account a);
	
	public int delete(int id);
}
