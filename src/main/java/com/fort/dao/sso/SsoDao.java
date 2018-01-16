package com.fort.dao.sso;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.fort.module.rule.RuleInfo;

public interface SsoDao {
	
	public List<RuleInfo> query(Map<String,Object> map,RowBounds rb);
	
	public int count(Map<String,Object> map);
	
}
