package com.weiboall.service;

import java.util.List;

import com.common.util.LogUtil;
import com.weiboall.dao.TranspondDao;
import com.weiboall.model.Transpond;



/**
 * 操作Transpond的service
 * 
 * @author Administrator
 *
 */
public class TranspondService {
	private TranspondDao transpondDao = new TranspondDao();

	public void save(Transpond transpond) {
		transpondDao.save(transpond);
		LogUtil.LogInfo("向transpond_db数据库插入：" + transpond.toString());
	}

	public void delete(Integer id) {
		transpondDao.delete(id);
		LogUtil.LogInfo("从transpond_db数据库删除：" + select(id).toString());
	}

	public void update(Transpond transpond) {
		transpondDao.update(transpond);
		LogUtil.LogInfo("从transpond_db数据库更新：" + transpond.toString());
	}

	public Transpond select(Integer id) {
		Transpond transpond = transpondDao.select(id);
		LogUtil.LogInfo("从transpond_db数据库查找：" + transpond.toString());
		return transpond;
	}

	public List<Transpond> select() {
		LogUtil.LogInfo("从transpond_db数据库中提取所有记录");
		return transpondDao.select();
	}

	public Transpond selectByName(String name) {
		Transpond transpond = transpondDao.selectByName(name);
		if(transpond != null) {
			LogUtil.LogInfo("从transpond_db数据库查找：" + transpond.toString());
		}
		return transpond;
	}
}
