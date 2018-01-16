package com.fort.module.employee;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fort.module.role.Role;

/**
 * 用户实体信息类
 * @author zhangzhigong
 *
 */
public class Employee implements UserDetails, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7296691154787268631L;

	private Collection<? extends GrantedAuthority> authorities;
	//用户ID
	private int 	id;
	//用户名
	private String 	username;
	//密码
	private String 	password;
	//姓名
	private String 	name;
	//备注
	private String	memo;
	//手机号码
	private String 	mobile;
	//固定电话
	private String 	tel;
	//邮箱地址
	private String 	email;
	//组织
	private String 	organization;
	//下次登录是否修改密码，1：是，0：否
	private int 	updatePwd;
	//登录认证方式，0：密码登录
	private int 	authType = 0;
	//用户状态，0:锁定，1：激活
	private int 	status = 0;
	//工作日起始时间
	private int 	startWeek = 0;
	//工作日结束时间
	private int 	endWeek = 4;
	//密码期限
	private Date 	pwdTimeOut;
	//创建时间
	private Date 	createTime;
	//更新时间
	private Date 	updateTime;
	//角色ID
	private int 	roleId = 0;
	//角色名称
	private String  roleName;
	//分组ID
	private int 	groupId = 0;
	//分组名称
	private String  groupName;
	
	//角色权限信息
	private Role    role;
	
	public Employee() {
		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public int getUpdatePwd() {
		return updatePwd;
	}

	public void setUpdatePwd(int updatePwd) {
		this.updatePwd = updatePwd;
	}

	public int getAuthType() {
		return authType;
	}

	public void setAuthType(int authType) {
		this.authType = authType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStartWeek() {
		return startWeek;
	}

	public void setStartWeek(int startWeek) {
		this.startWeek = startWeek;
	}

	public int getEndWeek() {
		return endWeek;
	}

	public void setEndWeek(int endWeek) {
		this.endWeek = endWeek;
	}

	public Date getPwdTimeOut() {
		return pwdTimeOut;
	}

	public void setPwdTimeOut(Date pwdTimeOut) {
		this.pwdTimeOut = pwdTimeOut;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * 复制信息
	 * @param emp
	 */
	public void copy(Employee emp) {
		this.setId(emp.getId());
		this.setName(emp.getName());
		this.setAuthType(emp.getAuthType());
		this.setCreateTime(emp.getCreateTime());
		this.setEmail(emp.getEmail());
		this.setEndWeek(emp.getEndWeek());
		this.setPassword(emp.getPassword());
		this.setMemo(emp.getMemo());
		this.setMobile(emp.getMemo());
		this.setOrganization(emp.getOrganization());
		this.setPwdTimeOut(emp.getPwdTimeOut());
		this.setUpdateTime(emp.getUpdateTime());
		this.setStartWeek(emp.getStartWeek());
		this.setStatus(emp.getStatus());
		this.setTel(emp.getTel());
		this.setUpdatePwd(emp.getUpdatePwd());
		this.setUsername(emp.getUsername());
		this.setRoleId(emp.getRoleId());
		this.setGroupId(emp.getGroupId());
	};
}
