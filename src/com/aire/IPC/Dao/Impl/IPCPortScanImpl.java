package com.aire.IPC.Dao.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.configuration2.Configuration;
import org.apache.log4j.Logger;

import com.aire.IPC.Dao.IPCDao;
import com.aire.IPC.Dao.IPCPortScanDao;
import com.aire.IPC.Entity.IPCEntity;
import com.aire.IPC.Entity.LocalHostIPCEntity;
import com.aire.exception.PropertiesReadException;
import com.aire.exception.getLocalHostIPException;
import com.aire.utils.LocalHostIP;
import com.aire.utils.PropertiesFileUtil;

/**
 * @author 作者 AIRE
 * @date 创建时间：2016年10月25日 下午6:29:28
 * @version 1.0
 */
public class IPCPortScanImpl implements IPCPortScanDao {
	private static Logger logger = Logger.getLogger(java.lang.Class.class);
	private IPCDao IPCDao = new IPCDaoImpl();
	private String propertiesFileName = "IPCDetails.properties";

	@Override
	// 用于扫描局域网内可用的IP地址
	public ArrayList<String> getLocalHostAvailableIPs() {
		// 1. 获取IP地址
		String localHostIP;
		try {
			localHostIP = new LocalHostIP().getLocalHostIP();
			// 2. 通过IP地址的前三位扫描局域网的所有设备
			String localHostIPDetect = "192.168.0";//localHostIP.substring(1, localHostIP.lastIndexOf("."));
			ArrayList<String> ips = new ArrayList<String>();
//			for (int i = 0; i < 256; i++) {
			for (int i = 102; i < 103; i++) {
				String localIP = localHostIPDetect + "." + i;
				if (IPCDao.isIPC(localIP)) {
					System.out.println(localIP);
					ips.add(localIP);
				}
			}
			// 3. 返回局域网内为IPC的ip地址
			return ips;
		} catch (getLocalHostIPException e) {
			e.printStackTrace();
			System.out.println("获取本机IP地址失败！");
			logger.error("获取本机IP地址失败！");
		} catch (PropertiesReadException e) {
			e.printStackTrace();
			System.out.println("读取配置文件失败！");
			logger.error("读取配置文件失败！");
		}
		return null;
	}

	@Override
	public LocalHostIPCEntity getLocalHostIPCs() {
		HashMap<String, String> password_username_serverIP = get_password_username_serverIP();
		if (password_username_serverIP==null) {
			//为空说明读取配置文件失败
			return null;
		} else {
			ArrayList<String> ips = getLocalHostAvailableIPs();
			LocalHostIPCEntity localHostIPCEntity = new LocalHostIPCEntity();
			HashMap<String, IPCEntity> ipcs = localHostIPCEntity.getLocalhostIPCEntity();
			int flag = 0;
			for (String ip : ips) {
				flag = 0;
				for (Entry<String, String> u_p_s : password_username_serverIP.entrySet()) {
					String[] username_serverIP = u_p_s.getValue().split("@@");
					IPCEntity ipc = IPCDao.getEntityBasedOnIP(ip, username_serverIP[0], u_p_s.getKey());
					if (ipc==null) {
						//说明获取失败
					} else {//获取成功
						flag = 1;//表明该ip已经获取到IPCEntity
						ipc.setConnectionStatus(true);
						if (username_serverIP.length<=1) {
							ipc.setServerIP("");
						} else {
							ipc.setServerIP(username_serverIP[1]);
						}
						ipcs.put(ip, ipc);
						break;
					}
				}
				if (flag==0) {
					//如果flag=0说明IPCEntity未获取到
					ipcs.put(ip, new IPCEntity());
				}
			}
			localHostIPCEntity.setLocalHostIPCEntity(ipcs);
			return localHostIPCEntity;
		}
}

	public HashMap<String, String> get_password_username_serverIP() {
		//为防止用户名相同，可以设置密码为主键
		HashMap<String, String> password_username_serverIP = new HashMap<String, String>();
		try {
			Configuration config = new PropertiesFileUtil().getPropertiesFromFile(propertiesFileName);
			Iterator<String> keys = config.getKeys();
			while (keys.hasNext()) {
				String[] u_p = config.getString(keys.next()).trim().split("\\|\\|");
				password_username_serverIP.put(u_p[0], u_p[1]);
			}
			return password_username_serverIP;
		} catch (PropertiesReadException e) {
			e.printStackTrace();
			logger.error("读取配置文件" + Thread.currentThread().getContextClassLoader().getResource("") + "\\"
					+ propertiesFileName + "失败!" + e.toString());
			System.out.println("读取配置文件" + Thread.currentThread().getContextClassLoader().getResource("") + "\\"
					+ propertiesFileName + "失败!" + e.toString());
			return null;
		}
	}
}
