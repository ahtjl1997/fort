package com.fort.dao.resource;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.fort.module.resource.Resource;

public interface ResourceDao {
	
	public List<Resource> query(Map<String,Object> map,RowBounds rb);
	
	public int count(Map<String,Object> map);
	
	public List<Resource> queryByName(String name);
	
	public Resource queryById(int resId);
	
	public int insert(Resource r);
	
	public int update(Resource r);
	
	public int delete(int resId);
	
	public int updateStatus(Map<String,Object> map);
}
