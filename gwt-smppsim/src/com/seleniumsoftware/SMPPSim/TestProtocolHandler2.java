/****************************************************************************
 * TestProtocolHandler2.java
 *
 * Copyright (C) Selenium Software Ltd 2006
 *
 * This file is part of SMPPSim.
 *
 * SMPPSim is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * SMPPSim is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SMPPSim; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * @author martin@seleniumsoftware.com
 * http://www.woolleynet.com
 * http://www.seleniumsoftware.com
 * $Header: /var/cvsroot/SMPPSim2/src/java/com/seleniumsoftware/SMPPSim/TestProtocolHandler2.java,v 1.1 2009/11/12 17:02:56 martin Exp $
 ****************************************************************************/

package com.seleniumsoftware.SMPPSim;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.seleniumsoftware.SMPPSim.pdu.DestAddress;
import com.seleniumsoftware.SMPPSim.pdu.DestAddressSME;
import com.seleniumsoftware.SMPPSim.pdu.PduConstants;
import com.seleniumsoftware.SMPPSim.pdu.SubmitMulti;
import com.seleniumsoftware.SMPPSim.pdu.SubmitMultiResp;
import com.seleniumsoftware.SMPPSim.pdu.SubmitSM;
import com.seleniumsoftware.SMPPSim.pdu.SubmitSMResp;
import com.seleniumsoftware.SMPPSim.pdu.UnsuccessSME;
import com.seleniumsoftware.SMPPSim.util.LoggingUtilities;

public class TestProtocolHandler2 extends StandardProtocolHandler {
	private static Logger logger = Logger
			.getLogger("com.seleniumsoftware.smppsim");

	public TestProtocolHandler2() {
	}

	public String getName() {
		return ("TestProtocolHandler1");
	}

	/**
	 * Custom variation of submitSM handler. This implementation will not
	 * respond to SUBMIT_SM PDUs where the destination MSISDN starts with 44
	 */
	void getSubmitSMResponse(byte[] message, int len) throws Exception {
		LoggingUtilities.hexDump("Custom SUBMIT_SM:", message, len);
		byte[] resp_message;
		SubmitSM smppmsg = new SubmitSM();
		smppmsg.demarshall(message);
		if (Smsc.isDecodePdus())
			LoggingUtilities.logDecodedPdu(smppmsg);

		// now make the response object

		SubmitSMResp smppresp = new SubmitSMResp(smppmsg);

		// Validate session
		if ((!session.isBound()) || (!session.isTransmitter())) {
			logger
					.warning("Invalid bind state. Must be bound as transmitter for this PDU");
			wasInvalidBindState = true;
			smsc.incSubmitSmERR();
			connection.writeResponse(smppresp.errorResponse(smppresp
					.getCmd_id(), PduConstants.ESME_RINVBNDSTS, smppresp
					.getSeq_no()));
		}

		// Check MSISDN
		logger.info("Checking destination_addr:"
				+ smppmsg.getDestination_addr());
		if (!smppmsg.getDestination_addr().startsWith("44")) {
			resp_message = smppresp.marshall();
			LoggingUtilities.hexDump("SUBMIT_SM_RESP:", resp_message,
					resp_message.length);
			if (Smsc.isDecodePdus())
				LoggingUtilities.logDecodedPdu(smppresp);
			smsc.incSubmitSmOK();
			connection.writeResponse(resp_message);
			// If loopback is switched on, have an SMPPReceiver object deliver
			// this message back to the client
			if (SMPPSim.isLoopback()) {
				smsc.doLoopback(smppmsg);
			}
		} else {
			logger.info("Will not respond to this SUBMIT_SM");
			connection.writeResponse(new byte[0]);
		}
	}

	/**
	 * Custom variation of submit_multi handler. This implementation will treat
	 * any non-numeric destinatation address as invalid
	 */
	void getSubmitMultiResponse(byte[] message, int len) throws Exception {
		LoggingUtilities.hexDump("Custom SUBMIT_MULTI:", message, len);
		byte[] resp_message;
		SubmitMulti smppmsg = new SubmitMulti();
		smppmsg.demarshall(message);

		// now make the response object

		SubmitMultiResp smppresp = new SubmitMultiResp(smppmsg);

		// Validate session
		if ((!session.isBound()) || (!session.isTransmitter())) {
			logger
					.warning("Invalid bind state. Must be bound as transmitter for this PDU");
			wasInvalidBindState = true;
			smsc.incSubmitMultiERR();
			connection.writeResponse(smppresp.errorResponse(smppresp
					.getCmd_id(), PduConstants.ESME_RINVBNDSTS, smppresp
					.getSeq_no()));
		}

		// Validate each destination address
		int dests = smppmsg.getNumber_of_dests();
		DestAddress[] da = smppmsg.getDest_addresses();
		DestAddressSME sme = new DestAddressSME();
		ArrayList<UnsuccessSME> usmes = new ArrayList<UnsuccessSME>();
		String dest;
		boolean containedFailures = false;
		for (int i = 0; i < dests; i++) {
			if (da[i] instanceof DestAddressSME) {
				sme = (DestAddressSME) da[i];
				dest = sme.getSme_address();
				try {
					long n = Long.parseLong(dest);
				} catch (NumberFormatException nfe) {
					// MSISDN treated as invalid
					logger
							.warning("'Invalid' MSISDN "
									+ sme.getSme_ton()
									+ ","
									+ sme.getSme_npi()
									+ ","
									+ dest
									+ ". This protocol handler treats non-numeric MSISDN as invalid for testing purposes");
					UnsuccessSME usme = new UnsuccessSME(sme.getSme_ton(), sme
							.getSme_npi(), sme.getSme_address(),
							PduConstants.ESME_RINVDSTADR);
					containedFailures = true;
					usmes.add(usme);
				}
			}
		}
		int u = usmes.size();
		UnsuccessSME[] usmea = new UnsuccessSME[u];
		for (int i = 0; i < u; i++) {
			usmea[i] = usmes.get(i);
		}
		smppresp.setUnsuccess_smes(usmea);

		// ....and turn it back into a byte array
		resp_message = smppresp.marshall();

		LoggingUtilities.hexDump("SUBMIT_MULTI_RESP:", resp_message,
				resp_message.length);
		if (!containedFailures)
			smsc.incSubmitMultiOK();
		else
			smsc.incSubmitMultiERR();

		connection.writeResponse(resp_message);
	}

}