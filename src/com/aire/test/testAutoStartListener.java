package com.aire.test;

import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.aire.autoStartListener.autoStartMain;

public class testAutoStartListener implements ServletContextListener {
	private static Logger logger = Logger.getLogger(java.lang.Class.class);

	private Timer timer = new Timer();

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		timer.cancel();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("testAutoStartListener 触发，并准备启动！！！" + new Date());
		System.out.println("testAutoStartListener 触发，并准备启动！！！" + new Date());
		timer.schedule(new autoStartMain(), 10000);// 延迟10s启动
		logger.info("testAutoStartListener 开始启动！！！" + new Date());
		System.out.println("testAutoStartListener 开始启动！！！" + new Date());
	}

}
