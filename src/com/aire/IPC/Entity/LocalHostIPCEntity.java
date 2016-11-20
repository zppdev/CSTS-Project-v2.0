package com.aire.IPC.Entity;

import java.util.HashMap;

/**
* @author  作者 AIRE
* @date 创建时间：2016年10月25日 下午6:14:52
* @version 1.0
*/
public class LocalHostIPCEntity {
	private static HashMap<String, IPCEntity> localhostIPCEntity = new HashMap<String, IPCEntity>();

	public static HashMap<String, IPCEntity> getLocalhostIPCEntity() {
		return localhostIPCEntity;
	}

	public static void setLocalHostIPCEntity(HashMap<String, IPCEntity> localhostIPCEntity) {
		LocalHostIPCEntity.localhostIPCEntity = localhostIPCEntity;
	}
}
