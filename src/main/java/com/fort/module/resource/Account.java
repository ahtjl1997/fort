package com.fort.module.resource;

import java.io.Serializable;

public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9127090109630238704L;

	private int id;
	
	private String name;
	
	private String password;
	
	private int type;
	
	private Integer resId;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getResId() {
		return resId;
	}

	public void setResId(int resId) {
		this.resId = resId;
	}
	
	public void copy(Account acc) {
		this.setId(acc.getId());
		this.setName(acc.getName());
		this.setPassword(acc.getPassword());
		this.setType(acc.getType());
		this.setResId(acc.getResId());
	}
}
