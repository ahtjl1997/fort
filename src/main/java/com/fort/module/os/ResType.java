package com.fort.module.os;

import java.io.Serializable;

public class ResType implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4007216509756160535L;

	private int id;
	
	private String name;

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
	
	
}
