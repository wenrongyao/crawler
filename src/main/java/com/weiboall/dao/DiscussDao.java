package com.weiboall.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.weiboall.model.Discuss;


/**
 * ����discuss���ݿ�Ĺ�����
 * 
 */
public class DiscussDao {
	private static SessionFactory sf = null;

	/**
	 * ����ģʽ����session����
	 */
	@SuppressWarnings("deprecation")
	public DiscussDao() {
		if (sf == null) {
			Configuration config = new Configuration().configure("hibernate.cfg.xml");
			sf = config.buildSessionFactory();
		}
	}

	/**
	 * ����ʵ����
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
	 * ɾ��ʵ�����
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
	 * �޸�ʵ�����
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
	 * ����id��ѯ������¼
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
	 * ��ѯȫ����¼
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
