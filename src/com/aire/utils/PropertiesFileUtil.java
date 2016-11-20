package com.aire.utils;

import java.io.File;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.log4j.Logger;

import com.aire.exception.PropertiesReadException;

/**
 * @author 作者 AIRE
 * @date 创建时间：2016年10月20日 下午11:08:55
 * @version 1.0
 * @see (new PropertiesFileUtil()).getPropertiesFromFile
 *      用于获取src目录下的CSTSConfig.properties配置文件参数
 */

public class PropertiesFileUtil {
	private static Logger logger = Logger.getLogger(java.lang.Class.class);

	public Configuration getPropertiesFromFile(String propertiesFileName) throws PropertiesReadException {
		try {
			// WEB获取src下路径的方法
			// System.out.println(PropertiesFileUtil.class.getResource("/"));
			// System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
			// System.out.println(PropertiesFileUtil.class.getClassLoader().getResource(""));
			System.out.println(Thread.currentThread().getContextClassLoader().getResource("") + propertiesFileName);
			Configuration config = new Configurations().properties(new File(
					Thread.currentThread().getContextClassLoader().getResource("") + propertiesFileName));
			return config;
		} catch (ConfigurationException e) {
			e.printStackTrace();
			logger.error("读取配置文件" + Thread.currentThread().getContextClassLoader().getResource("")
					+ "\\" + propertiesFileName + "失败!" + e.toString());
			throw new PropertiesReadException("配置文件读取异常！");
		}
	}
}
