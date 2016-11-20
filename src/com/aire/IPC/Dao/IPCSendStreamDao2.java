package com.aire.IPC.Dao;
/**
* @author  作者 AIRE
* @date 创建时间：2016年10月25日 下午6:27:14
* @version 1.0
*/
public interface IPCSendStreamDao2 {
	public Boolean sendStream(String ip, String username, String password, String serverIP, String streamName);
}
