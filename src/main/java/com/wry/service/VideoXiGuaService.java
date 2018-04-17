package com.wry.service;

import java.util.List;

import com.wry.dao.VideoXiGuaDAO;
import com.wry.log.LogUtil;
import com.wry.model.VideoXiGua;

/**
 * 操作video的service
 * 
 * @author Administrator
 *
 */
public class VideoXiGuaService {
	private VideoXiGuaDAO videoXiGuaDAO = new VideoXiGuaDAO();

	public void save(VideoXiGua video) {
		videoXiGuaDAO.save(video);
		LogUtil.LogInfo("向videoxigua_db数据库插入：" + video.toString());
	}

	public void delete(Integer id) {
		videoXiGuaDAO.delete(id);
		LogUtil.LogInfo("从videoxigua_db数据库删除：" + select(id).toString());
	}

	public void update(VideoXiGua video) {
		videoXiGuaDAO.update(video);
		LogUtil.LogInfo("从videoxigua_db数据库更新：" + video.toString());
	}

	public VideoXiGua select(Integer id) {
		VideoXiGua video = videoXiGuaDAO.select(id);
		LogUtil.LogInfo("从videoxigua_db数据库查找：" + video.toString());
		return video;
	}

	public List<VideoXiGua> select() {
		LogUtil.LogInfo("从videoxigua_db数据库中提取所有记录");
		return videoXiGuaDAO.select();
	}
}
