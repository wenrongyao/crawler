package com.common.util;

import org.apache.log4j.Logger;

/**
 * ��־��¼����
 * 
 * @author Administrator
 *
 */
public class LogUtil {
	private static Logger logger = Logger.getLogger(LogUtil.class);

	/**
	 * info�����¼��־
	 * 
	 * @param message
	 */
	public static void LogInfo(String message) {
		logger.info(message);
	}
}
