package com.gtl.fonecta.server;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gtl.fonecta.client.DataService;
import com.gtl.fonecta.client.bean.Message;
import com.gtl.fonecta.server.services.MessageService;
import com.gtl.fonecta.server.services.MessageServiceImpl;
import com.seleniumsoftware.SMPPSim.SMPPSim;

/**
 * 
 * @author devang
 * 
 */
public class DataServiceImpl extends RemoteServiceServlet implements
		DataService {

	private static final long serialVersionUID = 1L;
	boolean isRunning = false;

	/**
	 * method to start SMPPSim
	 */
	private void startSMPPSim() {
		String[] arguemnts = new String[1];
		String configFile = "";
				
		ResourceBundle bundle = ResourceBundle.getBundle("com.gtl.fonecta.configuration");
		configFile = bundle.getString("CONFIG_FILE");		

		
		arguemnts[0] = configFile;
		try {
			if (!isRunning) {
				SMPPSim.main(arguemnts);
				isRunning = true;
				truncateMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void truncateMessage() {
		MessageService messageService = new MessageServiceImpl();
		messageService.deleteAllMessage();
	}

	@Override
	public Map<String, String> getInitialData() {
		startSMPPSim();
		Map<String, String> map = new HashMap<String, String>();

		map = getMessageMap();
		return map;
	}

	private Map<String, String> getMessageMap() {

		ResourceBundle bundle = ResourceBundle.getBundle("com.gtl.fonecta.configuration");
		String host = null;
		String port ;		
		host = bundle.getString("HOST");
		port =  bundle.getString("HTTP_PORT");
		
		
		MessageService messageService = new MessageServiceImpl();
		Map<String, String> map = new TreeMap<String, String>();
		
		map.put("host", host);
		map.put("port", port);
		
		List<Message> listMessage = messageService.findAll();

		for (Message message : listMessage) {
			if (!map.containsKey("handsetNo")) {
				map.put("handsetNo", message.getSourceAddr().toString());
			}

			if (map.containsKey("serviceNo")) {
				map.put("serviceNo", message.getDestAddr().toString());
			}
			String key = message.getMessageType()
					+ message.getMsgId().toString();
			String value = "<font face='sans-serif' color='gray'>"
					+ message.getSendTime().toString().replace(".0", "").replace(" ", "&nbsp;")
					+ "&nbsp;&nbsp;[" + message.getSourceAddr().toString() + "->"
					+ message.getDestAddr().toString() + "]"
					+ "</font> <br> <font face='sans-serif'>"
					+ message.getShortMessage() + ". </font>";
			map.put(key, value);
		}

		return map;
	}

	@SuppressWarnings("unused")
	private Map<String, String> getMessageMap(String handsetNo, String serviceNo) {
		ResourceBundle bundle = ResourceBundle.getBundle("com.gtl.fonecta.configuration");
		String host = bundle.getString("HOST");
		String port =  bundle.getString("HTTP_PORT");;		
				
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("host", host);
		map.put("port", port);		
		
		MessageService messageService = new MessageServiceImpl();
		List<Message> listMessage = messageService.findBySrcDestAddress(
				new Long(handsetNo), new Long(serviceNo));

		map.put("handsetNo", handsetNo);
		map.put("serviceNo", serviceNo);
		for (Message message : listMessage) {
			String key = message.getMessageType()
					+ message.getMsgId().toString();
			String value = "<font face='sans-serif' color='gray'>"
					+ message.getSendTime().toString().replace(".0", "")
					+ "  [" + message.getSourceAddr().toString() + "->"
					+ message.getDestAddr().toString() + "]"
					+ "</font> <br> <font face='sans-serif'>"
					+ message.getShortMessage() + ". </font>";
			map.put(key, value);
		}

		return map;
	}

	@Override
	public void insertMessage(String handsetNo, String serviceNo,
			String shortMessage, Timestamp sendTime) {

		saveMessage(handsetNo, serviceNo, shortMessage, sendTime);
	}

	private void saveMessage(String handsetNo, String serviceNo,
			String shortMessage, Timestamp sendTime) {

		Message message = new Message();
		message.setSourceAddr(new Long(handsetNo));
		message.setDestAddr(new Long(serviceNo));
		message.setShortMessage(shortMessage);
		message.setMessageType("MO");
		message.setSendTime(sendTime);

		MessageService messageService = new MessageServiceImpl();
		messageService.insertMoMessage(message);
	}
}
