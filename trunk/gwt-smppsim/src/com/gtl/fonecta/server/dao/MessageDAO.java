package com.gtl.fonecta.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gtl.fonecta.client.bean.Message;

public class MessageDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(MessageDAO.class);

	public void save(Message transientInstance) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			session.save(transientInstance);
			transaction.commit();
			log.info("inserted 1 message");
		} catch (Exception e) {
			log.error(e);
			log.error(e);
			transaction.rollback();
		} finally {
			session.clear();
			session.flush();
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Message> findAll() {
		List<Message> messageList = new ArrayList<Message>();
		Session session = null;
		try {
			log.info("Finding all messages");
			session = getSession();
			String queryString = "from Message";
			Query queryObject = session.createQuery(queryString);
			messageList = queryObject.list();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			session.clear();
			session.flush();
			session.close();
		}
		return messageList;
	}

	public Message findById(int msgId) {
		Message message = null;
		Session session = null;
		try {
			log.info("Finding message id :" + msgId);
			session = getSession();
			String queryString = "from Message where msgId=?";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, msgId);
			queryObject.executeUpdate();
		} catch (Exception e) {
			log.error(e);
		}
		return message;
	}

	public void delete(Message persistentInstance) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			session.delete(persistentInstance);
			transaction.commit();
			log.info("delete message with msgID "
					+ persistentInstance.getMsgId());
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			session.clear();
			session.flush();
			session.close();
		}
	}

	public void deleteById(int id) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String queryString = "delete from Message where id=?";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, id);
			queryObject.executeUpdate();
			transaction.commit();
			log.info("delete message with msgID " + id);
		} catch (Exception e) {
			log.error(e);
			transaction.rollback();
		} finally {
			session.clear();
			session.flush();
			session.close();
		}
	}

	public void deleteAll() {
		Session session = null;
		Transaction transaction = null;
		try {
			log.info("Removing all messages...");
			session = getSession();
			transaction = session.beginTransaction();
			String queryString = "delete from Message";
			Query queryObject = session.createQuery(queryString);
			queryObject.executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			log.error(e);
			transaction.rollback();
		}
	}
}
