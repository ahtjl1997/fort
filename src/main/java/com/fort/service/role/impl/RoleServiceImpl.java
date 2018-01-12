package com.fort.service.role.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fort.dao.role.RoleDao;
import com.fort.module.role.Role;
import com.fort.service.role.RoleService;
import com.util.FortObjectUtil;
import com.util.page.Page;
import com.util.page.SearchResult;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	@Override
	public Role queryByName(String name) {
		Role role = null;
		try {
			List<Role> roleList = roleDao.queryByName(name);
			if(FortObjectUtil.isNotEmpty(roleList)) {
				role = roleList.get(0);
			}
			return role;
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("根据角色名称查询角色信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public Role queryById(int roleId) {
		try {
			return roleDao.queryById(roleId);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("根据角色ID查询角色信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public SearchResult<Role> query(Map<String, Object> map, int startIndex, int pageSize) {
		SearchResult<Role> sr = new SearchResult<Role>();
		try {
			int totalCount = roleDao.count(map);
			Page page = new Page(pageSize, startIndex);
			page.setTotalCount(totalCount);
			if(totalCount > 0) {
				RowBounds rb = new RowBounds(startIndex, pageSize);
				List<Role> list = roleDao.query(map, rb);
				sr.setList(list);
			}
			sr.setPage(page);
			return sr;
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件分页查询角色信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public int count(Map<String, Object> map) {
		try {
			return roleDao.count(map);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件统计角色总记录数时出现错误，" + e.getMessage());
		}
	}
	
	@Override
	public List<Role> query(Map<String,Object> map){
		try {
			return roleDao.query(map);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件查询角色信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public void insert(Role role) {
		try {
			roleDao.insert(role);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("新建角色信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public int update(Role role) {
		try {
			return roleDao.update(role);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("修改角色信息时出现错误，" + e.getMessage());
		}
	}

	@Override
	public int delete(int roleId) {
		try {
			return roleDao.delete(roleId);
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("删除角色信息时出现错误，" + e.getMessage());
		}
	}

	@Transactional(transactionManager="transactionManager")
	@Override
	public void delete(int[] idArr) {
		try {
			Role r = null;
			for(int id : idArr) {
				if(id == 0) {
					continue;
				}
				r = roleDao.queryById(id);
				if(FortObjectUtil.isEmpty(r) || "系统管理员".equals(r.getName())) {
					continue;
				}
				roleDao.delete(id);
			}
		}catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("删除角色信息时出现错误，" + e.getMessage());
		}
	}
}
