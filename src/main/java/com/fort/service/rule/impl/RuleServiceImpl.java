package com.fort.service.rule.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fort.dao.resource.ResourceDao;
import com.fort.dao.rule.RuleAccountDao;
import com.fort.dao.rule.RuleDao;
import com.fort.dao.rule.RuleResourceDao;
import com.fort.dao.rule.RuleUserDao;
import com.fort.module.employee.Employee;
import com.fort.module.resource.Account;
import com.fort.module.resource.Resource;
import com.fort.module.rule.Rule;
import com.fort.module.rule.RuleInfo;
import com.fort.service.rule.RuleService;
import com.util.FortObjectUtil;
import com.util.page.Page;
import com.util.page.SearchResult;

@Service("ruleService")
public class RuleServiceImpl implements RuleService {

	@Autowired
	private RuleDao ruleDao;
	
	@Autowired
	private RuleResourceDao ruleResourceDao;
	
	@Autowired
	private RuleAccountDao ruleAccountDao;
	
	@Autowired
	private RuleUserDao ruleUserDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
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

	@Override
	public List<RuleInfo> queryResource(int ruleId) {
		try {
			List<RuleInfo> resList = ruleResourceDao.query(ruleId);
			if(FortObjectUtil.isNotEmpty(resList)) {
				for(RuleInfo r : resList) {
					List<Account> accList = ruleAccountDao.query(r.getRelId());
					r.setAccountList(accList);
				}
			}
			return resList;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("根据授权ID查询授权资源时出现错误，" + e.getMessage());
		}
	}

	@Override
	public List<Employee> queryUser(int ruleId) {
		try {
			return ruleUserDao.query(ruleId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("根据授权ID查询授权用户时出现错误，" + e.getMessage());
		}
	}

	@Override
	public void insertResource(int ruleId, List<RuleInfo> resList) {
		try {
			List<RuleInfo> oldResList = ruleResourceDao.query(ruleId);
			if(FortObjectUtil.isNotEmpty(resList)) {
				Resource res;
				Collection<Account> accList;
				int relId;
				Map<String,Integer> map = new HashMap<String,Integer>();
				if(FortObjectUtil.isEmpty(oldResList)) {
					for(RuleInfo r : resList) {
						r.setRuleId(ruleId);
						res = resourceDao.queryById(r.getId());
						if(FortObjectUtil.isEmpty(res)) {
							continue;
						}
						ruleResourceDao.insert(r);
						relId = r.getRelId();
						if(relId == 0) {
							continue;
						}
						map.put("relId", relId);
						accList = r.getAccountList();
						if(FortObjectUtil.isNotEmpty(accList)) {
							for(Account acc : accList) {
								map.put("accId", acc.getId());
								ruleAccountDao.insert(map);
							}
						}
					}
				}else {
					List<Account> oldAccList;
					Collection<Account> newAccList;
					//更新
					for(RuleInfo old : oldResList) {
						old.setRuleId(ruleId);
						for(RuleInfo r : resList) {
							if(old.getId() == r.getId()) {
								old.copy(r);
								ruleResourceDao.update(old);
								oldAccList = ruleAccountDao.query(old.getRelId());
								newAccList = r.getAccountList();
								//删除
								for(Account oa : oldAccList) {
									boolean flag = true;
									for(Account na : newAccList) {
										if(oa.getId() == na.getId()) {
											flag = false;
											break;
										}
									}
									if(flag) {
										Map<String,Integer> accMap = new HashMap<String,Integer>();
										accMap.put("relId", old.getRelId());
										accMap.put("accId", oa.getId());
										ruleAccountDao.delete(accMap);
									}
								}
								//新建
								for(Account na : newAccList) {
									boolean flag = true;
									for(Account oa : oldAccList) {
										if(oa.getId() == na.getId()) {
											flag = false;
											break;
										}
									}
									if(flag) {
										Map<String,Integer> accMap = new HashMap<String,Integer>();
										accMap.put("relId", old.getRelId());
										accMap.put("accId", na.getId());
										ruleAccountDao.insert(accMap);
									}
								}
							}
							break;
						}
					}
					//删除
					for(RuleInfo old : oldResList) {
						boolean flag = true;
						for(RuleInfo r : resList) {
							if(r.getId() == old.getId()) {
								flag = false;
							}
						}
						if(flag) {
							Map<String,Integer> resMap = new HashMap<String,Integer>();
							resMap.put("ruleId", ruleId);
							resMap.put("resId", old.getId());
							ruleResourceDao.delete(resMap);
						}
					}
					//新建
					for(RuleInfo r : resList) {
						r.setRuleId(ruleId);
						boolean flag = true;
						for(RuleInfo old : oldResList) {
							if(r.getId() == old.getId()) {
								flag = false;
							}
						}
						if(flag) {
							ruleResourceDao.insert(r);
							relId = r.getRelId();
							if(relId == 0) {
								continue;
							}
							map.put("relId", relId);
							accList = r.getAccountList();
							if(FortObjectUtil.isNotEmpty(accList)) {
								for(Account acc : accList) {
									map.put("accId", acc.getId());
									ruleAccountDao.insert(map);
								}
							}
						}
					}
				}
			}else if(FortObjectUtil.isNotEmpty(oldResList)){
				Map<String,Integer> map = new HashMap<String,Integer>();
				map.put("ruleId", ruleId);
				for(RuleInfo r : oldResList) {
					map.put("resId", r.getId());
					ruleResourceDao.delete(map);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("根据授权ID更新资源信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public void insertUser(int ruleId, int[] empIdArr) {
		try {
			List<Employee> empList = ruleUserDao.query(ruleId);
			Map<String,Integer> map = new HashMap<String,Integer>();
			map.put("ruleId", ruleId);
			if(empIdArr == null || empIdArr.length == 0) {
				if(FortObjectUtil.isNotEmpty(empList)) {
					for(Employee e : empList) {
						map.put("empId", e.getId());
						ruleUserDao.delete(map);
					}
				}
			}else {
				if(FortObjectUtil.isEmpty(empList)) {
					for(int empId : empIdArr) {
						map.put("empId", empId);
						ruleUserDao.insert(map);
					}
				}else {
					for(int empId : empIdArr) {
						boolean flag = true;
						for(Employee e : empList) {
							int id = e.getId();
							if(empId == id) {
								flag = false;
								break;
							}
						}
						if(flag) {
							map.put("empId", empId);
							ruleUserDao.insert(map);
						}
					}
					for(Employee e : empList) {
						boolean flag = true;
						for(int empId : empIdArr) {
							int id = e.getId();
							if(empId == id) {
								flag = false;
								break;
							}
						}
						if(flag) {
							map.put("empId", e.getId());
							ruleUserDao.delete(map);
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("根据授权ID更新用户信息时出现错误，" + e.getMessage());
		}
	}

}
