package com.wry.service;

import java.util.List;
import java.util.Map;

import com.wry.constant.R;
import com.wry.dao.BerkeleyDBDao;

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
	 * 查询所有记录的key
	 * 
	 * @return
	 */
	public List<String> getAll_list() {
		return dbUtil.getAll_list();
	}

	/**
	 * 返回所有的记录
	 * 
	 * @return
	 */
	public Map<String, String> getAll_map() {
		return dbUtil.getAll_map();
	}

	/**
	 * 关闭资源
	 */
	public void close() {
		dbUtil.close();
	}

}
