package com.gtl.fonecta.server.services;


import java.util.List;

import com.gtl.fonecta.client.bean.Message;
import com.gtl.fonecta.server.dao.MessageDAO;

/**
 * @author devang
 * 
 */
public class MessageServiceImpl implements MessageService {

	@Override
	public void insertMoMessage(Message message) {
		MessageDAO messageDAO = new MessageDAO();
		message.setMessage_type("MO");
		messageDAO.save(message);
	}

	@Override
	public void insertMtMessage(Message message) {
		MessageDAO messageDAO = new MessageDAO();
		message.setMessage_type("MT");		
		messageDAO.save(message);
	}

	@Override
	public void deleteAllMessage() {
		MessageDAO messageDAO = new MessageDAO();
		messageDAO.deleteAll();
	}

	@Override
	public List<Message> findAll() {
		MessageDAO messageDAO = new MessageDAO();
		List<Message> listMessage = messageDAO.findAll();
		return listMessage;
	}

	@Override
	public List<Message> findBySrcDestAddress(Long sourceAddr, Long destAddr) {
		MessageDAO messageDAO = new MessageDAO();
		List<Message> listMessage = messageDAO.findBySrcDestAddress(sourceAddr,
				destAddr);

		return listMessage;
	}

}
