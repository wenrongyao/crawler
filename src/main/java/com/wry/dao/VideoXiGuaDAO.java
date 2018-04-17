package com.wry.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.wry.model.VideoXiGua;

/**
 * ����mysql���ݿ�Ĺ�����
 * 
 */
public class VideoXiGuaDAO {
	private static SessionFactory sf = null;

	/**
	 * ����ģʽ����session����
	 */
	@SuppressWarnings("deprecation")
	public VideoXiGuaDAO() {
		if (sf == null) {
			Configuration config = new Configuration().configure("hibernate.cfg.xml");
			config.setNamingStrategy(new MyNameStrategy()); 
			sf = config.buildSessionFactory();
		}
	}

	/**
	 * ����ʵ����
	 * 
	 * @param videoXiGua
	 */
	public void save(VideoXiGua videoXiGua) {
		Session session = sf.getCurrentSession();
		session.getTransaction().begin();
		session.save(videoXiGua);
		session.getTransaction().commit();
	}

	/**
	 * ɾ��ʵ�����
	 * 
	 * @param id
	 */
	public void delete(Integer id) {
		Session session = sf.getCurrentSession();
		session.getTransaction().begin();
		VideoXiGua video = new VideoXiGua();
		video.setId(id);
		session.delete(video);
		session.getTransaction().commit();
	}

	/**
	 * �޸�ʵ�����
	 * 
	 * @param video
	 */
	public void update(VideoXiGua video) {
		Session session = sf.getCurrentSession();
		session.getTransaction().begin();
		session.update(video);
		session.getTransaction().commit();
	}

	/**
	 * ����id��ѯ������¼
	 * 
	 * @param id
	 * @return
	 */
	public VideoXiGua select(Integer id) {
		Session session = sf.getCurrentSession();
		session.getTransaction().begin();
		VideoXiGua video = (VideoXiGua) session.get(VideoXiGua.class, id);
		session.getTransaction().commit();
		return video;
	}

	/**
	 * ��ѯȫ����¼
	 * 
	 * @return
	 */
	public List<VideoXiGua> select() {
		Session session = sf.getCurrentSession();
		session.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<VideoXiGua> videoList = session.createQuery("select v from Video v").list();
		session.getTransaction().commit();
		return videoList;
	}

}
