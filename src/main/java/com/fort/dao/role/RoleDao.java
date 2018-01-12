package com.fort.dao.role;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.fort.module.role.Role;

/**
 * 角色DAO接口
 * @author zhangzhigong
 *
 */
public interface RoleDao {
	
	/**
	 * 根据名称查询角色信息
	 * @param name
	 * @return
	 */
	public List<Role> queryByName(String name);
	
	/**
	 * 根据角色ID查询角色信息
	 * @param roleId
	 * @return
	 */
	public Role queryById(int roleId);
	
	/**
	 * 自定义条件分页查询角色信息
	 * @param map
	 * @param rb
	 * @return
	 */
	public List<Role> query(Map<String,Object> map,RowBounds rb);
	
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
}
