package com.xiguakey.service;

import java.util.List;

import com.common.util.LogUtil;
import com.xiguakey.dao.VideoDao;
import com.xiguakey.model.Video;

/**
 * 操作video的service
 * 
 * @author Administrator
 *
 */
public class VideoService {
	private VideoDao VideoDao = new VideoDao();

	public void save(Video video) {
		VideoDao.save(video);
		LogUtil.LogInfo("向videoxigua_db数据库插入：" + video.toString());
	}

	public void delete(Integer id) {
		VideoDao.delete(id);
		LogUtil.LogInfo("从videoxigua_db数据库删除：" + select(id).toString());
	}

	public void update(Video video) {
		VideoDao.update(video);
		LogUtil.LogInfo("从videoxigua_db数据库更新：" + video.toString());
	}

	public Video select(Integer id) {
		Video video = VideoDao.select(id);
		LogUtil.LogInfo("从videoxigua_db数据库查找：" + video.toString());
		return video;
	}

	public List<Video> select() {
		LogUtil.LogInfo("从videoxigua_db数据库中提取所有记录");
		return VideoDao.select();
	}
}
