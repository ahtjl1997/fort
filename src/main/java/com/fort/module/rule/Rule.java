package com.fort.module.rule;

import java.io.Serializable;
import java.util.Date;

public class Rule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3468983633919983843L;

	private int id;
	
	private String name;
	
	private String memo;
	
	private int status;
	
	private Date createTime;
	
	private Date updateTime;

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
	
	public void copy(Rule r) {
		this.setId(r.getId());
		this.setName(r.getName());
		this.setMemo(r.getMemo());
		this.setStatus(r.getStatus());
		this.setCreateTime(r.getCreateTime());
		this.setUpdateTime(r.getUpdateTime());
	}
}
