package com.aire.IPC.Entity;
/**
* @author  作者 AIRE
* @date 创建时间：2016年11月16日 12:31:05
* @version 2.0
*/
public class IPCEntity2 {
	private String ip;
	private String streamName;
	private String username;
	private String password;
	private Boolean connectionStatus;
	private String serverIP;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getStreamName() {
		return streamName;
	}
	public void setStreamName(String streamName) {
		this.streamName = streamName;
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
	public Boolean getConnectionStatus() {
		return connectionStatus;
	}
	public void setConnectionStatus(Boolean connectionStatus) {
		this.connectionStatus = connectionStatus;
	}
	public String getServerIP() {
		return serverIP;
	}
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
}
