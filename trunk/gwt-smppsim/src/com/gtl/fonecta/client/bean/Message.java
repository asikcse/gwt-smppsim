package com.gtl.fonecta.client.bean;

import java.sql.Timestamp;

/**
 * @author devang
 * 
 */
public class Message {

	// Fields
	private Integer msgId;
	private Long sourceAddr;
	private Long destAddr;
	private String shortMessage;
	private String messageType;
	private Timestamp sendTime;

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
		this.sourceAddr = sourceAddr;
		this.destAddr = destAddr;
		this.shortMessage = shortMessage;
		this.messageType = messageType;
		this.sendTime = sendTime;
	}

	// Property accessors
	
	/**
	 * @return the msgId
	 */
	public Integer getMsgId() {
		return msgId;
	}

	/**
	 * @param msgId the msgId to set
	 */
	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}

	/**
	 * @return the sourceAddr
	 */
	public Long getSourceAddr() {
		return sourceAddr;
	}

	/**
	 * @param sourceAddr the sourceAddr to set
	 */
	public void setSourceAddr(Long sourceAddr) {
		this.sourceAddr = sourceAddr;
	}

	/**
	 * @return the destAddr
	 */
	public Long getDestAddr() {
		return destAddr;
	}

	/**
	 * @param destAddr the destAddr to set
	 */
	public void setDestAddr(Long destAddr) {
		this.destAddr = destAddr;
	}

	/**
	 * @return the shortMessage
	 */
	public String getShortMessage() {
		return shortMessage;
	}

	/**
	 * @param shortMessage the shortMessage to set
	 */
	public void setShortMessage(String shortMessage) {
		this.shortMessage = shortMessage;
	}

	/**
	 * @return the messageType
	 */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	/**
	 * @return the sendTime
	 */
	public Timestamp getSendTime() {
		return sendTime;
	}

	/**
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}
	
	public String toString(){		
		return msgId+"\t"+sourceAddr+"\t"+destAddr+"\t"+ shortMessage +"\t"+messageType+"\t"+sendTime; 
	}

}
