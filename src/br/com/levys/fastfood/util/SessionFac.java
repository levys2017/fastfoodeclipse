package br.com.levys.fastfood.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class SessionFac {
	
	private static final SessionFactory sessionFactory;
	private static final ThreadLocal<Session> threadLocal=
	new ThreadLocal<Session>();
	
	static{
		try{
		sessionFactory = new AnnotationConfiguration()
		. configure ("/hibernate.cfg.xml").buildSessionFactory( );
		}catch(Throwable t){
		throw new ExceptionInInitializerError(t);
		}}
	
		public static Session getSession( ){
		Session session = (Session) threadLocal.get( );
		session = sessionFactory.openSession( );
		threadLocal.set(session);
		return session;
		}

}