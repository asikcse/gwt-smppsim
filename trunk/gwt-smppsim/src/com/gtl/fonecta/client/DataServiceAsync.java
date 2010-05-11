package com.gtl.fonecta.client;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DataServiceAsync {
	void getInitialData(AsyncCallback<Map<String, String>> asyncCallback);
}
