package com.wry.service;

import java.util.List;

import com.wry.dao.TranspondDAO;
import com.wry.log.LogUtil;
import com.wry.model.Transpond;

/**
 * 操作Transpond的service
 * 
 * @author Administrator
 *
 */
public class TranspondService {
	private TranspondDAO transpondDAO = new TranspondDAO();

	public void save(Transpond transpond) {
		transpondDAO.save(transpond);
		LogUtil.LogInfo("向transpond_db数据库插入：" + transpond.toString());
	}

	public void delete(Integer id) {
		transpondDAO.delete(id);
		LogUtil.LogInfo("从transpond_db数据库删除：" + select(id).toString());
	}

	public void update(Transpond transpond) {
		transpondDAO.update(transpond);
		LogUtil.LogInfo("从transpond_db数据库更新：" + transpond.toString());
	}

	public Transpond select(Integer id) {
		Transpond transpond = transpondDAO.select(id);
		LogUtil.LogInfo("从transpond_db数据库查找：" + transpond.toString());
		return transpond;
	}

	public List<Transpond> select() {
		LogUtil.LogInfo("从transpond_db数据库中提取所有记录");
		return transpondDAO.select();
	}

	public Transpond selectByName(String name) {
		Transpond transpond = transpondDAO.selectByName(name);
		if(transpond != null) {
			LogUtil.LogInfo("从transpond_db数据库查找：" + transpond.toString());
		}
		return transpond;
	}
}
