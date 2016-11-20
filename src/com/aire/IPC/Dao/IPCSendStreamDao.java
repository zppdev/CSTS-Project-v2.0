package com.aire.IPC.Dao;
/**
* @author  作者 AIRE
* @date 创建时间：2016年10月25日 下午6:27:14
* @version 1.0
*/
public interface IPCSendStreamDao {
	public Boolean SendStream(String ip, String username, String password, String ServerIP, String streamName);
	public Boolean SendStream(String ip, String username, String password, String id, String ServerIP, String streamName);
}
