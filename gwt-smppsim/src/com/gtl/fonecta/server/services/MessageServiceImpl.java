package com.gtl.fonecta.server.services;

import java.util.List;

import com.gtl.fonecta.client.bean.Message;
import com.gtl.fonecta.server.dao.MessageDAO;

/**
 * @author devang
 * 
 */
public class MessageServiceImpl implements MessageService {

	/**
	 * Method to insert MO message.
	 * 
	 */
	@Override
	public void insertMoMessage(Message message) {
		MessageDAO messageDAO = new MessageDAO();
		// message.setMessage_type("MO");
		messageDAO.save(message);
	}

	/**
	 * Method to insert MT message.
	 * 
	 */
	@Override
	public void insertMtMessage(Message message) {
		MessageDAO messageDAO = new MessageDAO();
		message.setMessageType("MT");
		messageDAO.save(message);
	}

	/**
	 * Method to delete all messages.
	 * 
	 */
	@Override
	public void deleteAllMessage() {
		MessageDAO messageDAO = new MessageDAO();
		messageDAO.deleteAll();
	}

	/**
	 * Method to find all messages and return list of message.
	 * 
	 */
	@Override
	public List<Message> findAll() {
		MessageDAO messageDAO = new MessageDAO();
		List<Message> listMessage = messageDAO.findAll();
		return listMessage;
	}

	/**
	 * Method to find message with source address and destination address and
	 * returns list of message.
	 * 
	 */
	@Override
	public List<Message> findBySrcDestAddress(Long sourceAddr, Long destAddr) {
		MessageDAO messageDAO = new MessageDAO();
		List<Message> listMessage = messageDAO.findBySrcDestAddress(sourceAddr,
				destAddr);

		return listMessage;
	}

}
