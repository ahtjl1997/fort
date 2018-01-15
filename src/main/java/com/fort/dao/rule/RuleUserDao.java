package com.fort.dao.rule;

import java.util.List;
import java.util.Map;

import com.fort.module.employee.Employee;

public interface RuleUserDao {
	
	public List<Employee> query(int ruleId);
	
	public int insert(Map<String,Integer> map);
	
	public int delete(Map<String,Integer> map);
}
