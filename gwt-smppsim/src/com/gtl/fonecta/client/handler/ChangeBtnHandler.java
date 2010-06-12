package com.gtl.fonecta.client.handler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.gtl.fonecta.client.GwtSmppSim;

/**
 * @author devang
 * 
 */
public class ChangeBtnHandler implements ClickHandler {

	GwtSmppSim gwtSMPPSim;

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
	public ChangeBtnHandler(GwtSmppSim gwtSMPPSim) {
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
	public GwtSmppSim getGwtSMPPSim() {
		return gwtSMPPSim;
	}

	/**
	 * @param gwtSMPPSim
	 *            the gwtSMPPSim to set
	 */
	public void setGwtSMPPSim(GwtSmppSim gwtSMPPSim) {
		this.gwtSMPPSim = gwtSMPPSim;
	}
}
