package br.com.levys.fastfood.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.levys.fastfood.modelo.Cliente;
import br.com.levys.fastfood.modelo.Usuario;
import br.com.levys.fastfood.util.SessionFac;

public class ClienteDAO extends DaoGenerico<Cliente, Integer> {
	
	
	public Cliente getAllbyUsuario(Usuario u) {

		Session session = new SessionFac().getSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(Cliente.class).add(Restrictions.eq("login", u));
		List<Cliente> list = crit.list();
		session.getTransaction().commit();
		session.close();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new Cliente();
		}

	}
		

}
