package com.aire.IPC.Dao.Impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.log4j.Logger;

import com.aire.IPC.Dao.IPCSendStreamDao;

/**
 * @author 作者 AIRE
 * @date 创建时间：2016年10月25日 下午6:30:08
 * @version 1.0
 */
public class IPCSendStreamImpl implements IPCSendStreamDao {
	private static Logger logger = Logger.getLogger(java.lang.Class.class);

	private Boolean SendStreamBaseWindows(String ip, String username, String password, String id, String ServerIP, String streamName) {
//		String cmdStr = "ffmpeg -i rtsp://" + username + ":" + password + "@" + ip
//				+ "/h264/ch1/sub/av_stream -f flv -an rtmp://" + ServerIP + "/live/" + id;
		//Test
		String cmdStr = "ffmpeg -i rtsp://" + username + ":" + password + "@" + ip
				+ "/h264/ch1/sub/av_stream -f flv -an rtmp://" + ServerIP + "/live/" + streamName;
		CommandLine cmdLine = CommandLine.parse(cmdStr);

		DefaultExecutor executor = new DefaultExecutor();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		executor.setStreamHandler(new PumpStreamHandler(baos, baos));
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		executor.setExitValue(1);
		try {
			executor.execute(cmdLine, resultHandler);
			String result = baos.toString().trim();
			System.out.println(result);// 这个result就是输出结果。如果是JAVA程序，抛出了异常，也被它获取。
			// 这里开始的代码会被立即执行下去，因为上面的语句不会被阻塞。
			resultHandler.waitFor(10);// 等待10秒。
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			logger.error("流Send失败" + e.toString());
			System.out.println("流Send失败" + e.toString());
		}

		return null;
	}

	private Boolean SendStreamBaseLinux(String ip, String username, String password, String id, String ServerIP, String streamName) {
//		String cmdStr = "ffmpeg -i rtsp://" + username + ":" + password + "@" + ip
//				+ "/h264/ch1/sub/av_stream -f flv -b:v 20k -r 20 -s 640x480 -c:a copy rtmp://" + ServerIP + "/live/" + id;
		//Test
		String cmdStr = "ffmpeg -i rtsp://" + username + ":" + password + "@" + ip
				+ "/h264/ch1/sub/av_stream -f flv -b:v 20k -r 20 -s 640x480 -c:a copy rtmp://" + ServerIP + "/live/" + streamName;
		CommandLine cmdLine = CommandLine.parse(cmdStr.trim());

		DefaultExecutor executor = new DefaultExecutor();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		executor.setStreamHandler(new PumpStreamHandler(baos, baos));
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		executor.setExitValue(1);
		try {
			executor.execute(cmdLine, resultHandler);
			String result = baos.toString().trim();
			System.out.println(result);// 这个result就是输出结果。如果是JAVA程序，抛出了异常，也被它获取。
			// 这里开始的代码会被立即执行下去，因为上面的语句不会被阻塞。
			resultHandler.waitFor(10);// 等待10秒。
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			logger.error("流Send失败" + e.toString());
			System.out.println("流Send失败" + e.toString());
		}

		return null;
	}

	@Override
	public Boolean SendStream(String ip, String username, String password, String id, String ServerIP, String streamName) {
		if (getOSName().equals("windows")) {
			SendStreamBaseWindows(ip, username, password, id, ServerIP, streamName);
		} else {
			SendStreamBaseLinux(ip, username, password, id, ServerIP, streamName);
		}
		return null;
	}
	
	private String getOSName() {
		String osName = System.getProperties().getProperty("os.name");
		if (Pattern.compile("windows").matcher(osName.toLowerCase()).find()) {
			return "windows";
		} else {
			return "linux";
		}
	}

	@Override
	public Boolean SendStream(String ip, String username, String password, String ServerIP, String streamName) {
		// TODO Auto-generated method stub
		return null;
	}
}
