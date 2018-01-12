package com.fort.dao.resource.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.fort.dao.base.BaseDao;
import com.fort.dao.resource.ResourceTypeDao;
import com.fort.module.os.Os;
import com.fort.module.os.ResType;

@Repository("resourceTypeDao")
public class ResourceTypeDaoImpl extends BaseDao implements ResourceTypeDao {

	@Override
	public List<ResType> queryType() {
		try {
			return getSqlSession().selectList("os.queryType");
		}catch (Exception e) {
			throw new RuntimeException("查询所有资源类型时连接数据库超时");
		}
	}

	@Override
	public List<Os> queryOsByTypeId(int typeId) {
		try {
			return getSqlSession().selectList("os.queryOsByTypeId", typeId);
		}catch (Exception e) {
			throw new RuntimeException("根据资源类型查询操作系统时连接数据库超时");
		}
	}

}
