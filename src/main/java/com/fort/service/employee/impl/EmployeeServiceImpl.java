package com.fort.service.employee.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fort.dao.employee.EmployeeDao;
import com.fort.module.employee.Employee;
import com.fort.service.employee.EmployeeService;
import com.util.FortObjectUtil;
import com.util.page.Page;
import com.util.page.SearchResult;

/**
 * 用户Service接口实现类
 * @author zhangzhigong
 *
 */
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public SearchResult<Employee> query(Map<String, Object> map, int startIndex, int pageSize) {
		SearchResult<Employee> sr = new SearchResult<Employee>();
		try {
			int totalCount = employeeDao.count(map);
			Page page = new Page(pageSize, startIndex);
			page.setTotalCount(totalCount);
			if(totalCount > 0) {
				RowBounds rb = new RowBounds(startIndex, pageSize);
				List<Employee> list = employeeDao.query(map, rb);
				sr.setList(list);
			}
			sr.setPage(page);
			return sr;
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件查询用户信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public int count(Map<String, Object> map) {
		try {
			return employeeDao.count(map);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件统计用户总记录数时出现错误，" + e.getMessage());
		}
	}

	@Override
	public Employee queryByLoginName(String loginName) {
		Employee employee = null;
		try {
			List<Employee> list = employeeDao.queryByLoginName(loginName);
			if(FortObjectUtil.isNotEmpty(list)) {
				employee= list.get(0);
			}
			return employee;
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("根据用户登录名查询用户信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public Employee queryById(int empId) {
		try {
			return employeeDao.queryById(empId);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("根据用户ID查询用户信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public void insert(Employee employee) {
		try {
			employeeDao.insert(employee);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("新建用户信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public int update(Employee employee) {
		try {
			return employeeDao.update(employee);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("修改用户信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public int delete(int empId) {
		try {
			return employeeDao.delete(empId);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("删除用户信息时出现错误，" + e.getMessage());
		}
	}
	
	@Transactional(transactionManager="transactionManager")
	@Override
	public void delete(int[] idArr) {
		try {
			Employee emp = null;
			for(int id : idArr) {
				if(id == 0) {
					continue;
				}
				emp = employeeDao.queryById(id);
				if(FortObjectUtil.isEmpty(emp) || "admin".equals(emp.getUsername())) {
					continue;
				}
				employeeDao.delete(id);
			}
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("删除用户信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public int updateStatus(int id,int status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("status", status);
		try {
			return employeeDao.updateStatus(map);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("修改用户状态时出现错误，" + e.getMessage());
		}
	}
	
	@Transactional(transactionManager="transactionManager")
	@Override
	public void updateStatus(int[] idArr,int status) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Employee emp = null;
			for(int id : idArr) {
				if(id == 0) {
					continue;
				}
				emp = employeeDao.queryById(id);
				if(FortObjectUtil.isEmpty(emp) || "admin".equals(emp.getUsername())) {
					continue;
				}
				map.put("id", id);
				map.put("status", status);
				employeeDao.updateStatus(map);
				map.clear();
			}
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("修改用户状态时出现错误，" + e.getMessage());
		}
	}
}
