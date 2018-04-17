package com.wry.dao;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.CursorConfig;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Transaction;
import com.sleepycat.je.TransactionConfig;
import com.sleepycat.je.log.LogException;
import com.wry.log.LogUtil;

/**
 * ����berkeledb �Ĺ�����
 * 
 * @author Administrator
 *
 */
public class BerkeleyDBDao {

	// ���ݿ⻷��
	private static Environment env;
	// ���ݿ�
	private Database frontierDatabase;
	// ���ݿ���
	private String dbName;

	public BerkeleyDBDao(String homeDirectory, String dbName) {
		this.dbName = dbName;
		// 1������EnvironmentConfig
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setTransactional(true);
		envConfig.setAllowCreate(true);
		// 2��ʹ��EnvironmentConfig����Environment
		try {
			env = new Environment(new File(homeDirectory), envConfig);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

		// 3������DatabaseConfig
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setTransactional(true);
		dbConfig.setAllowCreate(true);

		// 4��ʹ��Environment��DatabaseConfig��Database
		try {
			frontierDatabase = env.openDatabase(null, dbName, dbConfig);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

	}

	/*
	 * �����ݿ���д���¼�����ж��Ƿ�������ظ����ݡ� ����key��value
	 * ���������ظ����ݣ���ֱ��ʹ��put()���ɣ����������ظ����ݣ���ʹ��putNoOverwrite()��
	 */
	public boolean put(String key, String value, boolean isOverwrite) {
		try {
			// ����key/value,ע��DatabaseEntry��ʹ�õ���bytes����
			DatabaseEntry theKey = new DatabaseEntry(key.getBytes("UTF-8"));
			DatabaseEntry theData = new DatabaseEntry(value.getBytes("UTF-8"));
			OperationStatus status = null;
			Transaction txn = null;
			try {
				// 1��Transaction����
				TransactionConfig txConfig = new TransactionConfig();
				txConfig.setSerializableIsolation(true);
				txn = env.beginTransaction(null, txConfig);
				// 2��д������
				if (isOverwrite) {
					status = frontierDatabase.put(txn, theKey, theData);
				} else {
					status = frontierDatabase.putNoOverwrite(txn, theKey, theData);
				}
				txn.commit();
				if (status == OperationStatus.SUCCESS) {
					LogUtil.LogInfo("�����ݿ�" + dbName + "��д��:" + "key:" + key + "," + "value:" + value);
					return true;
				} else if (status == OperationStatus.KEYEXIST) {
					LogUtil.LogInfo("�����ݿ�" + dbName + "��д��:" + "key:" + key + "," + "value:" + value + "ʧ��,��ֵ�Ѿ�����");
					return false;
				} else {
					LogUtil.LogInfo("�����ݿ�" + dbName + "��д��:" + "key:" + key + "," + "value:" + value + "ʧ��");
					return false;
				}
			} catch (LogException lockConflict) {
				txn.abort();
				LogUtil.LogInfo("�����ݿ�" + dbName + "��д��:" + "key:" + key + "," + "value:" + value + "����lock�쳣");
				return false;
			}
		} catch (Exception e) {
			// ������
			LogUtil.LogInfo("�����ݿ�" + dbName + "��д��:" + "key:" + key + "," + "value:" + value + "���ִ���");
			return false;
		}
	}

	/*
	 * �����ݿ��ж������� ����key ����value
	 */
	public String get(String key) {
		String value = null;
		try {
			DatabaseEntry theKey = new DatabaseEntry(key.getBytes("UTF-8"));
			DatabaseEntry theData = new DatabaseEntry();
			Transaction txn = null;
			// 1������ Transaction�����Ϣ
			TransactionConfig txConfig = new TransactionConfig();
			txConfig.setSerializableIsolation(true);
			txn = env.beginTransaction(null, txConfig);
			// 2����ȡ����
			OperationStatus status = frontierDatabase.get(txn, theKey, theData, LockMode.DEFAULT);
			txn.commit();
			if (status == OperationStatus.SUCCESS) {
				// 3�����ֽ�ת����String
				byte[] retData = theData.getData();
				value = new String(retData, "UTF-8");
				LogUtil.LogInfo("�����ݿ�" + dbName + "�ж�ȡ:" + "key:" + key + "," + "value:" + value);
			} else {
				LogUtil.LogInfo("No record found for key '" + key + "'.");
			}
		} catch (UnsupportedEncodingException e) {
			LogUtil.LogInfo("�����ݿ�" + dbName + "�ж�ȡ:" + "key:" + key + "," + "value:" + value + "ʧ��");
			e.printStackTrace();
		} catch (DatabaseException e) {
			LogUtil.LogInfo("�����ݿ�" + dbName + "�ж�ȡ:" + "key:" + key + "," + "value:" + value + "ʧ��");
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * �������ݿ��е����м�¼���������е�key��list��
	 */
	public ArrayList<String> getAll_list() {
		LogUtil.LogInfo("===========�������ݿ�" + dbName + "�е���������==========");
		Cursor myCursor = null;
		ArrayList<String> resultList = new ArrayList<String>();
		Transaction txn = null;
		try {
			txn = BerkeleyDBDao.env.beginTransaction(null, null);
			CursorConfig cc = new CursorConfig();
			cc.setReadCommitted(true);
			if (myCursor == null)
				myCursor = frontierDatabase.openCursor(txn, cc);
			DatabaseEntry foundKey = new DatabaseEntry();
			DatabaseEntry foundData = new DatabaseEntry();
			// ʹ��cursor.getPrev�����������α��ȡ����
			if (myCursor.getFirst(foundKey, foundData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				String theKey = new String(foundKey.getData(), "UTF-8");
				String theData = new String(foundData.getData(), "UTF-8");
				resultList.add(theKey);
				LogUtil.LogInfo("Key | Data : " + "key:" + theKey + " | " + "value:" + theData + "");
				while (myCursor.getNext(foundKey, foundData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
					theKey = new String(foundKey.getData(), "UTF-8");
					theData = new String(foundData.getData(), "UTF-8");
					resultList.add(theKey);
					LogUtil.LogInfo("Key | Data : " + "key:" + theKey + " | " + "value:" + theData + "");
				}
			}
			myCursor.close();
			txn.commit();
			return resultList;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			LogUtil.LogInfo("getEveryItem��������쳣");
			try {
				txn.abort();
			} catch (DatabaseException e1) {
				e1.printStackTrace();
			}
			if (myCursor != null) {
				try {
					myCursor.close();
				} catch (DatabaseException e1) {
					e1.printStackTrace();
				}
			}
			return null;
		}
	}

	/**
	 * �������ݿ��е����м�¼������map
	 */
	public Map<String, String> getAll_map() {
		LogUtil.LogInfo("===========�������ݿ�" + dbName + "�е���������==========");
		Cursor myCursor = null;
		Map<String, String> resultMap = new HashMap<String, String>();
		Transaction txn = null;
		try {
			txn = BerkeleyDBDao.env.beginTransaction(null, null);
			CursorConfig cc = new CursorConfig();
			cc.setReadCommitted(true);
			if (myCursor == null)
				myCursor = frontierDatabase.openCursor(txn, cc);
			DatabaseEntry foundKey = new DatabaseEntry();
			DatabaseEntry foundData = new DatabaseEntry();
			// ʹ��cursor.getPrev�����������α��ȡ����
			if (myCursor.getFirst(foundKey, foundData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				String theKey = new String(foundKey.getData(), "UTF-8");
				String theData = new String(foundData.getData(), "UTF-8");
				resultMap.put(theKey, theData);
				LogUtil.LogInfo("Key | Data : " + "key:" + theKey + " | " + "value:" + theData + "");
				while (myCursor.getNext(foundKey, foundData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
					theKey = new String(foundKey.getData(), "UTF-8");
					theData = new String(foundData.getData(), "UTF-8");
					resultMap.put(theKey, theData);
					LogUtil.LogInfo("Key | Data : " + "key:" + theKey + " | " + "value:" + theData + "");
				}
			}
			myCursor.close();
			txn.commit();
			return resultMap;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			LogUtil.LogInfo("getEveryItem��������쳣");
			try {
				txn.abort();
			} catch (DatabaseException e1) {
				e1.printStackTrace();
			}
			if (myCursor != null) {
				try {
					myCursor.close();
				} catch (DatabaseException e1) {
					e1.printStackTrace();
				}
			}
			return null;
		}
	}

	/*
	 * ����keyֵɾ�����ݿ��е�һ����¼
	 */
	public boolean remove(String key) {
		boolean success = false;
		long sleepMillis = 0;
		for (int i = 0; i < 3; i++) {
			if (sleepMillis != 0) {
				try {
					Thread.sleep(sleepMillis);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sleepMillis = 0;
			}
			Transaction txn = null;
			try {
				// 1��ʹ��cursor.getPrev�����������α��ȡ����
				TransactionConfig txConfig = new TransactionConfig();
				txConfig.setSerializableIsolation(true);
				txn = env.beginTransaction(null, txConfig);
				DatabaseEntry theKey;
				theKey = new DatabaseEntry(key.getBytes("UTF-8"));

				// 2��ɾ������ ���ύ
				OperationStatus res = frontierDatabase.delete(txn, theKey);
				txn.commit();
				if (res == OperationStatus.SUCCESS) {
					LogUtil.LogInfo("�����ݿ�" + dbName + "��ɾ��:" + "key:" + key);
					success = true;
					return success;
				} else if (res == OperationStatus.KEYEMPTY) {
					LogUtil.LogInfo("û�д����ݿ�" + dbName + "���ҵ�:" + "key:" + key + "���޷�ɾ��");
				} else {
					LogUtil.LogInfo("ɾ������ʧ�ܣ�����" + res.toString());
				}
				return false;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return false;
			} catch (LogException lockConflict) {
				LogUtil.LogInfo("ɾ������ʧ�ܣ�����lockConflict�쳣");
				sleepMillis = 1000;

				continue;
			} catch (DatabaseException e) {
				e.printStackTrace();
			} finally {
				if (!success) {
					if (txn != null) {
						try {
							txn.abort();
						} catch (DatabaseException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * �رպ���
	 * 
	 */
	public void close() {
		if (frontierDatabase != null) {
			try {
				frontierDatabase.close();
				frontierDatabase = null;
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
		}
		if (env != null) {
			try {
				env.close();
				env = null;
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
		}
	}

}
