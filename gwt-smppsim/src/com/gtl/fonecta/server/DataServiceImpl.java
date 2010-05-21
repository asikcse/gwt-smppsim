package com.gtl.fonecta.server;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gtl.fonecta.client.DataService;
import com.gtl.fonecta.client.bean.Message;
import com.gtl.fonecta.server.dao.MessageDAO;
import com.seleniumsoftware.SMPPSim.SMPPSim;

/**
 * 
 * @author devang
 * 
 */
public class DataServiceImpl extends RemoteServiceServlet implements
		DataService {
	private static final Log log = LogFactory.getLog(DataServiceImpl.class);
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
		MessageDAO messageDAO = new MessageDAO();
		messageDAO.deleteAll();		
	}

	@Override
	public Map<String, String> getInitialData() {
		startSMPPSim();
		Map<String, String> map = new HashMap<String, String>();
		map = getMessageMap();
		return map;
	}

	@Override
	public Map<String, String> getInitialData(String handsetNo,
			String serviceNo, String shortMessage, Timestamp sendTime) {
		startSMPPSim();
		
		log.info("----------" + serviceNo + "\t" + handsetNo + "\t"+ shortMessage + "\t" + sendTime);
		insertMessage(handsetNo, serviceNo, shortMessage, sendTime);

		Map<String, String> map = getMessageMap();

		return map;
	}

	private Map<String, String> getMessageMap() {
		MessageDAO messageDAO = new MessageDAO();

		Map<String, String> map = new HashMap<String, String>();

		List<Message> listMessage = messageDAO.findAll();

		for (Message message : listMessage) {
			map.put("handsetNo", message.getSource_addr().toString());
			map.put("serviceNo", message.getDest_addr().toString());			

			String key = message.getMessage_type()
					+ message.getMsgId().toString();
			String value = "<font face='sans-serif' color='gray'>" + message.getSend_time().toString().replace(".0", "") + "  ["
					+ message.getSource_addr().toString() + "->"
					+ message.getDest_addr().toString() + "]" + "</font> <br> <font face='sans-serif'>"
					+ message.getShort_message()+". </font>";
			map.put(key, value);
		}
		return map;
	}

	private void insertMessage(String handsetNo, String serviceNo,
			String shortMessage, Timestamp sendTime) {
		MessageDAO messageDAO = new MessageDAO();
		Message message = new Message();
		message.setSource_addr(new Long(handsetNo));
		message.setDest_addr(new Long(serviceNo));
		message.setShort_message(shortMessage);
		message.setMessage_type("MO");
		message.setSend_time(sendTime);
		messageDAO.save(message);
	}

}
