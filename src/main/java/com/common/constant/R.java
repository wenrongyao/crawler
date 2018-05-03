package com.common.constant;

/**
 * 常量类
 * 
 * @author Administrator
 *
 */
public class R {
	// 微博模块的cookie
	public static String WEIBOCOOKIE = "SUB=_2AkMu-q8yf8NxqwJRmP0RxWvqbY1-wwrEieKYpl7pJRMxHRl-yT83ql0OtRCjJxWDhzJsLCPANZqnKvd1TRLpRw..; \r\n"
			+ "SUBP=0033WrSXqPxfM72-Ws9jqgMF55529P9D9WFHjjqrLYE8FBTJqjG.faNA; \r\n"
			+ "SINAGLOBAL=1531170698955.3154.1504059402911; \r\n" + "UOR=,,krcom.cn; \r\n"
			+ "ULV=1513576375693:20:10:2:5642275271271.326.1513576375621:1513510051116; ";

	// 西瓜视频的cookie
	public static String XIGUACOOKIE = "odin_tt=1c0d679468615f21d30d89c8dde15792cb223d081befa20a8c235356f8c7bba98b0523f62174e22ace2314c875650f28\r\n"
			+ "	alert_coverage=80\r\n" + "	qh[360]=1\r\n"
			+ "	UM_distinctid=16160cb18000-04a08f668-5137027b-38400-16160cb18056a\r\n" + "	install_id=25377382466\r\n"
			+ "	ttreq=1$2036ff535da35537f55a2599f42dd4cb5b803800\r\n" + "";
	// 待采集的url集合
	public static String URLS = "urls";
	// 爬取过的url
	public static String VISITEDURLS = "visitedurls";
	// 微信小视频单次获取条数
	public static Integer VIDEO_NUMBER = 25;
	// 单页一级评论总数
	public static Integer DISCUSS_NUMBER = 15;
	// berkeledb数据库的存放位置
	public static String BERKELEYDB_LOCATION = "/Crawler/berkeleyDB";
	// codejs的url
	public static String CODEJSURL = "/Crawler/code.js";
	// 当前路径
	public static String CURRENTURL = "H:/workspace/eclipst-ee/crawler";
	// 采集总条数
	public static Integer COLLECTIONCOUNT = 0;
	// 定时任务间隔时间
	public static Integer TIMETASK = 0;
}
