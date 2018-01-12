package com.fort.service.employee.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fort.dao.employee.EmployeeGroupDao;
import com.fort.module.employee.EmployeeGroup;
import com.fort.service.employee.EmployeeGroupService;
import com.util.FortObjectUtil;
import com.util.page.Page;
import com.util.page.SearchResult;

@Service("employeeGroupService")
public class EmployeeGroupServiceImpl implements EmployeeGroupService {

	@Autowired
	private EmployeeGroupDao employeeGroupDao;
	
	@Override
	public SearchResult<EmployeeGroup> query(Map<String, Object> map, int startIndex, int pageSize) {
		SearchResult<EmployeeGroup> sr = new SearchResult<EmployeeGroup>();
		try {
			int totalCount = employeeGroupDao.count(map);
			Page page = new Page(pageSize, startIndex);
			page.setTotalCount(totalCount);
			if(totalCount > 0) {
				RowBounds rb = new RowBounds(startIndex, pageSize);
				List<EmployeeGroup> list = employeeGroupDao.query(map, rb);
				sr.setList(list);
			}
			sr.setPage(page);
			return sr;
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("自定义分页查询用户分组时出现错误，" + e.getMessage());
		}
	}

	@Override
	public List<EmployeeGroup> query(Map<String, Object> map) {
		try {
			return employeeGroupDao.query(map);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("自定义查询用户分组时出现错误，" + e.getMessage());
		}
	}

	@Override
	public int count(Map<String, Object> map) {
		try {
			return employeeGroupDao.count(map);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件统计用户总记录数时出现错误，" + e.getMessage());
		}
	}

	@Override
	public EmployeeGroup queryByName(String name) {
		try {
			List<EmployeeGroup> list = employeeGroupDao.queryByName(name);
			EmployeeGroup eg = null;
			if(FortObjectUtil.isNotEmpty(list)) {
				eg = list.get(0);
			}
			return eg;
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("根据分组名称查询分组信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public EmployeeGroup queryById(int groupId) {
		try {
			return employeeGroupDao.queryById(groupId);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("根据分组ID查询分组信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public void insert(EmployeeGroup group) {
		try {
			employeeGroupDao.insert(group);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("新建用户分组信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public int update(EmployeeGroup group) {
		try {
			return employeeGroupDao.update(group);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("修改用户分组信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public int delete(int groupId) {
		try {
			return employeeGroupDao.delete(groupId);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("删除用户分组信息时出现错误，" + e.getMessage());
		}
	}
	
	@Transactional(transactionManager="transactionManager")
	@Override
	public void delete(int[] idArr) {
		try {
			for(int id : idArr) {
				employeeGroupDao.delete(id);
			}
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("删除用户分组信息时出现错误，" + e.getMessage());
		}
	}
}
