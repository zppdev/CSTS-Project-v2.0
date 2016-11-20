package com.aire.IPC.Dao;

import com.aire.IPC.Entity.IPCEntity;

/**
* @author  作者 AIRE
* @date 创建时间：2016年10月25日 下午6:22:00
* @version 1.0
*/
public interface IPCDao {
	public Boolean isIPC(String ip);//检测该IP所在的设备是否为IPC
	public IPCEntity getEntityBasedOnIP(String ip, String username, String password);//根据IP地址获取IPCEntity
}
