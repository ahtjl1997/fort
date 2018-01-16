package com.fort.dao.sso.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.fort.dao.base.BaseDao;
import com.fort.dao.sso.SsoDao;
import com.fort.module.rule.RuleInfo;

@Repository("ssoDao")
public class SsoDaoImpl extends BaseDao implements SsoDao {

	@Override
	public List<RuleInfo> query(Map<String, Object> map, RowBounds rb) {
		try {
			return getSqlSession().selectList("sso.query", map, rb);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件查询运维设备时连接数据库超时");
		}
	}

	@Override
	public int count(Map<String, Object> map) {
		try {
			return getSqlSession().selectOne("sso.count", map);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("自定义条件统计运维设备总记录数时连接数据库超时");
		}
	}

}
