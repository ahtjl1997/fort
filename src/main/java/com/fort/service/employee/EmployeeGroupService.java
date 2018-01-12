package com.fort.service.employee;

import java.util.List;
import java.util.Map;

import com.fort.module.employee.EmployeeGroup;
import com.util.page.SearchResult;

public interface EmployeeGroupService {
	
	/**
	 * 自定义条件分页查询用户分组信息
	 * @param map
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public SearchResult<EmployeeGroup> query(Map<String,Object> map,int startIndex,int pageSize);
	
	/**
	 * 自定义条件查询用户分组信息
	 * @param map
	 * @param rb
	 * @return
	 */
	public List<EmployeeGroup> query(Map<String,Object> map);
	
	/**
	 * 自定义条件统计用户总记录数
	 * @param map
	 * @return
	 */
	public int count(Map<String,Object> map);
	
	/**
	 * 根据分组名称查询分组信息
	 * @param name
	 * @return
	 */
	public EmployeeGroup queryByName(String name);
	
	/**
	 * 根据分组ID查询分组信息
	 * @param groupId
	 * @return
	 */
	public EmployeeGroup queryById(int groupId);
	
	/**
	 * 新建用户分组信息
	 * @param group
	 */
	public void insert(EmployeeGroup group);
	
	/**
	 * 修改用户分组信息
	 * @param group
	 * @return
	 */
	public int update(EmployeeGroup group);
	
	/**
	 * 删除用户分组信息
	 * @param groupId
	 * @return
	 */
	public int delete(int groupId);
	
	public void delete(int[] idArr);
}
