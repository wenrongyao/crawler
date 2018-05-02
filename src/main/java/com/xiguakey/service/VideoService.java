package com.xiguakey.service;

import java.util.List;

import com.common.util.LogUtil;
import com.xiguakey.dao.VideoDao;
import com.xiguakey.model.Video;

/**
 * ����video��service
 * 
 * @author Administrator
 *
 */
public class VideoService {
	private VideoDao VideoDao = new VideoDao();

	public void save(Video video) {
		VideoDao.save(video);
		LogUtil.LogInfo("��videoxigua_db���ݿ���룺" + video.toString());
	}

	public void delete(Integer id) {
		VideoDao.delete(id);
		LogUtil.LogInfo("��videoxigua_db���ݿ�ɾ����" + select(id).toString());
	}

	public void update(Video video) {
		VideoDao.update(video);
		LogUtil.LogInfo("��videoxigua_db���ݿ���£�" + video.toString());
	}

	public Video select(Integer id) {
		Video video = VideoDao.select(id);
		LogUtil.LogInfo("��videoxigua_db���ݿ���ң�" + video.toString());
		return video;
	}

	public List<Video> select() {
		LogUtil.LogInfo("��videoxigua_db���ݿ�����ȡ���м�¼");
		return VideoDao.select();
	}
}
