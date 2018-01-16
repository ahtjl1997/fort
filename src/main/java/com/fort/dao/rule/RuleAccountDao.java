package com.fort.dao.rule;

import java.util.List;
import java.util.Map;

import com.fort.module.resource.Account;

public interface RuleAccountDao {
	
	public List<Account> query(int relId);
	
	public int insert(Map<String,Integer> map);
	
	public int delete(Map<String,Integer> map);
}
