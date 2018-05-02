package com.weibokey.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.weibokey.model.Transpond;

/**
 * 操作mysql数据库的工具类
 * 
 */
public class TranspondDao {
	private static SessionFactory sf = null;

	/**
	 * 单例模式产生session工厂
	 */
	@SuppressWarnings("deprecation")
	public TranspondDao() {
		if (sf == null) {
			Configuration config = new Configuration().configure("hibernate.cfg.xml");
			sf = config.buildSessionFactory();
		}
	}

	/**
	 * 保存实体类
	 * 
	 * @param transpond
	 */
	public void save(Transpond transpond) {
		Session session = sf.getCurrentSession();
		session.getTransaction().begin();
		session.save(transpond);
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
		Transpond transpond = new Transpond();
		transpond.setId(id);
		session.delete(transpond);
		session.getTransaction().commit();
	}

	/**
	 * 修改实体对象
	 * 
	 * @param transpond
	 */
	public void update(Transpond transpond) {
		Session session = sf.getCurrentSession();
		session.getTransaction().begin();
		session.update(transpond);
		session.getTransaction().commit();
	}

	/**
	 * 根据id查询单条记录
	 * 
	 * @param id
	 * @return
	 */
	public Transpond select(Integer id) {
		Session session = sf.getCurrentSession();
		session.getTransaction().begin();
		Transpond transpond = (Transpond) session.get(Transpond.class, id);
		session.getTransaction().commit();
		return transpond;
	}

	/**
	 * 查询全部记录
	 * 
	 * @return
	 */
	public List<Transpond> select() {
		Session session = sf.getCurrentSession();
		session.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Transpond> transpondList = session.createQuery("select t from Transpond t").list();
		session.getTransaction().commit();
		return transpondList;
	}

	/**
	 * 根据name查找transponde
	 * 
	 * @param name
	 * @return
	 */
	public Transpond selectByName(String name) {
		Session session = sf.getCurrentSession();
		session.getTransaction().begin();
		Transpond transpond = (Transpond) session.createQuery("select t from Transpond t where t.name = ?0")
				.setParameter(0, name).uniqueResult();
		session.getTransaction().commit();
		return transpond;
	}

}
