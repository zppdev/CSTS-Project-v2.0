package com.aire.autoStartListener;

import java.util.Date;
import java.util.HashMap;
import java.util.TimerTask;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.log4j.Logger;

import com.aire.IPC.Dao.IPCDao;
import com.aire.IPC.Dao.Impl.IPCDaoImpl;
import com.aire.IPC.Dao.Impl.IPCSendStreamImpl2;

public class autoDetectStream extends TimerTask {
	
private static Logger logger = Logger.getLogger(java.lang.Class.class);
	
	private String propertiesFileName = "CSTSConfig.properties";
	
	private IPCSendStreamImpl2 iPCSendStreamDao = new IPCSendStreamImpl2();
	
	private IPCDao iPCDao = new IPCDaoImpl();
	
	@Override
	public void run() {//检测是否有推流进程,若无则进行推流
		HashMap<String, String> hm = iPCSendStreamDao.isProcessExisting(".*ffmpeg.*rtmp.*");
		if (hm.get("isExisting").equals("true")) {
			//ffmpeg进程存在
			System.out.println("ffmpeg进程存在,无需自动推流!!!");
		} else {
			System.out.println("推流程序自动启动");
			Parameters params = new Parameters();
			FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
					PropertiesConfiguration.class).configure(params.properties().setFileName(propertiesFileName));
			Configuration config;
			try {
				config = builder.getConfiguration();
				if (config.getString("IPMode").equals("0")) {
					if (iPCDao.isIPC(config.getString("ip"))) {//是IPC,开始推流
						iPCSendStreamDao.sendStream(config.getString("ip"), config.getString("username"), config.getString("password"), config.getString("serverIP"), config.getString("streamName"));
					} else {//配置文件参数不是IPC或无法连接成功
						logger.info("配置文件参数不是IPC或无法连接成功" + new Date());
						System.out.println("配置文件参数不是IPC或无法连接成功");
					}
				} else {
					// 暂时不进行该设置
					logger.info("暂时没有设置自动模式,请等待下一版本!!!");
				}
			} catch (ConfigurationException e) {
				e.printStackTrace();
				logger.info("读取配置文件失败!!!" + new Date());
				System.out.println("读取配置文件失败!!!");
			}
		}
	}

}
