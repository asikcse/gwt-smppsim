package com.gtl.fonecta.server;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gtl.fonecta.client.DataService;
import com.seleniumsoftware.SMPPSim.SMPPSim;

/**
 * 
 * @author devang
 *
 */
public class DataServiceImpl extends RemoteServiceServlet implements
		DataService {

	private static final long serialVersionUID = 1L;
	boolean isRunning= false;

	/**
	 * method to start SMPPSim
	 */
	private void startSMPPSim() {
		String[] arguemnts = new String[1];		
		arguemnts[0] = "/home/devang/workspace_FDS/GWT-SMPPSim/conf/smppsim.props";
		try {		
			if(!isRunning){
				SMPPSim.main(arguemnts);
				isRunning=true;	
			}			 			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, String> getInitialData() {
		startSMPPSim();
		//TODO : get initial values into the map
		Map<String, String> map = new HashMap<String, String>();
		
		
		return map;
	}

}
