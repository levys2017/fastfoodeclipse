package br.com.levys.fastfood.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.levys.fastfood.util.SessionFac;

public class DaoGenerico<T, I extends Serializable> {

	public List<T> getAll(Class<T> classe) {

		Session session = new SessionFac().getSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(classe);
		List<T> list = crit.list();
		session.getTransaction().commit();
		session.close();
		if (list.size() > 0) {
			return list;
		} else {
			return new ArrayList<T>();
		}

	}
	
	
	
	public List<T> getAllOrderAsc(Class<T> classe, String ordemby) {

		Session session = new SessionFac().getSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(classe).addOrder(Order.desc(ordemby));
		List<T> list = crit.list();
		session.getTransaction().commit();
		session.close();
		if (list.size() > 0) {
			return list;
		} else {
			return new ArrayList<T>();
		}

	}
	
	public List<T> getAllOrderDesc(Class<T> classe, String ordemby) {

		Session session = new SessionFac().getSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(classe).addOrder(
				Order.desc(ordemby));
		List<T> list = crit.list();
		session.getTransaction().commit();
		session.close();
		if (list.size() > 0) {
			return list;
		} else {
			return new ArrayList<T>();
		}

	}

	public T getById(Class<T> classe, I pk, String id) {
		Session session = new SessionFac().getSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(classe).add(
				Restrictions.eq(id, pk));
		List<T> list = crit.list();
		session.getTransaction().commit();
		session.close();
		if (list.size() > 0) {
			return list.get(0);

		} else {
			return null;
		}
	}

	public List<T> getAllByObj(Class<T> classe, Class<T> obj, String objname) {
		Session session = new SessionFac().getSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(classe).add(
				Restrictions.eq(objname, obj));
		List<T> list = crit.list();
		session.getTransaction().commit();
		session.close();
		if (list.size() > 0) {
			return list;

		} else {
			return new ArrayList<T>();
		}
	}

	public boolean save(T entity) {
		Session session = new SessionFac().getSession();
		session.beginTransaction();
		boolean ret = false;

		try {

			session.saveOrUpdate(entity);

			ret = true;
			session.getTransaction().commit();
		} catch (Exception e) {
			ret = false;
			session.getTransaction().rollback();

			e.printStackTrace();
			// TODO: handle exception
		} finally {
			session.close();
		}
		return ret;

	}

	public boolean update(T entity) {
		Session session = new SessionFac().getSession();
		session.beginTransaction();
		boolean ret = false;

		try {

			session.saveOrUpdate(entity);

			ret = true;
			session.getTransaction().commit();
		} catch (Exception e) {
			ret = false;
			session.getTransaction().rollback();

			e.printStackTrace();
			// TODO: handle exception
		} finally {
			session.close();
		}
		return ret;

	}

	public boolean delete(T entity) {
		Session session = new SessionFac().getSession();
		session.beginTransaction();
		boolean ret = false;

		try {

			session.delete(entity);

			ret = true;
			session.getTransaction().commit();
		} catch (Exception e) {
			ret = false;
			session.getTransaction().rollback();

			e.printStackTrace();
			// TODO: handle exception
		} finally {
			session.close();
		}
		return ret;

	}

}
