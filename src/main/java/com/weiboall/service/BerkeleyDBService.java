package com.weiboall.service;

import java.util.Map;

import com.common.constant.R;
import com.weiboall.dao.BerkeleyDBDao;


public class BerkeleyDBService {
	private BerkeleyDBDao dbUtil;

	public BerkeleyDBService(String dbName) {
		dbUtil = new BerkeleyDBDao(R.CURRENTURL + R.BERKELEYDB_LOCATION, dbName);
	}

	/**
	 * 增加记录
	 * 
	 * @param key
	 * @param value
	 * @param isOverwrite
	 */
	public void put(String key, String value, boolean isOverwrite) {
		dbUtil.put(key, value, isOverwrite);
	}

	/**
	 * 删除信息
	 * 
	 * @param key
	 */
	public void remove(String key) {
		dbUtil.remove(key);
	}

	/**
	 * 查询单条记录
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return dbUtil.get(key);
	}

	/**
	 * 返回所有的记录
	 * 
	 * @return
	 */
	public Map<String, String> getAll() {
		return dbUtil.getAll();
	}

	/**
	 * 关闭资源
	 */
	public void close() {
		dbUtil.close();
	}

}
