/****************************************************************************
 * GWT_SMPPSim.java
 * 
 * 
 *****************************************************************************/
package com.gtl.fonecta.client;

import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gtl.fonecta.client.handler.ChangeBtnHandler;
import com.gtl.fonecta.client.handler.MessageHandler;

/**
 * class <code>GWT_SMPPSim</code> is gwt GUI.
 * 
 * @author devang
 */

public class GwtSmppSim implements EntryPoint {

	private DataServiceAsync serviceProxy;
	Map<String, String> initMap = new TreeMap<String, String>();

	Hidden hiddenHost;
	

	Hidden hiddenHttpPort;
	VerticalPanel mainVPanel;
	VerticalPanel leftVPanel;
	VerticalPanel rightVPanel;
	Label handsetNumLabel;
	Label serviceNumLabel;
	Label messageLabel;
	TextBox hansetNum;
	TextBox serviceNum;
	TextArea textMessage;
	Button changeButton;
	Button submitButton;
	Grid topGrid;
	Grid msgGrid;

	String hansetNo;
	String serviceNo;
	String shortMessage;

	GwtSmppSim() {
		
		serviceProxy = GWT.create(DataService.class);

		serviceProxy.getInitialData(new AsyncCallback<Map<String, String>>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("FAIL" + caught.getMessage());
				caught.getStackTrace();
			}

			@Override
			public void onSuccess(Map<String, String> result) {
				initMap = result;
				setComponetValue();
			}
		});
	}

	/**
	 * this method should set the component's values
	 */
	protected void setComponetValue() {

		leftVPanel.clear();
		rightVPanel.clear();
		if (initMap.size() > 0) {
			for (String key : initMap.keySet()) {
				if (key.contentEquals("handsetNo")) {
					hansetNo = initMap.get(key);
					hansetNum.setText(hansetNo);
				} else if (key.contentEquals("serviceNo")) {
					serviceNo = initMap.get(key);
					serviceNum.setText(serviceNo);
				} else if (key.contains("MO")) {
					leftVPanel.add(new HTML(initMap.get(key) + "<br>"));
				} else if (key.contains("MT")) {
					rightVPanel.add(new HTML(initMap.get(key) + "<br>"));
				} else if(key.contains("port")) {
					hiddenHttpPort.setValue(initMap.get(key)); 
				} else if(key.contains("host")) {					
					hiddenHost.setValue(initMap.get(key));					
				}				
			}
		}
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		hiddenHost= new Hidden();
		hiddenHttpPort = new Hidden(); 
		
		mainVPanel = new VerticalPanel();
		handsetNumLabel = new Label("Handset number :");
		serviceNumLabel = new Label("Service number :");
		messageLabel = new Label("Message :");

		hansetNum = new TextBox();
		setHansetNo("4477665544");
		hansetNo = "4477665544";
		hansetNum.setText(hansetNo);

		serviceNum = new TextBox();
		serviceNo = "337788665522";
		serviceNum.setText(serviceNo);
		serviceNum.setEnabled(false);

		textMessage = new TextArea();
		textMessage.setText("Hello from SMPPSim");

		changeButton = new Button("Change");
		submitButton = new Button("Send Message");

		changeButton.addClickHandler(new ChangeBtnHandler(this));
		submitButton.addClickHandler(new MessageHandler(this));

		topGrid = new Grid(3, 3);

		topGrid.setCellSpacing(5);
		topGrid.setWidget(0, 0, handsetNumLabel);
		topGrid.setWidget(1, 0, serviceNumLabel);
		topGrid.setWidget(2, 0, messageLabel);

		topGrid.setWidget(0, 1, hansetNum);
		topGrid.setWidget(1, 1, serviceNum);
		topGrid.setWidget(2, 1, textMessage);

		topGrid.setWidget(0, 2, changeButton);
		topGrid.setWidget(1, 2, submitButton);
		topGrid.setWidget(2, 2, new HTML());

		msgGrid = new Grid(2, 2);
		msgGrid.setCellSpacing(10);

		msgGrid.setWidget(0,0,new HTML("<font face='sans-serif'>Mobile Originated <i>messages</i>  </font>"));
		msgGrid.getWidget(0, 0).setWidth("350px");
		msgGrid.setWidget(0,1,new HTML("<font face='sans-serif'>Mobile Terminated <i>messages</i> </font>"));
		msgGrid.getWidget(0, 1).setWidth("350px");
		msgGrid.getWidget(0, 1).setStyleName("rightAlign");

		leftVPanel = new VerticalPanel();
		rightVPanel = new VerticalPanel();

		rightVPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		rightVPanel.setHeight("50px");
		leftVPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		leftVPanel.setHeight("50px");

		msgGrid.setWidget(1, 0, leftVPanel);

		msgGrid.setWidget(1, 1, rightVPanel);
		msgGrid.getWidget(1, 1).setWidth("300px");
		msgGrid.getWidget(1, 1).setStyleName("rightAlign");

		mainVPanel.add(topGrid);
		mainVPanel.add(msgGrid);
		mainVPanel.setSpacing(5);

		mainVPanel.setStyleName("table-center");

		RootPanel.get().add(mainVPanel);
		

		try {
			 // Setup timer to refresh MT and MO messages automatically.
			Timer refreshTimer = new Timer() {
			      @Override
			      public void run() {
			    	  serviceProxy.getInitialData(new AsyncCallback<Map<String, String>>() {

			  			@Override
			  			public void onFailure(Throwable caught) {
			  				System.out.println("FAIL" + caught.getMessage());
			  				caught.getStackTrace();
			  			}

			  			@Override
			  			public void onSuccess(Map<String, String> result) {
			  				initMap = result;
			  				setComponetValue();
			  			}
			  		});			    				    
			      }
			    };
			    refreshTimer.scheduleRepeating(10000);	
		} catch (Exception e) {		
			System.out.println("EXCEPTION");
		} 	    
	}

	/**
	 * @return the hansetNum
	 */
	public TextBox getHansetNum() {
		return hansetNum;
	}

	/**
	 * @param hansetNum
	 *            the hansetNum to set
	 */
	public void setHansetNum(TextBox hansetNum) {
		this.hansetNum = hansetNum;
	}

	/**
	 * @return the serviceNum
	 */
	public TextBox getServiceNum() {
		return serviceNum;
	}

	/**
	 * @param serviceNum
	 *            the serviceNum to set
	 */
	public void setServiceNum(TextBox serviceNum) {
		this.serviceNum = serviceNum;
	}

	/**
	 * @return the textMessage
	 */
	public TextArea getTextMessage() {
		return textMessage;
	}

	/**
	 * @param textMessage
	 *            the textMessage to set
	 */
	public void setTextMessage(TextArea textMessage) {
		this.textMessage = textMessage;
	}

	/**
	 * @return the hansetNo
	 */
	public String getHansetNo() {
		return hansetNo;
	}

	/**
	 * @param hansetNo
	 *            the hansetNo to set
	 */
	public void setHansetNo(String hansetNo) {
		this.hansetNo = hansetNo;
	}

	/**
	 * @return the serviceNo
	 */
	public String getServiceNo() {
		return serviceNo;
	}

	/**
	 * @param serviceNo
	 *            the serviceNo to set
	 */
	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}

	/**
	 * @return the shortMessage
	 */
	public String getShortMessage() {
		return shortMessage;
	}

	/**
	 * @param shortMessage
	 *            the shortMessage to set
	 */
	public void setShortMessage(String shortMessage) {
		this.shortMessage = shortMessage;
	}

	/**
	 * @return the initMap
	 */
	public Map<String, String> getInitMap() {
		return initMap;
	}

	/**
	 * @param initMap
	 *            the initMap to set
	 */
	public void setInitMap(Map<String, String> initMap) {
		this.initMap = initMap;
	}
	
	/**
	 * @return the hiddenHost
	 */
	public Hidden getHiddenHost() {
		return hiddenHost;
	}

	/**
	 * @param hiddenHost the hiddenHost to set
	 */
	public void setHiddenHost(Hidden hiddenHost) {
		this.hiddenHost = hiddenHost;
	}

	/**
	 * @return the hiddenHttpPort
	 */
	public Hidden getHiddenHttpPort() {
		return hiddenHttpPort;
	}

	/**
	 * @param hiddenHttpPort the hiddenHttpPort to set
	 */
	public void setHiddenHttpPort(Hidden hiddenHttpPort) {
		this.hiddenHttpPort = hiddenHttpPort;
	}
}
