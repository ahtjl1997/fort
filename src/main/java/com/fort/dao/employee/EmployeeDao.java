package com.fort.dao.employee;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.fort.module.employee.Employee;

public interface EmployeeDao {
	
	public List<Employee> query(Map<String,Object> map,RowBounds rb);
	
	public int count(Map<String,Object> map);
	
	public List<Employee> queryByLoginName(String loginName);
	
	public Employee queryById(int empId);
	
	public void insert(Employee employee);
	
	public int update(Employee employee);
	
	public int delete(int empId);
	
	public int updateStatus(Map<String,Object> map);
}
