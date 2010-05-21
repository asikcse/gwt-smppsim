package com.gtl.fonecta.client;

import java.sql.Timestamp;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("dataService")
public interface DataService extends RemoteService {
	Map<String, String> getInitialData();

	Map<String, String> getInitialData(String handsetNo, String serviceNo,
			String shortMessage, Timestamp sendTime);
}
