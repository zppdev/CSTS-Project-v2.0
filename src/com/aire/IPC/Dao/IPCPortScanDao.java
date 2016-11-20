package com.aire.IPC.Dao;

import java.util.ArrayList;

import com.aire.IPC.Entity.LocalHostIPCEntity;

/**
* @author  作者 AIRE
* @date 创建时间：2016年10月25日 下午6:22:14
* @version 1.0
*/
public interface IPCPortScanDao {
	public ArrayList<String> getLocalHostAvailableIPs();//获取局域网内的为IPC的ip地址
	public LocalHostIPCEntity getLocalHostIPCs();//获取局域网内所有的IPC，包含ip地址为IPC的但是无法获取序列号的IPC
}
