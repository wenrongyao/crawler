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
	 * ���Ӽ�¼
	 * 
	 * @param key
	 * @param value
	 * @param isOverwrite
	 */
	public void put(String key, String value, boolean isOverwrite) {
		dbUtil.put(key, value, isOverwrite);
	}

	/**
	 * ɾ����Ϣ
	 * 
	 * @param key
	 */
	public void remove(String key) {
		dbUtil.remove(key);
	}

	/**
	 * ��ѯ������¼
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return dbUtil.get(key);
	}

	/**
	 * �������еļ�¼
	 * 
	 * @return
	 */
	public Map<String, String> getAll() {
		return dbUtil.getAll();
	}

	/**
	 * �ر���Դ
	 */
	public void close() {
		dbUtil.close();
	}

}
