package com.aire.IPC.Entity;
/**
* @author  作者 AIRE
* @date 创建时间：2016年10月25日 下午6:13:05
* @version 1.0
*/
public class IPCEntity {
	private String id;
	private String username;
	private String password;
	private String ip;
	private Boolean connectionStatus;
	private String serverIP;
	public String getServerIP() {
		return serverIP;
	}
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	private String descritpion;
	public String getId() {
		return id;
	}
	public Boolean getConnectionStatus() {
		return connectionStatus;
	}
	public void setConnectionStatus(Boolean connectionStatus) {
		this.connectionStatus = connectionStatus;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDescritpion() {
		return descritpion;
	}
	public void setDescritpion(String descritpion) {
		this.descritpion = descritpion;
	}
}
