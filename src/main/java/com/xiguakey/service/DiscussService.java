package com.xiguakey.service;

import java.util.List;

import com.common.util.LogUtil;
import com.weibokey.dao.DiscussDao;
import com.weibokey.model.Discuss;


/**
 * ����discuss��service
 * 
 * @author Administrator
 *
 */
public class DiscussService {
	private DiscussDao disscussDao = new DiscussDao();

	public void save(Discuss discuss) {
		disscussDao.save(discuss	);
		LogUtil.LogInfo("��discuss_db���ݿ���룺" + discuss.toString());
	}

	public void delete(Integer id) {
		disscussDao.delete(id);
		LogUtil.LogInfo("��discuss_db���ݿ�ɾ����" + select(id).toString());
	}

	public void update(Discuss discuss) {
		disscussDao.update(discuss);
		LogUtil.LogInfo("��discuss_db���ݿ���£�" + discuss.toString());
	}

	public Discuss select(Integer id) {
		Discuss discuss = disscussDao.select(id);
		LogUtil.LogInfo("��discuss_db���ݿ���ң�" + discuss.toString());
		return discuss;
	}

	public List<Discuss> select() {
		LogUtil.LogInfo("��discuss_db���ݿ�����ȡ���м�¼");
		return disscussDao.select();
	}
}
