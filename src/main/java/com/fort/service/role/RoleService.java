package com.fort.service.role;

import java.util.List;
import java.util.Map;

import com.fort.module.role.Role;
import com.util.page.SearchResult;

/**
 * 角色Service接口
 * @author zhangzhigong
 *
 */
public interface RoleService {
	
	/**
	 * 根据名称查询角色信息
	 * @param name
	 * @return
	 */
	public Role queryByName(String name);
	
	/**
	 * 根据角色ID查询角色信息
	 * @param roleId
	 * @return
	 */
	public Role queryById(int roleId);
	
	/**
	 * 自定义条件查询角色信息
	 * @param map
	 * @param rb
	 * @return
	 */
	public SearchResult<Role> query(Map<String,Object> map,int startIndex,int pageSize);
	
	/**
	 * 自定义条件统计角色总记录数
	 * @param map
	 * @return
	 */
	public int count(Map<String,Object> map);
	
	/**
	 * 自定义条件查询角色信息
	 * @param map
	 * @return
	 */
	public List<Role> query(Map<String,Object> map);
	
	/**
	 * 新建角色信息
	 * @param role
	 */
	public void insert(Role role);
	
	/**
	 * 修改角色信息
	 * @param role
	 * @return
	 */
	public int update(Role role);
	
	/**
	 * 删除角色信息
	 * @param roleId
	 * @return
	 */
	public int delete(int roleId);
	
	public void delete(int[] idArr);
}
