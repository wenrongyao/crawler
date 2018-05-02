package com.weibokey.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.weibokey.model.Video;

/**
 * 操作mysql数据库的工具类
 * 
 */
public class VideoDao {
	private static SessionFactory sf = null;

	/**
	 * 单例模式产生session工厂
	 */
	@SuppressWarnings("deprecation")
	public VideoDao() {
		if (sf == null) {
			Configuration config = new Configuration().configure("hibernate.cfg.xml");
			sf = config.buildSessionFactory();
		}
	}

	/**
	 * 保存实体类
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
	 * 删除实体对象
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
	 * 修改实体对象
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
	 * 根据id查询单条记录
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
	 * 查询全部记录
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
