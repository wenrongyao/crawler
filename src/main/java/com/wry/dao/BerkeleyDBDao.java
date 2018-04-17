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
 * 操作berkeledb 的工具类
 * 
 * @author Administrator
 *
 */
public class BerkeleyDBDao {

	// 数据库环境
	private static Environment env;
	// 数据库
	private Database frontierDatabase;
	// 数据库名
	private String dbName;

	public BerkeleyDBDao(String homeDirectory, String dbName) {
		this.dbName = dbName;
		// 1、创建EnvironmentConfig
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setTransactional(true);
		envConfig.setAllowCreate(true);
		// 2、使用EnvironmentConfig配置Environment
		try {
			env = new Environment(new File(homeDirectory), envConfig);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

		// 3、创建DatabaseConfig
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setTransactional(true);
		dbConfig.setAllowCreate(true);

		// 4、使用Environment与DatabaseConfig打开Database
		try {
			frontierDatabase = env.openDatabase(null, dbName, dbConfig);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

	}

	/*
	 * 向数据库中写入记录，并判断是否可以有重复数据。 传入key和value
	 * 若可以有重复数据，则直接使用put()即可，若不能有重复数据，则使用putNoOverwrite()。
	 */
	public boolean put(String key, String value, boolean isOverwrite) {
		try {
			// 设置key/value,注意DatabaseEntry内使用的是bytes数组
			DatabaseEntry theKey = new DatabaseEntry(key.getBytes("UTF-8"));
			DatabaseEntry theData = new DatabaseEntry(value.getBytes("UTF-8"));
			OperationStatus status = null;
			Transaction txn = null;
			try {
				// 1、Transaction配置
				TransactionConfig txConfig = new TransactionConfig();
				txConfig.setSerializableIsolation(true);
				txn = env.beginTransaction(null, txConfig);
				// 2、写入数据
				if (isOverwrite) {
					status = frontierDatabase.put(txn, theKey, theData);
				} else {
					status = frontierDatabase.putNoOverwrite(txn, theKey, theData);
				}
				txn.commit();
				if (status == OperationStatus.SUCCESS) {
					LogUtil.LogInfo("向数据库" + dbName + "中写入:" + "key:" + key + "," + "value:" + value);
					return true;
				} else if (status == OperationStatus.KEYEXIST) {
					LogUtil.LogInfo("向数据库" + dbName + "中写入:" + "key:" + key + "," + "value:" + value + "失败,该值已经存在");
					return false;
				} else {
					LogUtil.LogInfo("向数据库" + dbName + "中写入:" + "key:" + key + "," + "value:" + value + "失败");
					return false;
				}
			} catch (LogException lockConflict) {
				txn.abort();
				LogUtil.LogInfo("向数据库" + dbName + "中写入:" + "key:" + key + "," + "value:" + value + "出现lock异常");
				return false;
			}
		} catch (Exception e) {
			// 错误处理
			LogUtil.LogInfo("向数据库" + dbName + "中写入:" + "key:" + key + "," + "value:" + value + "出现错误");
			return false;
		}
	}

	/*
	 * 从数据库中读出数据 传入key 返回value
	 */
	public String get(String key) {
		String value = null;
		try {
			DatabaseEntry theKey = new DatabaseEntry(key.getBytes("UTF-8"));
			DatabaseEntry theData = new DatabaseEntry();
			Transaction txn = null;
			// 1、配置 Transaction相关信息
			TransactionConfig txConfig = new TransactionConfig();
			txConfig.setSerializableIsolation(true);
			txn = env.beginTransaction(null, txConfig);
			// 2、读取数据
			OperationStatus status = frontierDatabase.get(txn, theKey, theData, LockMode.DEFAULT);
			txn.commit();
			if (status == OperationStatus.SUCCESS) {
				// 3、将字节转换成String
				byte[] retData = theData.getData();
				value = new String(retData, "UTF-8");
				LogUtil.LogInfo("从数据库" + dbName + "中读取:" + "key:" + key + "," + "value:" + value);
			} else {
				LogUtil.LogInfo("No record found for key '" + key + "'.");
			}
		} catch (UnsupportedEncodingException e) {
			LogUtil.LogInfo("从数据库" + dbName + "中读取:" + "key:" + key + "," + "value:" + value + "失败");
			e.printStackTrace();
		} catch (DatabaseException e) {
			LogUtil.LogInfo("从数据库" + dbName + "中读取:" + "key:" + key + "," + "value:" + value + "失败");
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 遍历数据库中的所有记录，返回所有的key（list）
	 */
	public ArrayList<String> getAll_list() {
		LogUtil.LogInfo("===========遍历数据库" + dbName + "中的所有数据==========");
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
			// 使用cursor.getPrev方法来遍历游标获取数据
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
			LogUtil.LogInfo("getEveryItem处理出现异常");
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
	 * 遍历数据库中的所有记录，返回map
	 */
	public Map<String, String> getAll_map() {
		LogUtil.LogInfo("===========遍历数据库" + dbName + "中的所有数据==========");
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
			// 使用cursor.getPrev方法来遍历游标获取数据
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
			LogUtil.LogInfo("getEveryItem处理出现异常");
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
	 * 根据key值删除数据库中的一条记录
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
				// 1、使用cursor.getPrev方法来遍历游标获取数据
				TransactionConfig txConfig = new TransactionConfig();
				txConfig.setSerializableIsolation(true);
				txn = env.beginTransaction(null, txConfig);
				DatabaseEntry theKey;
				theKey = new DatabaseEntry(key.getBytes("UTF-8"));

				// 2、删除数据 并提交
				OperationStatus res = frontierDatabase.delete(txn, theKey);
				txn.commit();
				if (res == OperationStatus.SUCCESS) {
					LogUtil.LogInfo("从数据库" + dbName + "中删除:" + "key:" + key);
					success = true;
					return success;
				} else if (res == OperationStatus.KEYEMPTY) {
					LogUtil.LogInfo("没有从数据库" + dbName + "中找到:" + "key:" + key + "。无法删除");
				} else {
					LogUtil.LogInfo("删除操作失败，由于" + res.toString());
				}
				return false;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return false;
			} catch (LogException lockConflict) {
				LogUtil.LogInfo("删除操作失败，出现lockConflict异常");
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
	 * 关闭函数
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
