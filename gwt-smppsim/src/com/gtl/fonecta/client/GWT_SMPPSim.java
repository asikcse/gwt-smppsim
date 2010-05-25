/****************************************************************************
 * GWT_SMPPSim.java
 * 
 * 
 *****************************************************************************/
package com.gtl.fonecta.client;

import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gtl.fonecta.client.handler.ChangeBtnHandler;
import com.gtl.fonecta.client.handler.MessageHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWT_SMPPSim implements EntryPoint {

	private DataServiceAsync serviceProxy;
	Map<String, String> initMap = null;

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

	GWT_SMPPSim() {
		// System.out.println("Constructor---");
		serviceProxy = GWT.create(DataService.class);

		/*try{
			String hno=getHansetNo();
			String sno=serviceNo;
			System.out.println("Constructor---" + hno + " " + sno+"\t"+hansetNo+"\t"+serviceNo+"\t"+this.hansetNo+"\t"+this.serviceNo);
		}catch(Exception e){
			e.printStackTrace();
		}*/
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

	protected void setComponetValue() {

		//java.util.NavigableMap<String, String> descMap;
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
			}
		}
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

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
		msgGrid.setCellSpacing(5);

		msgGrid
				.setWidget(
						0,
						0,
						new HTML(
								"<font face='sans-serif'>Mobile Originated <i>messages</i>  </font>"));
		msgGrid.getWidget(0, 0).setWidth("300px");
		msgGrid
				.setWidget(
						0,
						1,
						new HTML(
								"<font face='sans-serif'>Mobile Terminated <i>messages</i> </font>"));
		msgGrid.getWidget(0, 1).setWidth("300px");
		msgGrid.getWidget(0, 1).setStyleName("rightAlign");

		leftVPanel = new VerticalPanel();
		rightVPanel = new VerticalPanel();

		msgGrid.setWidget(1, 0, leftVPanel);

		msgGrid.setWidget(1, 1, rightVPanel);
		msgGrid.getWidget(1, 1).setWidth("300px");

		msgGrid.getWidget(1, 1).setStyleName("rightAlign");

		mainVPanel.add(topGrid);
		mainVPanel.add(msgGrid);
		mainVPanel.setSpacing(5);
		// mainVPanel.setBorderWidth(1);

		mainVPanel.setStyleName("table-center");

		RootPanel.get().add(mainVPanel);

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

}
