package com.wry.service;

import java.util.List;

import com.wry.dao.VideoDAO;
import com.wry.log.LogUtil;
import com.wry.model.Video;

/**
 * 操作video的service
 * 
 * @author Administrator
 *
 */
public class VideoService {
	private VideoDAO videoDAO = new VideoDAO();

	public void save(Video video) {
		videoDAO.save(video);
		LogUtil.LogInfo("向video_db数据库插入：" + video.toString());
	}

	public void delete(Integer id) {
		videoDAO.delete(id);
		LogUtil.LogInfo("从video_db数据库删除：" + select(id).toString());
	}

	public void update(Video video) {
		videoDAO.update(video);
		LogUtil.LogInfo("从video_db数据库更新：" + video.toString());
	}

	public Video select(Integer id) {
		Video video = videoDAO.select(id);
		LogUtil.LogInfo("从video_db数据库查找：" + video.toString());
		return video;
	}

	public List<Video> select() {
		LogUtil.LogInfo("从video_db数据库中提取所有记录");
		return videoDAO.select();
	}
}
