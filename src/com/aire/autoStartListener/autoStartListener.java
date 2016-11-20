package com.aire.autoStartListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.log4j.Logger;

import com.aire.IPC.Dao.Impl.IPCSendStreamImpl2;

public class autoStartListener implements ServletContextListener {
	private static Logger logger = Logger.getLogger(java.lang.Class.class);
	
	private IPCSendStreamImpl2 iPCSendStreamDao = new IPCSendStreamImpl2();
	
	private String propertiesFileName = "CSTSConfig.properties";
	
	private Timer timer1 = new Timer();
	private Timer timer2 = new Timer();

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		timer1.cancel();
		timer2.cancel();
		HashMap<String, String> hm = iPCSendStreamDao.isProcessExisting(".*ffmpeg.*rtmp.*");
		if (hm.get("isExisting").equals("true")) {
			//ffmpeg进程存在
			iPCSendStreamDao.killProcess(hm.get("pid"));
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("testAutoStartListener 触发，并准备启动！！！" + new Date());
		System.out.println("testAutoStartListener 触发，并准备启动！！！" + new Date());
		
		Parameters params = new Parameters();
		FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
				PropertiesConfiguration.class).configure(params.properties().setFileName(propertiesFileName));
		Configuration config;
		try {
			config = builder.getConfiguration();
			timer1.schedule(new autoStartMain(), 1000*config.getInt("delayTime"));// 延迟delayTime(s)启动
			timer2.schedule(new autoDetectStream(), 1000*config.getLong("startScan"), 1000*config.getLong("scanPeriod"));;//startScan scanPeriod
			logger.info("testAutoStartListener 开始启动！！！" + new Date());
			System.out.println("testAutoStartListener 开始启动！！！" + new Date());
		} catch (ConfigurationException e) {
			e.printStackTrace();
			logger.info("读取配置文件失败!!!" + new Date());
			System.out.println("读取配置文件失败!!!");
		}
		
		
	}
}
