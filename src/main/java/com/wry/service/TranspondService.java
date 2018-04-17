package com.wry.service;

import java.util.List;

import com.wry.dao.TranspondDAO;
import com.wry.log.LogUtil;
import com.wry.model.Transpond;

/**
 * ����Transpond��service
 * 
 * @author Administrator
 *
 */
public class TranspondService {
	private TranspondDAO transpondDAO = new TranspondDAO();

	public void save(Transpond transpond) {
		transpondDAO.save(transpond);
		LogUtil.LogInfo("��transpond_db���ݿ���룺" + transpond.toString());
	}

	public void delete(Integer id) {
		transpondDAO.delete(id);
		LogUtil.LogInfo("��transpond_db���ݿ�ɾ����" + select(id).toString());
	}

	public void update(Transpond transpond) {
		transpondDAO.update(transpond);
		LogUtil.LogInfo("��transpond_db���ݿ���£�" + transpond.toString());
	}

	public Transpond select(Integer id) {
		Transpond transpond = transpondDAO.select(id);
		LogUtil.LogInfo("��transpond_db���ݿ���ң�" + transpond.toString());
		return transpond;
	}

	public List<Transpond> select() {
		LogUtil.LogInfo("��transpond_db���ݿ�����ȡ���м�¼");
		return transpondDAO.select();
	}

	public Transpond selectByName(String name) {
		Transpond transpond = transpondDAO.selectByName(name);
		if(transpond != null) {
			LogUtil.LogInfo("��transpond_db���ݿ���ң�" + transpond.toString());
		}
		return transpond;
	}
}
