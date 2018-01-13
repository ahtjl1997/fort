package com.fort.dao.rule;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.fort.module.rule.Rule;

public interface RuleDao {
	
	public List<Rule> query(Map<String,Object> map,RowBounds rb);
	
	public int count(Map<String,Object> map);
	
	public Rule queryById(int id);
	
	public List<Rule> queryByName(String name);
	
	public int insert(Rule r);
	
	public int update(Rule r);
	
	public int updateStatus(Map<String,Object> map);
	
	public int delete(int id);
}
