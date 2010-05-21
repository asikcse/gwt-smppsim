package com.gtl.fonecta.client;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.gtl.fonecta.client.bean.Message;
import com.gtl.fonecta.server.dao.MessageDAO;

/**
 * @author devang
 * 
 */
public class DerbyHibernateTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		 insertMessage();
		// deleteMessage();
		// deleteByID(2);
		// deleteAll();
		 selectMessage();

	}

	private static void selectMessage() {
		MessageDAO dao = new MessageDAO();
		List<Message> messageList = dao.findAll();
		for (Message message : messageList) {
			System.out.println(message.toString());
		}
	}

	@SuppressWarnings("deprecation")
	private static void insertMessage() {
		MessageDAO dao = new MessageDAO();
		System.out.println("Inserting Record");
		Message message = new Message();
		//message.setMsgId(new Integer(2));
		message.setSource_addr(new Long(9825300375L));
		message.setDest_addr(new Long(333355778L));
		message.setShort_message("This is Test Message x");
		message.setMessage_type("MO");
		Date now = new Date();
		Timestamp sendTime = new Timestamp(now.getYear(), now.getMonth(), now
				.getDate(), now.getHours(), now.getMinutes(), now.getSeconds(),
				0);

		message.setSend_time(sendTime);
		dao.save(message);
		System.out.println("Inserting..." + message.toString());
	}

	private static void deleteMessage() {
		MessageDAO dao = new MessageDAO();
		Message message = new Message();
		message.setMsgId(2);
		dao.delete(message);
	}

	private static void deleteByID(int id) {
		MessageDAO dao = new MessageDAO();
		dao.deleteById(id);
	}

	private static void deleteAll() {
		MessageDAO dao = new MessageDAO();
		dao.deleteAll();
	}
}
