package com.weibokey.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.weibokey.model.Video;

/**
 * ����mysql���ݿ�Ĺ�����
 * 
 */
public class VideoDao {
	private static SessionFactory sf = null;

	/**
	 * ����ģʽ����session����
	 */
	@SuppressWarnings("deprecation")
	public VideoDao() {
		if (sf == null) {
			Configuration config = new Configuration().configure("hibernate.cfg.xml");
			sf = config.buildSessionFactory();
		}
	}

	/**
	 * ����ʵ����
	 * 
	 * @param video
	 */
	public void save(Video video) {
		Session session = sf.getCurrentSession();
		session.getTransaction().begin();
		session.save(video);
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
		Video video = new Video();
		video.setId(id);
		session.delete(video);
		session.getTransaction().commit();
	}

	/**
	 * �޸�ʵ�����
	 * 
	 * @param video
	 */
	public void update(Video video) {
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
	public Video select(Integer id) {
		Session session = sf.getCurrentSession();
		session.getTransaction().begin();
		Video video = (Video) session.get(Video.class, id);
		session.getTransaction().commit();
		return video;
	}

	/**
	 * ��ѯȫ����¼
	 * 
	 * @return
	 */
	public List<Video> select() {
		Session session = sf.getCurrentSession();
		session.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Video> videoList = session.createQuery("select v from Video v").list();
		session.getTransaction().commit();
		return videoList;
	}

}
