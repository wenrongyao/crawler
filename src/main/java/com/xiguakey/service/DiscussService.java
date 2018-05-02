package com.xiguakey.service;

import java.util.List;

import com.common.util.LogUtil;
import com.weibokey.dao.DiscussDao;
import com.weibokey.model.Discuss;


/**
 * 操作discuss的service
 * 
 * @author Administrator
 *
 */
public class DiscussService {
	private DiscussDao disscussDao = new DiscussDao();

	public void save(Discuss discuss) {
		disscussDao.save(discuss	);
		LogUtil.LogInfo("向discuss_db数据库插入：" + discuss.toString());
	}

	public void delete(Integer id) {
		disscussDao.delete(id);
		LogUtil.LogInfo("从discuss_db数据库删除：" + select(id).toString());
	}

	public void update(Discuss discuss) {
		disscussDao.update(discuss);
		LogUtil.LogInfo("从discuss_db数据库更新：" + discuss.toString());
	}

	public Discuss select(Integer id) {
		Discuss discuss = disscussDao.select(id);
		LogUtil.LogInfo("从discuss_db数据库查找：" + discuss.toString());
		return discuss;
	}

	public List<Discuss> select() {
		LogUtil.LogInfo("从discuss_db数据库中提取所有记录");
		return disscussDao.select();
	}
}
