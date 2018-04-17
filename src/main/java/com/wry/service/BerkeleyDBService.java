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
	 * ��ѯ���м�¼��key
	 * 
	 * @return
	 */
	public List<String> getAll_list() {
		return dbUtil.getAll_list();
	}

	/**
	 * �������еļ�¼
	 * 
	 * @return
	 */
	public Map<String, String> getAll_map() {
		return dbUtil.getAll_map();
	}

	/**
	 * �ر���Դ
	 */
	public void close() {
		dbUtil.close();
	}

}
