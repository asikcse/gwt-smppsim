package com.gtl.fonecta.server.dao;

import org.hibernate.Session;

import com.gtl.fonecta.server.HibernateSessionFactory;

/**
 * Data access object (DAO) for domain model
 * 
 * @author devang
 */
public class BaseHibernateDAO implements IBaseHibernateDAO {

	public Session getSession() {
		return HibernateSessionFactory.getSession();
	}

}