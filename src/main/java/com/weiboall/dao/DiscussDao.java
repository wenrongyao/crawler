package com.weiboall.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.weiboall.model.Discuss;


/**
 * 操作discuss数据库的工具类
 * 
 */
public class DiscussDao {
	private static SessionFactory sf = null;

	/**
	 * 单例模式产生session工厂
	 */
	@SuppressWarnings("deprecation")
	public DiscussDao() {
		if (sf == null) {
			Configuration config = new Configuration().configure("hibernate.cfg.xml");
			sf = config.buildSessionFactory();
		}
	}

	/**
	 * 保存实体类
	 * 
	 * @param discuss
	 */
	public void save(Discuss discuss) {
		Session session = sf.getCurrentSession();
		session.getTransaction().begin();
		session.save(discuss);
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
		Discuss discuss = new Discuss();
		discuss.setId(id);
		session.delete(discuss);
		session.getTransaction().commit();
	}

	/**
	 * 修改实体对象
	 * 
	 * @param video
	 */
	public void update(Discuss discuss) {
		Session session = sf.getCurrentSession();
		session.getTransaction().begin();
		session.update(discuss);
		session.getTransaction().commit();
	}

	/**
	 * 根据id查询单条记录
	 * 
	 * @param id
	 * @return
	 */
	public Discuss select(Integer id) {
		Session session = sf.getCurrentSession();
		session.getTransaction().begin();
		Discuss discuss = (Discuss) session.get(Discuss.class, id);
		session.getTransaction().commit();
		return discuss;
	}

	/**
	 * 查询全部记录
	 * 
	 * @return
	 */
	public List<Discuss> select() {
		Session session = sf.getCurrentSession();
		session.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Discuss> discussList = session.createQuery("select d from Discuss d").list();
		session.getTransaction().commit();
		return discussList;
	}

}
