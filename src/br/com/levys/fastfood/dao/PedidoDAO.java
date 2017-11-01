package br.com.levys.fastfood.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.levys.fastfood.modelo.ItemPedido;
import br.com.levys.fastfood.modelo.Pedido;
import br.com.levys.fastfood.util.SessionFac;

public class PedidoDAO extends DaoGenerico<Pedido, Integer> {
	
	
	
	public boolean savePedido(Pedido p, List<ItemPedido> it) {
		Session session = new SessionFac().getSession();
		session.beginTransaction();
		boolean ret = false;

		try {
				double total=0;
			
				for(ItemPedido ip : it) {
					
					session.saveOrUpdate(ip);
					total+=ip.getProduto().getValor();
				}
				p.setValor_total(total);
			
				session.saveOrUpdate(p);

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
	
	public boolean deletePedido(Pedido p, List<ItemPedido> it) {
		Session session = new SessionFac().getSession();
		session.beginTransaction();
		boolean ret = false;

		try {
			
				for(ItemPedido ip : it) {
					
					session.delete(ip);
				}
			
				session.delete(p);

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
	
	
	public List<ItemPedido> getAllbyPedido(Pedido p) {

		Session session = new SessionFac().getSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(ItemPedido.class).add(Restrictions.eq("pedido", p));
		List<ItemPedido> list = crit.list();
		session.getTransaction().commit();
		session.close();
		if (list.size() > 0) {
			return list;
		} else {
			return new ArrayList<ItemPedido>();
		}

	}
	
		

}
