package com.fort.service.resource;

import java.util.List;
import java.util.Map;

import com.fort.module.os.Os;
import com.fort.module.os.ResType;
import com.fort.module.resource.Account;
import com.fort.module.resource.Resource;
import com.util.page.SearchResult;

public interface ResourceService {
	
	public SearchResult<Resource> query(Map<String,Object> map,int startIndex,int pageSize);
	
	public Resource queryByName(String name);
	
	public Resource queryById(int resId);
	
	public int insert(Resource r);
	
	public int update(Resource r);
	
	public int delete(int resId);
	
	public void delete(int[] idArr);
	
	public int updateStatus(int resId,int status);
	
	public void updateStatus(int[] idArr,int status);
	
	public List<Os> queryOsByTypeId(int typeId);
	
	public List<ResType> queryResType();
	
	public List<Account> queryAccountByResId(int resId);
}
