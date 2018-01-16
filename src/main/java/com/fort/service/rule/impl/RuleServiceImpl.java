package com.fort.service.rule.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fort.dao.rule.RuleDao;
import com.fort.module.rule.Rule;
import com.fort.service.rule.RuleService;
import com.util.FortObjectUtil;
import com.util.page.Page;
import com.util.page.SearchResult;

@Service("ruleService")
public class RuleServiceImpl implements RuleService {

	@Autowired
	private RuleDao ruleDao;
	
	@Override
	public SearchResult<Rule> query(Map<String, Object> map, int startIndex, int pageSize) {
		SearchResult<Rule> sr = new SearchResult<Rule>();
		try {
			int totalCount = ruleDao.count(map);
			Page page = new Page(pageSize, startIndex);
			page.setTotalCount(totalCount);
			sr.setPage(page);
			if(totalCount > 0) {
				RowBounds rb = new RowBounds(startIndex, pageSize);
				List<Rule> list = ruleDao.query(map, rb);
				sr.setList(list);
			}
			return sr;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件查询授权信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public Rule queryById(int id) {
		try {
			return ruleDao.queryById(id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("根据授权ID查询授权信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public Rule queryByName(String name) {
		Rule rule = null;
		try {
			List<Rule> list = ruleDao.queryByName(name);
			if(FortObjectUtil.isNotEmpty(list)) {
				rule = list.get(0);
			}
			return rule;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("根据授权名称查询授权信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public int insert(Rule r) {
		int id = 0;
		try {
			int row = ruleDao.insert(r);
			if(row > 0) {
				id = r.getId();
			}
			return id;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("新建授权信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public int update(Rule r) {
		try {
			return ruleDao.update(r);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改授权信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public int updateStatus(int id, int status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("status", status);
		map.put("updateTime", new Date());
		try {
			return ruleDao.updateStatus(map);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("新建授权状态信息时出现错误，" + e.getMessage());
		}
	}

	@Transactional(transactionManager="transactionManager")
	@Override
	public void updateStatus(int[] idArr, int status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("updateTime", new Date());
		Rule rule = null;
		try {
			for(int id : idArr) {
				if(id == 0) {
					continue;
				}
				rule = ruleDao.queryById(id);
				if(FortObjectUtil.isEmpty(rule)) {
					continue;
				}
				map.put("id", id);
				ruleDao.updateStatus(map);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("新建授权状态信息时出现错误，" + e.getMessage());
		}

	}

	@Override
	public int delete(int id) {
		try {
			return ruleDao.delete(id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("删除授权信息时出现错误，" + e.getMessage());
		}
	}

	@Transactional(transactionManager="transactionManager")
	@Override
	public void delete(int[] idArr) {
		Rule rule = null;
		try {
			for(int id : idArr) {
				if(id == 0) {
					continue;
				}
				rule = ruleDao.queryById(id);
				if(FortObjectUtil.isEmpty(rule)) {
					continue;
				}
				ruleDao.delete(id);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("删除授权信息时出现错误，" + e.getMessage());
		}

	}

}
