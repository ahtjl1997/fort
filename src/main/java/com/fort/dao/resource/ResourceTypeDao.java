package com.fort.dao.resource;

import java.util.List;

import com.fort.module.os.Os;
import com.fort.module.os.ResType;

public interface ResourceTypeDao {
	
	public List<ResType> queryType();
	
	public List<Os> queryOsByTypeId(int typeId);

}
