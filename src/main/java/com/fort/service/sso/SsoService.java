package com.fort.service.sso;

import java.util.Map;

import com.fort.module.rule.RuleInfo;
import com.util.page.SearchResult;

public interface SsoService {
	
	public SearchResult<RuleInfo> query(Map<String,Object> map,int startIndex,int pageSize);
}
