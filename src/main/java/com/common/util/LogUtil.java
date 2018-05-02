package com.common.util;

import org.apache.log4j.Logger;

/**
 * 日志记录工具
 * 
 * @author Administrator
 *
 */
public class LogUtil {
	private static Logger logger = Logger.getLogger(LogUtil.class);

	/**
	 * info级别记录日志
	 * 
	 * @param message
	 */
	public static void LogInfo(String message) {
		logger.info(message);
	}
}
