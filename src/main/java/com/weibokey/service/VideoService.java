package com.weibokey.service;

import java.util.List;

import com.common.util.LogUtil;
import com.weibokey.dao.VideoDao;
import com.weibokey.model.Video;

/**
 * ����video��service
 * 
 * @author Administrator
 *
 */
public class VideoService {
	private VideoDao videoDao = new VideoDao();

	public void save(Video video) {
		videoDao.save(video);
		LogUtil.LogInfo("��video_db���ݿ���룺" + video.toString());
	}

	public void delete(Integer id) {
		videoDao.delete(id);
		LogUtil.LogInfo("��video_db���ݿ�ɾ����" + select(id).toString());
	}

	public void update(Video video) {
		videoDao.update(video);
		LogUtil.LogInfo("��video_db���ݿ���£�" + video.toString());
	}

	public Video select(Integer id) {
		Video video = videoDao.select(id);
		LogUtil.LogInfo("��video_db���ݿ���ң�" + video.toString());
		return video;
	}

	public List<Video> select() {
		LogUtil.LogInfo("��video_db���ݿ�����ȡ���м�¼");
		return videoDao.select();
	}
}
