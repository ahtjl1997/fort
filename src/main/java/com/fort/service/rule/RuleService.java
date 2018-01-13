package com.fort.service.rule;

import java.util.Map;

import com.fort.module.rule.Rule;
import com.util.page.SearchResult;

public interface RuleService {
	
	public SearchResult<Rule> query(Map<String,Object> map,int startIndex,int pageSize);
	
	public Rule queryById(int id);
	
	public Rule queryByName(String name);
	
	public int insert(Rule r);
	
	public int update(Rule r);
	
	public int updateStatus(int id,int status);
	
	public void updateStatus(int[] idArr,int status);
	
	public int delete(int id);
	
	public void delete(int[] idArr);
}
