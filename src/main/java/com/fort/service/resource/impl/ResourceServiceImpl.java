package com.fort.service.resource.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.validator.GenericValidator;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fort.dao.resource.AccountDao;
import com.fort.dao.resource.ResourceDao;
import com.fort.dao.resource.ResourceTypeDao;
import com.fort.module.os.Os;
import com.fort.module.os.ResType;
import com.fort.module.resource.Account;
import com.fort.module.resource.Resource;
import com.fort.service.resource.ResourceService;
import com.util.FortObjectUtil;
import com.util.page.Page;
import com.util.page.SearchResult;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private ResourceTypeDao resourceTypeDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Override
	public SearchResult<Resource> query(Map<String, Object> map, int startIndex, int pageSize) {
		SearchResult<Resource> sr = new SearchResult<Resource>();
		Page page = new Page(pageSize, startIndex);
		try {
			int totalCount = resourceDao.count(map);
			if(totalCount > 0) {
				page.setTotalCount(totalCount);
				RowBounds rb = new RowBounds(startIndex, pageSize);
				List<Resource> list = resourceDao.query(map, rb);
				sr.setList(list);
			}
			sr.setPage(page);
			return sr;
		}catch (Exception e) {
			throw new RuntimeException("自定义条件查询资源信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public Resource queryByName(String name) {
		try {
			List<Resource> list = resourceDao.queryByName(name);
			if(FortObjectUtil.isEmpty(list)) {
				return null;
			}
			Resource r = list.get(0);
			List<Account> accList = accountDao.queryByResId(r.getId());
			r.setAccountList(accList);
			return r;
		}catch (Exception e) {
			throw new RuntimeException("根据资源名称查询资源信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public Resource queryById(int resId) {
		try {
			Resource r = resourceDao.queryById(resId);
			if(r == null) {
				return null;
			}
			List<Account> accList = accountDao.queryByResId(r.getId());
			r.setAccountList(accList);
			return r;
		}catch (Exception e) {
			throw new RuntimeException("根据资源ID查询资源信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public int insert(Resource r) {
		int resId = 0;
		try {
			int row = resourceDao.insert(r);
			if(row == 0) {
				return 0;
			}
			resId = r.getId();
			
			Collection<Account> accList = r.getAccountList();
			if(FortObjectUtil.isNotEmpty(accList)) {
				for(Account acc : accList) {
					acc.setResId(resId);
					accountDao.insert(acc);
				}
			}
			return resId;
		}catch (Exception e) {
			throw new RuntimeException("添加资源信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public int update(Resource r) {
		Collection<Account> accList = r.getAccountList();
		Map<String,Account> accMap = new HashMap<String,Account>();
		if(FortObjectUtil.isNotEmpty(accList)) {
			for(Account acc : accList) {
				String name = acc.getName();
				if(accMap.get(name) == null) {
					accMap.put(name, acc);
				}
			}
		}
		try {
			Resource res = queryById(r.getId());
			Collection<Account> oldAccList = res.getAccountList();
			List<Integer> delIdList = new ArrayList<Integer>();
			if(FortObjectUtil.isNotEmpty(oldAccList)) {
				for(Account acc : oldAccList) {
					String name = acc.getName();
					Account newAcc = accMap.get(name);
					if(newAcc == null) {
						delIdList.add(acc.getId());
					}else {
						newAcc.setId(acc.getId());
						if(GenericValidator.isBlankOrNull(newAcc.getPassword())) {
							newAcc.setPassword(acc.getPassword());
						}
					}
				}
			}
			for(Entry<String,Account> entry : accMap.entrySet()) {
				Account acc = entry.getValue();
				acc.setResId(r.getId());
				if(acc.getId() > 0) {
					accountDao.update(acc);
				}else {
					accountDao.insert(acc);
				}
			}
			for(Integer id : delIdList) {
				accountDao.delete(id);
			}
			return resourceDao.update(r);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改资源信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public int delete(int resId) {
		try {
			return resourceDao.delete(resId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("删除资源信息时出现错误，" + e.getMessage());
		}
	}
	
	@Transactional(transactionManager="transactionManager")
	@Override
	public void delete(int[] idArr) {
		Resource r = null;
		try {
			for(int id : idArr) {
				if(id == 0) {
					continue;
				}
				r = resourceDao.queryById(id);
				if(r == null) {
					continue;
				}
				resourceDao.delete(id);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("删除资源信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public int updateStatus(int resId, int status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", resId);
		map.put("status", status);
		map.put("updateTime", new Date());
		try {
			return resourceDao.updateStatus(map);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改资源状态时出现错误，" + e.getMessage());
		}
	}

	@Override
	public void updateStatus(int[] idArr, int status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("updateTime", new Date());
		try {
			for(int id : idArr) {
				if(id == 0) {
					continue;
				}
				map.put("id", id);
				resourceDao.updateStatus(map);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改资源状态时出现错误，" + e.getMessage());
		}
	}

	@Override
	public List<Os> queryOsByTypeId(int typeId) {
		try {
			return resourceTypeDao.queryOsByTypeId(typeId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询资源操作系统信息时出现错误" + e.getMessage());
		}
	}

	@Override
	public List<ResType> queryResType() {
		try {
			return resourceTypeDao.queryType();
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询资源类型时出现错误" + e.getMessage());
		}
	}
	
	@Override
	public List<Account> queryAccountByResId(int resId){
		try {
			return accountDao.queryByResId(resId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询资源类型时出现错误" + e.getMessage());
		}
	}
	
	@Override
	public SearchResult<Account> queryAccount(Map<String,Object> map,int startIndex,int pageSize){
		SearchResult<Account> sr = new SearchResult<Account>();
		Page page = new Page(pageSize, startIndex);
		try {
			int totalCount = accountDao.count(map);
			page.setTotalCount(totalCount);
			if(totalCount > 0) {
				RowBounds rb = new RowBounds(startIndex, pageSize);
				List<Account> list = accountDao.query(map, rb);
				sr.setList(list);
			}
			sr.setPage(page);
			return sr;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件查询账号信息时出现错误，" + e.getMessage());
		}
	}

}
