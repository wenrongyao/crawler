package com.weibokey.service;

import java.util.List;

import com.common.util.LogUtil;
import com.weibokey.dao.VideoDao;
import com.weibokey.model.Video;

/**
 * 操作video的service
 * 
 * @author Administrator
 *
 */
public class VideoService {
	private VideoDao videoDao = new VideoDao();

	public void save(Video video) {
		videoDao.save(video);
		LogUtil.LogInfo("向video_db数据库插入：" + video.toString());
	}

	public void delete(Integer id) {
		videoDao.delete(id);
		LogUtil.LogInfo("从video_db数据库删除：" + select(id).toString());
	}

	public void update(Video video) {
		videoDao.update(video);
		LogUtil.LogInfo("从video_db数据库更新：" + video.toString());
	}

	public Video select(Integer id) {
		Video video = videoDao.select(id);
		LogUtil.LogInfo("从video_db数据库查找：" + video.toString());
		return video;
	}

	public List<Video> select() {
		LogUtil.LogInfo("从video_db数据库中提取所有记录");
		return videoDao.select();
	}
}
