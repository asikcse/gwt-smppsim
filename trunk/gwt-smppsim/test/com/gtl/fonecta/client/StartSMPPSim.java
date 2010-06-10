package com.gtl.fonecta.client;

import com.seleniumsoftware.SMPPSim.SMPPSim;

public class StartSMPPSim {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean isRunning= false;
		String configFile = "/home/devang/workspace_FDS/GWT-SMPPSim/conf/smppsim.props";
		String[] arguemnts = new String[1];		
		arguemnts[0] = configFile;
		try {		
			if(!isRunning){
				SMPPSim.main(arguemnts);
				isRunning=true;	
			}			 			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
