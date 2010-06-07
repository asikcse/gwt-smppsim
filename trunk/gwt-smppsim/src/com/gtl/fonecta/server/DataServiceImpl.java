package com.gtl.fonecta.server;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		// TODO remove static value of arguments[0], get it from configuration
		// file
		arguemnts[0] = "/home/devang/workspace_FDS/GWT-SMPPSim/conf/smppsim.props";
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

		MessageService messageService = new MessageServiceImpl();
		Map<String, String> map = new TreeMap<String, String>();	
		
		List<Message> listMessage = messageService.findAll();

		for (Message message : listMessage) {
			if (!map.containsKey("handsetNo")) {
				map.put("handsetNo", message.getSource_addr().toString());
			}

			if (map.containsKey("serviceNo")) {
				map.put("serviceNo", message.getDest_addr().toString());
			}
			String key = message.getMessage_type()
					+ message.getMsgId().toString();
			String value = "<font face='sans-serif' color='gray'>"
					+ message.getSend_time().toString().replace(".0", "").replace(" ", "&nbsp;")
					+ "&nbsp;&nbsp;[" + message.getSource_addr().toString() + "->"
					+ message.getDest_addr().toString() + "]"
					+ "</font> <br> <font face='sans-serif'>"
					+ message.getShort_message() + ". </font>";
			map.put(key, value);
		}

		return map;
	}

	@SuppressWarnings("unused")
	private Map<String, String> getMessageMap(String handsetNo, String serviceNo) {

		Map<String, String> map = new HashMap<String, String>();
		MessageService messageService = new MessageServiceImpl();
		List<Message> listMessage = messageService.findBySrcDestAddress(
				new Long(handsetNo), new Long(serviceNo));

		map.put("handsetNo", handsetNo);
		map.put("serviceNo", serviceNo);
		for (Message message : listMessage) {
			String key = message.getMessage_type()
					+ message.getMsgId().toString();
			String value = "<font face='sans-serif' color='gray'>"
					+ message.getSend_time().toString().replace(".0", "")
					+ "  [" + message.getSource_addr().toString() + "->"
					+ message.getDest_addr().toString() + "]"
					+ "</font> <br> <font face='sans-serif'>"
					+ message.getShort_message() + ". </font>";
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
		message.setSource_addr(new Long(handsetNo));
		message.setDest_addr(new Long(serviceNo));
		message.setShort_message(shortMessage);
		message.setMessage_type("MO");
		message.setSend_time(sendTime);

		MessageService messageService = new MessageServiceImpl();
		messageService.insertMoMessage(message);
	}
}
