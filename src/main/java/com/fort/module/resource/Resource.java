package com.fort.module.resource;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

public class Resource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7704846413778145717L;
	
	private int id;
	
	private String name;

	private String ip;
	
	private Integer typeId;
	
	private String typeName;
	
	private Integer osId;
	
	private String osName;
	
	private int useSsh;
	
	private int sshPort;
	
	private int useSftp;
	
	private int sftpPort;
	
	private int useRdp;
	
	private int rdpPort;
	
	private int status;
	
	private Date createTime;
	
	private Date updateTime;
	
	private Collection<Account> accountList;

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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getOsId() {
		return osId;
	}

	public void setOsId(Integer osId) {
		this.osId = osId;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public int getUseSsh() {
		return useSsh;
	}

	public void setUseSsh(int useSsh) {
		this.useSsh = useSsh;
	}

	public int getSshPort() {
		return sshPort;
	}

	public void setSshPort(int sshPort) {
		this.sshPort = sshPort;
	}

	public int getUseSftp() {
		return useSftp;
	}

	public void setUseSftp(int useSftp) {
		this.useSftp = useSftp;
	}

	public int getSftpPort() {
		return sftpPort;
	}

	public void setSftpPort(int sftpPort) {
		this.sftpPort = sftpPort;
	}

	public int getUseRdp() {
		return useRdp;
	}

	public void setUseRdp(int useRdp) {
		this.useRdp = useRdp;
	}

	public int getRdpPort() {
		return rdpPort;
	}

	public void setRdpPort(int rdpPort) {
		this.rdpPort = rdpPort;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public Collection<Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(Collection<Account> accountList) {
		this.accountList = accountList;
	}
	
	public void copy(Resource r) {
		this.setId(r.getId());
		this.setName(r.getName());
		this.setIp(r.getIp());
		this.setTypeId(r.getTypeId());
		this.setTypeName(r.getTypeName());
		this.setOsId(r.getOsId());
		this.setOsName(r.getOsName());
		this.setUseSsh(r.getUseSsh());
		this.setSshPort(r.getSshPort());
		this.setUseSftp(r.getUseSftp());
		this.setSftpPort(r.getSftpPort());
		this.setUseRdp(r.getUseRdp());
		this.setRdpPort(r.getRdpPort());
		this.setStatus(r.getStatus());
		this.setCreateTime(r.getCreateTime());
		this.setUpdateTime(r.getUpdateTime());
		this.setAccountList(r.getAccountList());
	}
}
