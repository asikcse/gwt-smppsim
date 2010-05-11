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

	GWT_SMPPSim() {
		System.out.println("Constructor---");
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

			private void setComponetValue() {
				// TODO : set component values from the Map
			}
		});

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
		serviceNum = new TextBox();
		textMessage = new TextArea();

		changeButton = new Button("Change");
		submitButton = new Button("Send Message");

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

		msgGrid.setWidget(0, 0, new HTML("<font face='sans-serif'>Mobile Originated messages  </font>"));
		msgGrid.getWidget(0, 0).setWidth("300px");
		msgGrid.setWidget(0, 1, new HTML("<font face='sans-serif'>Mobile Terminated messages </font>"));
		msgGrid.getWidget(0, 1).setWidth("300px");
		msgGrid.getWidget(0, 1).setStyleName("rightAlign");

		leftVPanel = new VerticalPanel();
		rightVPanel = new VerticalPanel();
		
/*		leftVPanel
				.add(new HTML(
						"2010-05-06 12:12.12 [040123123->16123]  <br>This is a Mobile Originated message 1<p/>"));
		leftVPanel
				.add(new HTML(
						"2010-05-06 12:12.12 [040123123->16123]  <br>This is a Mobile Originated message 2<p/>"));
		leftVPanel
				.add(new HTML(
						"2010-05-06 12:12.12 [040123123->16123]  <br>This is a Mobile Originated message 3<p/>"));

		rightVPanel
				.add(new HTML(
						"2010-05-06 12:12.12 [040123123->16123]  <br>This is a Mobile Terminated message 4<p/>"));
		rightVPanel
				.add(new HTML(
						"2010-05-06 12:12.12 [040123123->16123]  <br>This is a Mobile Terminated message 5<p/>"));
		rightVPanel
				.add(new HTML(
						"2010-05-06 12:12.12 [040123123->16123]  <br>This is a Mobile Terminated message 6<p/>"));
		rightVPanel.getWidget(0).setStyleName("rightAlign");
		rightVPanel.getWidget(1).setStyleName("rightAlign");
		rightVPanel.getWidget(2).setStyleName("rightAlign");
*/		 
		msgGrid.setWidget(1, 0, leftVPanel);

		msgGrid.setWidget(1, 1, rightVPanel);
		msgGrid.getWidget(1, 1).setWidth("300px");

		msgGrid.getWidget(1, 1).setStyleName("rightAlign");

		mainVPanel.add(topGrid);
		mainVPanel.add(msgGrid);
		mainVPanel.setSpacing(5);
		//mainVPanel.setBorderWidth(1);

		mainVPanel.setStyleName("table-center");

		RootPanel.get().add(mainVPanel);
		

	}

}
