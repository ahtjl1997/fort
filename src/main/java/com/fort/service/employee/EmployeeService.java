package com.fort.service.employee;

import java.util.Map;

import com.fort.module.employee.Employee;
import com.util.page.SearchResult;

/**
 * 用户Service接口
 * @author zhangzhigong
 *
 */
public interface EmployeeService {
	
	/**
	 * 自定义条件查询用户信息
	 * @param map
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public SearchResult<Employee> query(Map<String,Object> map,int startIndex,int pageSize);
	
	/**
	 * 自定义条件统计用户总记录数
	 * @param map
	 * @return
	 */
	public int count(Map<String,Object> map);
	
	/**
	 * 根据用户登录名查询用户信息
	 * @param loginName
	 * @return
	 */
	public Employee queryByLoginName(String loginName);
	
	/**
	 * 根据用户ID查询用户信息
	 * @param empId
	 * @return
	 */
	public Employee queryById(int empId);
	
	/**
	 * 新建用户信息
	 * @param employee
	 */
	public void insert(Employee employee);
	
	/**
	 * 修改用户信息
	 * @param employee
	 * @return
	 */
	public int update(Employee employee);
	
	/**
	 * 删除用户信息
	 * @param empId
	 * @return
	 */
	public int delete(int empId);
	
	public void delete(int[] idArr);
	
	public int updateStatus(int id,int status);
	
	public void updateStatus(int[] idArr,int status);
}
