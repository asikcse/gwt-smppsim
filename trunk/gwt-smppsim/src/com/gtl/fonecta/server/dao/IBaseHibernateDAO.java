package com.gtl.fonecta.server.dao;

import org.hibernate.Session;

/**
 * Data access interface for domain model
 * 
 * @author devang
 */

public interface IBaseHibernateDAO {
	public Session getSession();
}