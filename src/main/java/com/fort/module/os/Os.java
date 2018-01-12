package com.fort.module.os;

import java.io.Serializable;

public class Os implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2326797585632126751L;

	private int id;
	
	private String name;
	
	private Integer typeId;

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

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
	
}
