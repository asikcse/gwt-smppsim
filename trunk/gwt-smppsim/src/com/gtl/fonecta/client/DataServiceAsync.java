package com.gtl.fonecta.client;

import java.sql.Timestamp;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DataServiceAsync {
	void getInitialData(AsyncCallback<Map<String, String>> asyncCallback);

	void getInitialData(String handsetNo, String serviceNo, String shortMessage,
			Timestamp sendTime, AsyncCallback<Map<String, String>> callback);
}
