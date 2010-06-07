package com.gtl.fonecta.server.services;

import java.util.List;

import com.gtl.fonecta.client.bean.Message;

/**
 * @author devang
 * 
 */
public interface MessageService {

	public void insertMoMessage(Message message);

	public void insertMtMessage(Message message);
	
	public void deleteAllMessage();
	
	public List<Message> findAll();
	
	public List<Message> findBySrcDestAddress(Long sourceAddr, Long destAddr);
	
}
