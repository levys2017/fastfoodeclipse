package br.com.levys.fastfood.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.levys.fastfood.modelo.Usuario;
import br.com.levys.fastfood.util.SessionFac;

public class UsuarioDAO extends DaoGenerico<Usuario, Integer> {
	

	
	
	public Usuario getAllbyEmail( String email) {

		Session session = new SessionFac().getSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(Usuario.class)

				.add(Restrictions.eq("email", email));
		List<Usuario> list = crit.list();
		session.getTransaction().commit();
		session.close();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}

	}
	
	

	
	
	public Usuario Login( String email, String senha) {

		Session session = new SessionFac().getSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(Usuario.class)

				.add(Restrictions.eq("email", email)).add(Restrictions.eq("senha", senha));
		List<Usuario> list = crit.list();
		session.getTransaction().commit();
		session.close();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}

	}
	
	
		

}
