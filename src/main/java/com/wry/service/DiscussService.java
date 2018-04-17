package com.wry.service;

import java.util.List;

import com.wry.dao.DiscussDAO;
import com.wry.log.LogUtil;
import com.wry.model.Discuss;

/**
 * ����discuss��service
 * 
 * @author Administrator
 *
 */
public class DiscussService {
	private DiscussDAO disscussDAO = new DiscussDAO();

	public void save(Discuss discuss) {
		disscussDAO.save(discuss	);
		LogUtil.LogInfo("��discuss_db���ݿ���룺" + discuss.toString());
	}

	public void delete(Integer id) {
		disscussDAO.delete(id);
		LogUtil.LogInfo("��discuss_db���ݿ�ɾ����" + select(id).toString());
	}

	public void update(Discuss discuss) {
		disscussDAO.update(discuss);
		LogUtil.LogInfo("��discuss_db���ݿ���£�" + discuss.toString());
	}

	public Discuss select(Integer id) {
		Discuss discuss = disscussDAO.select(id);
		LogUtil.LogInfo("��discuss_db���ݿ���ң�" + discuss.toString());
		return discuss;
	}

	public List<Discuss> select() {
		LogUtil.LogInfo("��discuss_db���ݿ�����ȡ���м�¼");
		return disscussDAO.select();
	}
}
