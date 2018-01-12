package com.fort.module.employee;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户分组实体信息类
 * @author zhangzhigong
 *
 */
public class EmployeeGroup implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6025387717532789537L;
	//主键
	private int id;
	//分组名称
	private String name;
	//备注
	private String memo;
	//创建时间
	private Date createTime;
	//修改时间
	private Date updateTime;
	//角色ID
	private int 	roleId = 0;
	//角色名称
	private String  roleName;
	
	public EmployeeGroup() {
		
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

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public void copy(EmployeeGroup g) {
		this.setId(g.getId());
		this.setName(g.getName());
		this.setMemo(g.getMemo());
		this.setCreateTime(g.getCreateTime());
		this.setUpdateTime(g.getUpdateTime());
		this.setRoleId(g.getRoleId());
		this.setRoleName(g.getRoleName());
	}
}
