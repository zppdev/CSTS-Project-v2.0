package com.aire.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.aire.exception.PropertiesReadException;
import com.aire.exception.getLocalHostIPException;

/**
* @author  作者 AIRE
* @date 创建时间：2016年10月20日 下午11:08:55
* @version 1.0
* @see (new LocalHostIP()).getLocalHostIP 用于获取本机的IP地址
*/

public class LocalHostIP {
	private static Logger logger = Logger.getLogger(java.lang.Class.class);
	private String propertiesFileName = "CSTSConfig.properties";

	public String getLocalHostIP() throws getLocalHostIPException, PropertiesReadException {
		String localHostIP;
		Enumeration netInterfaces;
		InetAddress ip = null;
		ArrayList<InetAddress> ips = new ArrayList<InetAddress>();
		ArrayList<NetworkInterface> networkInterfaces = new ArrayList<NetworkInterface>();
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) netInterfaces.nextElement();
				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address) {
						if (!ip.getHostAddress().equals("127.0.0.1")) {
							ips.add(ip);
							networkInterfaces.add(netInterface);
							// ip为获得的有效网卡的IP地址
							// System.out.println("本机的IP = " +
							// ip.getHostAddress());
							// System.out.println(netInterface.getDisplayName());
						}
					}
				}
			}
			if (ips.size() <= 0) {
				System.out.println("本地的IP地址找到失败！原因：未联网或者程序失败！");
				logger.info("本地的IP地址找到失败！原因：未联网或者程序失败！");
				// 根据Properties的默认IP地址进行配置，可能存在失败
				try {
					localHostIP = new PropertiesFileUtil().getPropertiesFromFile(propertiesFileName).getString("localhostIPAddress");
					return localHostIP;
				} catch (PropertiesReadException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("读取配置文件失败！");
					logger.error("读取配置文件失败！");
					throw new PropertiesReadException("读取配置文件失败");
				}
			} else if (ips.size() >= 2) {
				String[] networkInterfaceFilter;
				try {
					networkInterfaceFilter = new PropertiesFileUtil().getPropertiesFromFile(propertiesFileName)
							.getString("NetworkInterfaceFilter").toLowerCase().split(";");
					ArrayList<InetAddress> ipss = new ArrayList<InetAddress>();
					for (int i = 0; i < ips.size(); i++) {
						for (int j = 0; j < networkInterfaceFilter.length; j++) {
							if (!Pattern.compile(networkInterfaceFilter[j])
									.matcher(networkInterfaces.get(i).getDisplayName().toLowerCase()).find()) {
								// 发现网卡名称不在过滤网卡列表里面
								ipss.add(ips.get(i));
							}
						}
					}
					if (ipss.size() <= 0) {
						System.out.println("本地的IP地址找到失败！原因：IP地址全部在过滤列表里！");
						logger.info("本地的IP地址找到失败！原因：IP地址全部在过滤列表里！");
						throw new getLocalHostIPException("本地的IP地址找到失败！原因：IP地址全部在过滤列表里！");
					} else if (ipss.size() >= 2) {
						System.out.println("本地的IP地址找到失败！原因：IP地址仍有许多，需要重新过滤！");
						logger.info("本地的IP地址找到失败！原因：IP地址仍有许多，需要重新过滤！");
						throw new getLocalHostIPException("本地的IP地址找到失败！原因：IP地址仍有许多，需要重新过滤！");
					} else {
						localHostIP = ipss.get(0).toString();
						return localHostIP;
					}
				} catch (PropertiesReadException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("读取配置文件失败！" + e.toString());
					logger.error("读取配置文件失败！" + e.toString());
					throw new PropertiesReadException("读取配置文件失败" + e.toString());
				}
			} else {// ips里面只有一个值
				localHostIP = ips.get(0).toString();
				return localHostIP;
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("获取本机IP地址失败!" + e.toString());
			logger.error("获取本机IP地址失败!" + e.toString());
			throw new getLocalHostIPException("获取本机IP地址失败!" + e.toString());
		}
	}
}
