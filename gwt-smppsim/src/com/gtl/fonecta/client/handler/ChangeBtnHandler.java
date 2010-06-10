package com.gtl.fonecta.client.handler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.gtl.fonecta.client.GWT_SMPPSim;

/**
 * @author devang
 * 
 */
public class ChangeBtnHandler implements ClickHandler {

	GWT_SMPPSim gwtSMPPSim;

	/**
	 * Default constructor
	 */
	public ChangeBtnHandler() {
	}

	/**
	 * Parameterised constructor
	 * 
	 * @param gwtSMPPSim
	 */
	public ChangeBtnHandler(GWT_SMPPSim gwtSMPPSim) {
		super();
		this.gwtSMPPSim = gwtSMPPSim;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event
	 * .dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		gwtSMPPSim.getInitMap().put("handsetNo",
				gwtSMPPSim.getHansetNum().getText());
	}

	/**
	 * @return the gwtSMPPSim
	 */
	public GWT_SMPPSim getGwtSMPPSim() {
		return gwtSMPPSim;
	}

	/**
	 * @param gwtSMPPSim
	 *            the gwtSMPPSim to set
	 */
	public void setGwtSMPPSim(GWT_SMPPSim gwtSMPPSim) {
		this.gwtSMPPSim = gwtSMPPSim;
	}
}
