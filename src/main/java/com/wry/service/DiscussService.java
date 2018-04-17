package com.wry.service;

import java.util.List;

import com.wry.dao.DiscussDAO;
import com.wry.log.LogUtil;
import com.wry.model.Discuss;

/**
 * 操作discuss的service
 * 
 * @author Administrator
 *
 */
public class DiscussService {
	private DiscussDAO disscussDAO = new DiscussDAO();

	public void save(Discuss discuss) {
		disscussDAO.save(discuss	);
		LogUtil.LogInfo("向discuss_db数据库插入：" + discuss.toString());
	}

	public void delete(Integer id) {
		disscussDAO.delete(id);
		LogUtil.LogInfo("从discuss_db数据库删除：" + select(id).toString());
	}

	public void update(Discuss discuss) {
		disscussDAO.update(discuss);
		LogUtil.LogInfo("从discuss_db数据库更新：" + discuss.toString());
	}

	public Discuss select(Integer id) {
		Discuss discuss = disscussDAO.select(id);
		LogUtil.LogInfo("从discuss_db数据库查找：" + discuss.toString());
		return discuss;
	}

	public List<Discuss> select() {
		LogUtil.LogInfo("从discuss_db数据库中提取所有记录");
		return disscussDAO.select();
	}
}
