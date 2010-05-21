package com.gtl.fonecta.client.bean;

import java.sql.Timestamp;

/**
 * @author devang
 * 
 */
public class Message {

	// Fields
	private Integer msgId;
	private Long source_addr;
	private Long dest_addr;
	private String short_message;
	private String message_type;
	private Timestamp send_time;

	// Constructors

	/**
	 * default Constructor
	 */
	public Message() {
	}

	/**
	 * Full constructor
	 * 
	 * @param msgId
	 * @param sourceAddr
	 * @param destAddr
	 * @param shortMessage
	 * @param messageType
	 * @param sendTime
	 */
	public Message(Integer msgId, Long sourceAddr, Long destAddr,
			String shortMessage, String messageType, Timestamp sendTime) {
		super();
		this.msgId = msgId;
		source_addr = sourceAddr;
		dest_addr = destAddr;
		short_message = shortMessage;
		message_type = messageType;
		send_time = sendTime;
	}

	// Property accessors

	/**
	 * @return the msgId
	 */
	public Integer getMsgId() {
		return msgId;
	}

	/**
	 * @param msgId
	 *            the msgId to set
	 */
	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}

	/**
	 * @return the source_addr
	 */
	public Long getSource_addr() {
		return source_addr;
	}

	/**
	 * @param sourceAddr
	 *            the source_addr to set
	 */
	public void setSource_addr(Long sourceAddr) {
		source_addr = sourceAddr;
	}

	/**
	 * @return the dest_addr
	 */
	public Long getDest_addr() {
		return dest_addr;
	}

	/**
	 * @param destAddr
	 *            the dest_addr to set
	 */
	public void setDest_addr(Long destAddr) {
		dest_addr = destAddr;
	}

	/**
	 * @return the short_message
	 */
	public String getShort_message() {
		return short_message;
	}

	/**
	 * @param shortMessage
	 *            the short_message to set
	 */
	public void setShort_message(String shortMessage) {
		short_message = shortMessage;
	}

	/**
	 * @return the message_type
	 */
	public String getMessage_type() {
		return message_type;
	}

	/**
	 * @param messageType
	 *            the message_type to set
	 */
	public void setMessage_type(String messageType) {
		message_type = messageType;
	}

	/**
	 * @return the send_time
	 */
	public Timestamp getSend_time() {
		return send_time;
	}

	/**
	 * @param sendTime
	 *            the send_time to set
	 */
	public void setSend_time(Timestamp sendTime) {
		send_time = sendTime;
	}
	
	public String toString(){		
		return msgId+"\t"+source_addr+"\t"+dest_addr+"\t"+ short_message +"\t"+message_type+"\t"+send_time; 
	}
}
