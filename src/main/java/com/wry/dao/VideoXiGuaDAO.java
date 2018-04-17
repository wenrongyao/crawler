package com.wry.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.wry.model.VideoXiGua;

/**
 * 操作mysql数据库的工具类
 * 
 */
public class VideoXiGuaDAO {
	private static SessionFactory sf = null;

	/**
	 * 单例模式产生session工厂
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
	 * 保存实体类
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
	 * 删除实体对象
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
	 * 修改实体对象
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
	 * 根据id查询单条记录
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
	 * 查询全部记录
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
