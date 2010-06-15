package com.gtl.fonecta.client;

import org.smslib.AGateway;
import org.smslib.IGatewayStatusNotification;
import org.smslib.IOutboundMessageNotification;
import org.smslib.Library;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.AGateway.GatewayStatuses;
import org.smslib.smpp.BindAttributes;
import org.smslib.smpp.BindAttributes.BindType;
import org.smslib.smpp.jsmpp.JSMPPGateway;

public class SendMtMessage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SendMtMessage app = new SendMtMessage();
		try {
			app.doIt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void doIt() throws Exception {
		System.out
				.println("Example: Send/Receive message through SMPP using JSMPP.");
		System.out.println(Library.getLibraryDescription());
		System.out.println("Version: " + Library.getLibraryVersion());
		Service srv = new Service();


		JSMPPGateway gateway = new JSMPPGateway("smpp", "localhost", 2775,
				new BindAttributes("smppclient1", "password", "cp",
						BindType.TRANSCEIVER));
		srv.addGateway(gateway);		
		srv.setGatewayStatusNotification(new GatewayStatusNotification());
		srv.setOutboundMessageNotification(new OutboundNotification());

		srv.startService();

		// Send a message.
		OutboundMessage msg = new OutboundMessage("+919825300375",
				"Hello from SMSLib and JSMPP... ");

		// Request Delivery Report
		msg.setStatusReport(true);
		srv.sendMessage(msg);

		System.out.println(msg);

		System.out.println("Now Sleeping - Hit <enter> to terminate.");
		System.in.read();
		srv.stopService();
	}

	public class OutboundNotification implements IOutboundMessageNotification {
		public void process(AGateway gateway, OutboundMessage msg) {
			System.out.println("Outbound handler called from Gateway: "
					+ gateway.getGatewayId());
			System.out.println(msg);
		}
	}
	
	public class GatewayStatusNotification implements
			IGatewayStatusNotification {
		public void process(AGateway gateway, GatewayStatuses oldStatus,
				GatewayStatuses newStatus) {
			System.out.println(">>> Gateway Status change for "
					+ gateway.getGatewayId() + ", OLD: " + oldStatus
					+ " -> NEW: " + newStatus);
		}
	}

}
