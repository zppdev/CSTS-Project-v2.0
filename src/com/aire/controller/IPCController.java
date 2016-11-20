package com.aire.controller;

import java.util.HashMap;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.aire.IPC.Dao.IPCPortScanDao;
import com.aire.IPC.Dao.IPCSendStreamDao;
import com.aire.IPC.Dao.Impl.IPCPortScanImpl;
import com.aire.IPC.Dao.Impl.IPCSendStreamImpl;
import com.aire.IPC.Entity.IPCEntity;

/**
* @author  作者 AIRE
* @date 创建时间：2016年10月26日 下午9:23:30
* @version 1.0
*/
@Controller
public class IPCController {
	IPCPortScanDao ipcPortScanDao = new IPCPortScanImpl();
	IPCSendStreamDao ipcSendStreamDao = new IPCSendStreamImpl();
	private String propertiesFileName = "IPCDetails.properties";
	
	@RequestMapping("IPCView")
	public ModelAndView IPCView() {
		ModelAndView modelAndView = new ModelAndView();
		HashMap<String, IPCEntity> ipcList = ipcPortScanDao.getLocalHostIPCs().getLocalhostIPCEntity();
		modelAndView.addObject("ipcList", ipcList);
		System.out.println(ipcList.size());
		modelAndView.setViewName("IPCView");
		return modelAndView;
	}
	
	@RequestMapping("connectIPC")
	public String connectIPC(@RequestParam("ip") String localIP, @RequestParam("id") String id, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("serverIP") String serverIP) {
		Parameters params = new Parameters();
		FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
				PropertiesConfiguration.class).configure(params.properties().setFileName(propertiesFileName));
		Configuration config;
		try {
			config = builder.getConfiguration();
			config.setProperty(id, password + "||" + username + "@@" + serverIP);
//			System.out.println(id);
//			System.out.println(password + "||" + username + "@@" + serverIP);
			builder.save();
		} catch (ConfigurationException e) {
			e.printStackTrace();
			System.out.println("配置文件保存失败！");
		}
		return "IPCView";
	}
	
	@RequestMapping("sendStreamForIPC")
	public String sendStreamForIPC(@RequestParam("ip") String localIP, @RequestParam("id") String id, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("serverIP") String serverIP, @RequestParam("streamName") String streamName) {
		System.out.println(localIP);
		System.out.println(id);
		ipcSendStreamDao.SendStream(localIP, username, password, id, serverIP, streamName);
		return "IPCView";
	}
}
