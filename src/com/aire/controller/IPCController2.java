package com.aire.controller;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.aire.IPC.Dao.IPCDao;
import com.aire.IPC.Dao.IPCSendStreamDao2;
import com.aire.IPC.Dao.Impl.IPCDaoImpl;
import com.aire.IPC.Dao.Impl.IPCSendStreamImpl2;
import com.aire.IPC.Entity.IPCEntity2;

/**
 * @author 作者 AIRE
 * @date 创建时间：2016年11月16日 下午14:05:30
 * @version 2.0
 */
@Controller
public class IPCController2 {
	private static Logger logger = Logger.getLogger(java.lang.Class.class);
	private String propertiesFileName = "CSTSConfig.properties";
	private IPCDao iPCDao = new IPCDaoImpl();
	private IPCSendStreamDao2 iPCSendStreamDao = new IPCSendStreamImpl2();

	@RequestMapping("index")
	public ModelAndView sendStreamForIPC() {
		ModelAndView modelAndView = new ModelAndView();
		Parameters params = new Parameters();
		FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
				PropertiesConfiguration.class).configure(params.properties().setFileName(propertiesFileName));
		Configuration config;
		try {
			config = builder.getConfiguration();
			if (config.getString("IPMode").equals("0")) {
				IPCEntity2 iPCEntity = new IPCEntity2();
				iPCEntity.setIp(config.getString("ip"));
				iPCEntity.setUsername(config.getString("username"));
				iPCEntity.setPassword(config.getString("password"));
				iPCEntity.setServerIP(config.getString("serverIP"));
				iPCEntity.setStreamName(config.getString("streamName"));
				if (iPCDao.isIPC(config.getString("ip"))) {
					// 表明是IPC,并已连接成功
					iPCEntity.setConnectionStatus(true);
					modelAndView.addObject("ipc", iPCEntity);
					modelAndView.setViewName("index");
					return modelAndView;
				} else {
					iPCEntity.setConnectionStatus(false);
					modelAndView.addObject("ipc", iPCEntity);
					modelAndView.setViewName("index");
					return modelAndView;
				}
			} else {
				// 暂时不进行该设置
				logger.info("暂时不进行该设置");
				modelAndView.addObject("message", "暂时没有设置自动模式,请等待下一版本!!!");
				modelAndView.setViewName("info");
				return modelAndView;
			}
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.toString());
			modelAndView.addObject("message", "读写Configuration文件出错,请联系管理员!!!");
			modelAndView.setViewName("error");
			return modelAndView;
		}
	}
	
	
	@RequestMapping("sendStream")
	public ModelAndView connectIPC(@RequestParam("ip") String ip, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("serverIP") String serverIP, @RequestParam("streamName") String streamName) {
		ModelAndView modelAndView = new ModelAndView();
		if (iPCSendStreamDao.sendStream(ip, username, password, serverIP, streamName)) {
			//推流成功
			logger.info("推流成功!!!");
			modelAndView.addObject("message", "推流成功!!!");
			modelAndView.setViewName("success");
			return modelAndView;
		} else {
			logger.error("推流失败!!!");
			modelAndView.addObject("message", "推流失败!!!");
			modelAndView.setViewName("error");
			return modelAndView;
		}
	}

}
