package com.fort.module.role;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色实体信息类
 * 
 * @author zhangzhigong
 *
 */
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6563577316392131411L;
	
	//基本信息
	//角色ID
	private int id;
	//角色名称
	private String name;
	//备注信息
	private String memo;
	//创建时间
	private Date createTime;
	//修改时间
	private Date updateTime;
	
	//权限信息
	//用户管理模块
	//新建用户权限
	private int newEmp;
	//修改用户权限
	private int updateEmp;
	//删除用户权限
	private int delEmp;
	//查看用户权限
	private int viewEmp;
	//导入用户
	private int importEmp;
	//导出用户
	private int exportEmp;
	
	//用户分组模块
	//新建用户分组
	private int newEmpGroup;
	//修改用户分组
	private int updateEmpGroup;
	//删除用户分组
	private int delEmpGroup;
	//查看用户分组
	private int viewEmpGroup;
	
	
	//角色管理模块
	//新建角色权限
	private int newRole;
	//修改角色权限
	private int updateRole;
	//删除角色权限
	private int delRole;
	//查看角色权限
	private int viewRole;
	
	
	//资源管理
	//新建资源权限
	private int newRes;
	//修改资源权限
	private int updateRes;
	//删除资源权限
	private int delRes;
	//查看资源权限
	private int viewRes;
	
	public Role() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getNewEmp() {
		return newEmp;
	}

	public void setNewEmp(int newEmp) {
		this.newEmp = newEmp;
	}

	public int getUpdateEmp() {
		return updateEmp;
	}

	public void setUpdateEmp(int updateEmp) {
		this.updateEmp = updateEmp;
	}

	public int getDelEmp() {
		return delEmp;
	}

	public void setDelEmp(int delEmp) {
		this.delEmp = delEmp;
	}

	public int getViewEmp() {
		return viewEmp;
	}

	public void setViewEmp(int viewEmp) {
		this.viewEmp = viewEmp;
	}

	public int getImportEmp() {
		return importEmp;
	}

	public void setImportEmp(int importEmp) {
		this.importEmp = importEmp;
	}

	public int getExportEmp() {
		return exportEmp;
	}

	public void setExportEmp(int exportEmp) {
		this.exportEmp = exportEmp;
	}

	public int getNewRole() {
		return newRole;
	}

	public void setNewRole(int newRole) {
		this.newRole = newRole;
	}

	public int getUpdateRole() {
		return updateRole;
	}

	public void setUpdateRole(int updateRole) {
		this.updateRole = updateRole;
	}

	public int getDelRole() {
		return delRole;
	}

	public void setDelRole(int delRole) {
		this.delRole = delRole;
	}

	public int getViewRole() {
		return viewRole;
	}

	public void setViewRole(int viewRole) {
		this.viewRole = viewRole;
	}

	public int getNewEmpGroup() {
		return newEmpGroup;
	}

	public void setNewEmpGroup(int newEmpGroup) {
		this.newEmpGroup = newEmpGroup;
	}

	public int getUpdateEmpGroup() {
		return updateEmpGroup;
	}

	public void setUpdateEmpGroup(int updateEmpGroup) {
		this.updateEmpGroup = updateEmpGroup;
	}

	public int getDelEmpGroup() {
		return delEmpGroup;
	}

	public void setDelEmpGroup(int delEmpGroup) {
		this.delEmpGroup = delEmpGroup;
	}

	public int getViewEmpGroup() {
		return viewEmpGroup;
	}

	public void setViewEmpGroup(int viewEmpGroup) {
		this.viewEmpGroup = viewEmpGroup;
	}
	
	public int getNewRes() {
		return newRes;
	}

	public void setNewRes(int newRes) {
		this.newRes = newRes;
	}

	public int getUpdateRes() {
		return updateRes;
	}

	public void setUpdateRes(int updateRes) {
		this.updateRes = updateRes;
	}

	public int getDelRes() {
		return delRes;
	}

	public void setDelRes(int delRes) {
		this.delRes = delRes;
	}

	public int getViewRes() {
		return viewRes;
	}

	public void setViewRes(int viewRes) {
		this.viewRes = viewRes;
	}

	public void copy(Role r) {
		this.setId(r.getId());
		this.setName(r.getName());
		this.setMemo(r.getMemo());
		this.setCreateTime(r.getCreateTime());
		this.setUpdateTime(r.getUpdateTime());
		this.setNewEmp(r.getNewEmp());
		this.setUpdateEmp(r.getUpdateEmp());
		this.setDelEmp(r.getDelEmp());
		this.setViewEmp(r.getViewEmp());
		this.setImportEmp(r.getImportEmp());
		this.setExportEmp(r.getExportEmp());
		this.setNewRole(r.getNewRole());
		this.setUpdateRole(r.getUpdateRole());
		this.setDelRole(r.getDelRole());
		this.setViewRole(r.getViewRole());
		this.setNewEmpGroup(r.getNewEmpGroup());
		this.setUpdateEmpGroup(r.getUpdateEmpGroup());
		this.setDelEmpGroup(r.getDelEmpGroup());
		this.setViewEmpGroup(r.getViewEmpGroup());
		this.setNewRes(r.getNewRes());
		this.setUpdateRes(r.getUpdateRes());
		this.setDelRes(r.getDelRes());
		this.setViewRes(r.getViewRes());
	}
}
