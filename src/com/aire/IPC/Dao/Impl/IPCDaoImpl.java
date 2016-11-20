package com.aire.IPC.Dao.Impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import org.apache.commons.exec.CommandLine;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.aire.IPC.Dao.IPCDao;
import com.aire.IPC.Entity.IPCEntity;
import com.aire.exception.HCNETSDKException;

/**
 * @author 作者 AIRE
 * @date 创建时间：2016年10月25日 下午6:28:41
 * @version 1.0
 */

public class IPCDaoImpl implements IPCDao {
	private static Logger logger = Logger.getLogger(java.lang.Class.class);

	CloseableHttpClient httpclient = HttpClients.createDefault();
	RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(100).setConnectionRequestTimeout(100)
			.setSocketTimeout(100).build();

	@Override
	// 通过http访问判断局域网的某个ip地址是否为IPC
	// localIP为局域网的IP地址
	// 返回值
	// 1. 为true即为IPC 2. 为false即不是IPC 3. 为flase表示程序出错
	public Boolean isIPC(String localIP) {
		// 简易get
		String uri = "http://" + localIP;

		// 创建HttpGet，将要请求的URL通过构造方法传入HttpGet对象。
		HttpGet httpget = new HttpGet(uri);
		try {
			httpget.setConfig(requestConfig);

			CloseableHttpResponse response = httpclient.execute(httpget);

			// 相当于按了下确定登录的按钮，也就是浏览器调转了
			HttpEntity httpEntity = response.getEntity();
			String entityMsg = EntityUtils.toString(httpEntity);
			Document doc = Jsoup.parse(entityMsg);
			String msg = doc.data().trim();// 获取原生页
			boolean flag = Pattern.compile("(doc/page/login.asp)+").matcher(msg).find();
			return flag;
		} catch (ClientProtocolException e) {
			// e.printStackTrace();
			// System.out.println("ClientProtocolException" + e.toString());
			logger.error("ClientProtocolException" + e.toString());
		} catch (IOException e) {
			// e.printStackTrace();
			// System.out.println("IOException" + e.toString());
			logger.error("IOException" + e.toString());
		}
		return false;
	}

	@Override
	// 根据IP地址获取该设备的序列号
	// 返回值 1. null表示未获取到该设备，该ip地址、用户名、密码可能不匹配或者NET_DVR_Init 初始化失败 2. 返回IPCEntity
	public IPCEntity getEntityBasedOnIP(String localIP, String username, String password) {
		IPCEntity ipcEntity = new IPCEntity();
		
		String cmdStr = Thread.currentThread().getContextClassLoader().getResource("/").getPath() + "exe/getIPCSerialNumber " + localIP + " "
				+ username + " " + password;//Class.class.getClass().getResource("/").getPath()
		System.out.println("执行命令" + cmdStr);
		logger.info("执行命令" + cmdStr);
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(cmdStr);
			InputStream is = p.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String content = "";
			String line;
			while ((line = reader.readLine()) != null) {
				content = content + line;
			}
			System.out.println(content);
			p.waitFor();
			is.close();
			reader.close();
			if (!content.toLowerCase().startsWith("login error")) {
				ipcEntity.setConnectionStatus(true);
				ipcEntity.setId(content);
				ipcEntity.setIp(localIP);
				ipcEntity.setPassword(password);
				ipcEntity.setUsername(username);
				return ipcEntity;
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.toString());
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error(e.toString());
		} finally {
			p.destroy();
		}
		return null;
	}

}
