package com.aire.IPC.Dao.Impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.log4j.Logger;

import com.aire.IPC.Dao.IPCSendStreamDao;
import com.aire.IPC.Dao.IPCSendStreamDao2;

/**
 * @author 作者 AIRE
 * @date 创建时间：2016年11月16日 下午15:26:08
 * @version 2.0
 */
public class IPCSendStreamImpl2 implements IPCSendStreamDao2 {
	private static Logger logger = Logger.getLogger(java.lang.Class.class);
	
	@Override
	public Boolean sendStream(String ip, String username, String password, String serverIP, String streamName) {
		HashMap<String, String> hm = isProcessExisting(".*ffmpeg.*rtmp.*");
		if (hm.get("isExisting").equals("true")) {
			//ffmpeg进程存在
			killProcess(hm.get("pid"));
		}
		
		String cmdStr = "ffmpeg -i rtsp://" + username + ":" + password + "@" + ip
				+ "/h264/ch1/sub/av_stream -f flv -b:v 20k -r 20 -s 640x480 -c:a copy rtmp://" + serverIP + "/live/" + streamName;
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
			logger.info(result);
			return true;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			logger.error("流Send失败" + e.toString());
			System.out.println("流Send失败" + e.toString());
			return false;
		}
	}
	
	public HashMap<String, String> isProcessExisting(String processName) {//".*ffmpeg.*rtmp.*"
		HashMap<String, String> hm = new HashMap<String, String>();
		Process p = null;
		int flag = 0;
		try {
			p = Runtime.getRuntime().exec("ps -aux");
			p.waitFor();
			InputStream is = p.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			String pid = "";
			while ((line = reader.readLine()) != null) {
				if (line.matches(processName)) {
					System.out.println(line);
					Matcher m = Pattern.compile("[0-9]{4,5}").matcher(line);
					m.find();
					pid = m.group(0);
					System.out.println("pid为:"+pid);
					flag = 1;
				}
			}
			is.close();
			reader.close();
			if (flag==1) {
				hm.put("isExisting", "true");
				hm.put("pid", pid);
				return hm;
			} else {
				hm.put("isExisting", "fasle");
				hm.put("pid", pid);
				return hm;
			}
		} catch (IOException e) {
			e.printStackTrace();
			hm.put("isExisting", "fasle");
			hm.put("pid", "");
			return hm;
		} catch (InterruptedException e) {
			e.printStackTrace();
			hm.put("isExisting", "fasle");
			hm.put("pid", "");
			return hm;
		} finally {
			p.destroy();
		}
	}
	public Boolean killProcess(String pid) {
		Process p = null;
		System.out.println("kill -s 9 " + pid);
		try {
			p = Runtime.getRuntime().exec("kill -s 9 " + pid);
			p.waitFor();
			InputStream is = p.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			p.waitFor();
			is.close();
			reader.close();
			System.out.println("进程已删除");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		} finally {
			p.destroy();
		}
	}
}
