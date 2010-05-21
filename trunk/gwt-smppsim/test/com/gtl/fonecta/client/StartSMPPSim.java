package com.gtl.fonecta.client;

import com.seleniumsoftware.SMPPSim.SMPPSim;

public class StartSMPPSim {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean isRunning= false;
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
}
