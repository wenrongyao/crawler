package com.wry.service;

import java.util.List;

import com.wry.dao.VideoXiGuaDAO;
import com.wry.log.LogUtil;
import com.wry.model.VideoXiGua;

/**
 * ����video��service
 * 
 * @author Administrator
 *
 */
public class VideoXiGuaService {
	private VideoXiGuaDAO videoXiGuaDAO = new VideoXiGuaDAO();

	public void save(VideoXiGua video) {
		videoXiGuaDAO.save(video);
		LogUtil.LogInfo("��videoxigua_db���ݿ���룺" + video.toString());
	}

	public void delete(Integer id) {
		videoXiGuaDAO.delete(id);
		LogUtil.LogInfo("��videoxigua_db���ݿ�ɾ����" + select(id).toString());
	}

	public void update(VideoXiGua video) {
		videoXiGuaDAO.update(video);
		LogUtil.LogInfo("��videoxigua_db���ݿ���£�" + video.toString());
	}

	public VideoXiGua select(Integer id) {
		VideoXiGua video = videoXiGuaDAO.select(id);
		LogUtil.LogInfo("��videoxigua_db���ݿ���ң�" + video.toString());
		return video;
	}

	public List<VideoXiGua> select() {
		LogUtil.LogInfo("��videoxigua_db���ݿ�����ȡ���м�¼");
		return videoXiGuaDAO.select();
	}
}
