/****************************************************************************
 * Session.java
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
 * $Header: /var/cvsroot/SMPPSim2/src/java/com/seleniumsoftware/SMPPSim/Session.java,v 1.3 2007/11/23 08:52:28 martin Exp $
 ****************************************************************************/

package com.seleniumsoftware.SMPPSim;

public class Session {
	boolean isBound = false;
	boolean isReceiver = false;
	boolean isTransmitter = false;

	public Session() {
	}

	/**
	 * @return
	 */
	public boolean isBound() {
		return isBound;
	}

	/**
	 * @return
	 */
	public boolean isReceiver() {
		return isReceiver;
	}

	/**
	 * @return
	 */
	public boolean isTransmitter() {
		return isTransmitter;
	}

	/**
	 * @param b
	 */
	public void setBound(boolean b) {
		isBound = b;
	}

	/**
	 * @param b
	 */
	public void setReceiver(boolean b) {
		isReceiver = b;
	}

	/**
	 * @param b
	 */
	public void setTransmitter(boolean b) {
		isTransmitter = b;
	}

}