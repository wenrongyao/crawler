package com.weiboall.service;

import java.util.List;

import com.common.util.LogUtil;
import com.weiboall.dao.TranspondDao;
import com.weiboall.model.Transpond;



/**
 * ����Transpond��service
 * 
 * @author Administrator
 *
 */
public class TranspondService {
	private TranspondDao transpondDao = new TranspondDao();

	public void save(Transpond transpond) {
		transpondDao.save(transpond);
		LogUtil.LogInfo("��transpond_db���ݿ���룺" + transpond.toString());
	}

	public void delete(Integer id) {
		transpondDao.delete(id);
		LogUtil.LogInfo("��transpond_db���ݿ�ɾ����" + select(id).toString());
	}

	public void update(Transpond transpond) {
		transpondDao.update(transpond);
		LogUtil.LogInfo("��transpond_db���ݿ���£�" + transpond.toString());
	}

	public Transpond select(Integer id) {
		Transpond transpond = transpondDao.select(id);
		LogUtil.LogInfo("��transpond_db���ݿ���ң�" + transpond.toString());
		return transpond;
	}

	public List<Transpond> select() {
		LogUtil.LogInfo("��transpond_db���ݿ�����ȡ���м�¼");
		return transpondDao.select();
	}

	public Transpond selectByName(String name) {
		Transpond transpond = transpondDao.selectByName(name);
		if(transpond != null) {
			LogUtil.LogInfo("��transpond_db���ݿ���ң�" + transpond.toString());
		}
		return transpond;
	}
}
