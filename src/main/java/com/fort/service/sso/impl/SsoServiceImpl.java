package com.fort.service.sso.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fort.dao.rule.RuleAccountDao;
import com.fort.dao.sso.SsoDao;
import com.fort.module.resource.Account;
import com.fort.module.rule.RuleInfo;
import com.fort.service.sso.SsoService;
import com.util.FortObjectUtil;
import com.util.page.Page;
import com.util.page.SearchResult;

@Service("ssoService")
public class SsoServiceImpl implements SsoService {

	@Autowired
	private SsoDao ssoDao;
	
	@Autowired
	private RuleAccountDao ruleAccountDao;
	
	@Override
	public SearchResult<RuleInfo> query(Map<String, Object> map, int startIndex, int pageSize) {
		SearchResult<RuleInfo> sr = new SearchResult<RuleInfo>();
		Page page = new Page(pageSize, startIndex);
		try {
			int totalCount = ssoDao.count(map);
			page.setTotalCount(totalCount);
			if(totalCount > 0) {
				RowBounds rb = new RowBounds(startIndex, pageSize);
				List<RuleInfo> list = ssoDao.query(map, rb);
				
				if(FortObjectUtil.isNotEmpty(list)) {
					for(RuleInfo r : list) {
						List<Account> accList = ruleAccountDao.query(r.getRelId());
						r.setAccountList(accList);
					}
				}
				
				sr.setList(list);
			}
			sr.setPage(page);
			return sr;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件查询运维设备时出现错误，" + e.getMessage());
		}
	}

}
