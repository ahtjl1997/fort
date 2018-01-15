package com.fort.dao.rule;

import java.util.List;
import java.util.Map;

import com.fort.module.rule.RuleInfo;

public interface RuleResourceDao {
	
	public List<RuleInfo> query(int ruleId);
	
	public int insert(RuleInfo ruleInfo);
	
	public int delete(Map<String,Integer> map);
	
	public int update(RuleInfo ruleInfo);
}
