package com.wry.service;

import java.util.List;

import com.wry.dao.VideoDAO;
import com.wry.log.LogUtil;
import com.wry.model.Video;

/**
 * ����video��service
 * 
 * @author Administrator
 *
 */
public class VideoService {
	private VideoDAO videoDAO = new VideoDAO();

	public void save(Video video) {
		videoDAO.save(video);
		LogUtil.LogInfo("��video_db���ݿ���룺" + video.toString());
	}

	public void delete(Integer id) {
		videoDAO.delete(id);
		LogUtil.LogInfo("��video_db���ݿ�ɾ����" + select(id).toString());
	}

	public void update(Video video) {
		videoDAO.update(video);
		LogUtil.LogInfo("��video_db���ݿ���£�" + video.toString());
	}

	public Video select(Integer id) {
		Video video = videoDAO.select(id);
		LogUtil.LogInfo("��video_db���ݿ���ң�" + video.toString());
		return video;
	}

	public List<Video> select() {
		LogUtil.LogInfo("��video_db���ݿ�����ȡ���м�¼");
		return videoDAO.select();
	}
}
