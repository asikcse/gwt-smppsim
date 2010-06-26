/****************************************************************************
 * GwtSmppSim.java
 * 
 * 
 *****************************************************************************/
package com.gtl.fonecta.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gtl.fonecta.client.handler.ChangeBtnHandler;
import com.gtl.fonecta.client.handler.MessageHandler;

/**
 * class <code>GwtSmppSim</code> is gwt GUI.
 * 
 * @author devang
 */

@SuppressWarnings("deprecation")
public class GwtSmppSim implements EntryPoint {

	private DataServiceAsync serviceProxy;
	Map<String, String> initMap = new TreeMap<String, String>();

	Hidden hiddenHost;	

	Hidden hiddenHttpPort;
	VerticalPanel mainVPanel;
	VerticalPanel messageVPanel;	
	Label handsetNumLabel;
	Label serviceNumLabel;
	Label messageLabel;
	TextBox hansetNum;
	TextBox serviceNum;
	TextArea textMessage;
	Button changeButton;
	Button submitButton;
	Grid topGrid;
	Grid msgTitleGrid;

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
		final SortableTable sortableTable = new SortableTable();
		messageVPanel.clear();
		if (initMap.size() > 0) {		
			for(int i=0;i<initMap.size();i++){
				sortableTable.setValue(i, 0, "");
				sortableTable.setValue(i, 1, "");
			}
						
			for (String key : initMap.keySet()) {
				if (key.contentEquals("handsetNo")) {
					hansetNo = initMap.get(key);
					hansetNum.setText(hansetNo);
				} else if (key.contentEquals("serviceNo")) {
					serviceNo = initMap.get(key);
					serviceNum.setText(serviceNo);
				} else if (key.contains("MO")) {
					int rowIndex = Integer.parseInt( key.replace("MO", ""));
					sortableTable.setValue(rowIndex, 0, initMap.get(key));					
					sortableTable.setValue(rowIndex, 1, "");							
				} else if (key.contains("MT")) {
					int rowIndex = Integer.parseInt( key.replace("MT", ""));
					sortableTable.setValue(rowIndex, 0, "");
					sortableTable.setValue(rowIndex, 1, initMap.get(key));	
				} else if(key.contains("port")) {
					hiddenHttpPort.setValue(initMap.get(key)); 
				} else if(key.contains("host")) {					
					hiddenHost.setValue(initMap.get(key));					
				}				
			}			
			messageVPanel.add(sortableTable);			
			messageVPanel.setHorizontalAlignment(HasAlignment.ALIGN_CENTER);
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
		hansetNum.setWidth("200px");
		
		serviceNum = new TextBox();
		serviceNo = "337788665522";
		serviceNum.setText(serviceNo);
		serviceNum.setEnabled(false);
		serviceNum.setWidth("200px");

		textMessage = new TextArea();
		textMessage.setWidth("200px");
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

		msgTitleGrid = new Grid(1, 2);
		msgTitleGrid.setCellSpacing(10);

		msgTitleGrid.setWidget(0,0,new HTML("<font face='sans-serif'>Mobile Originated <i>messages</i>  </font>"));
		msgTitleGrid.getWidget(0, 0).setWidth("350px");
		msgTitleGrid.setWidget(0,1,new HTML("<font face='sans-serif'>Mobile Terminated <i>messages</i> </font>"));
		msgTitleGrid.getWidget(0, 1).setWidth("350px");
		msgTitleGrid.getWidget(0, 1).setStyleName("rightAlign");		

		messageVPanel = new VerticalPanel();		

		mainVPanel.add(topGrid);		
		mainVPanel.add(msgTitleGrid);
		mainVPanel.add(messageVPanel);
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

/*
 * SortableTable is a type of FlexTable which allows Sorting on its column.
 * Sorting is done totally on the client side. No server side call is made in
 * this table.
 * 
 * Current implementation of Sortable Table needs <code>Comparable</code> Object
 * in its column values to be able to sort them correctly
 * 
 * The objects being set in the column values must implement the interface
 * <code>Comparable</code> and implement methods: compareTo() and toString()
 * 
 * {@link com.google.gwt.user.client.ui.FlexTable} {@link java.lang.Comparable}
 */
@SuppressWarnings("deprecation")
class SortableTable extends FlexTable implements Sortable, TableListener {

	// Holds the current column being sorted
	private int sortColIndex = -1;

	// Holds the current direction of sort: Asc/ Desc
	private int sortDirection = -1;

	// The default image to show acending order arrow
	private String sortAscImage = "images/asc.gif";

	// The default image to show descending order arrow
	private String sortDescImage = "images/desc.gif";

	// The default image to show the blank image
	// This is needed to paint the columns other than
	// the one which is being sorted.
	// Should be same length and width as the asc/ desc
	// images.
	private String blankImage = "images/blank.gif";

	// Holds the data rows of the table
	// This is a list of RowData Object
	@SuppressWarnings("unchecked")
	private List tableRows = new ArrayList();

	// Holds the data for the column headers
	@SuppressWarnings("unchecked")
	private List tableHeader = new ArrayList();

	/*
	 * Default Constructor
	 * 
	 * Calls the super class constructor and adds a TableListener object
	 */
	public SortableTable() {
		super();
		this.addTableListener(this);
	}

	/*
	 * addColumnHeader
	 * 
	 * Adds the Column Header to the table Uses the rowIndex 0 to add the header
	 * names. Renders the name and the asc/desc/blank gif to the column
	 * 
	 * @param columnName (String)
	 * 
	 * @param columnIndex (int)
	 */
	@SuppressWarnings("unchecked")
	public void addColumnHeader(String name, int index) {
		tableHeader.add(index, name);
		this.renderTableHeader(name, index);
	}

	/*
	 * setValue
	 * 
	 * Sets the values in specifed row/column Expects a Comparable Object for
	 * sorting
	 * 
	 * @param rowIndex (int)
	 * 
	 * @param columnIndex (int)
	 * 
	 * @param Value (Comparable)
	 */
	@SuppressWarnings("unchecked")
	public void setValue(int rowIndex, int colIndex, Comparable value) {
		// The rowIndex should begin with 1 as rowIndex 0 is for the Header
		// Any row with index == 0 will not be displayed.
		if (rowIndex == 0) {
			return;
		}

		if ((rowIndex - 1) >= this.tableRows.size()
				|| null == tableRows.get(rowIndex - 1)) {
			tableRows.add(rowIndex - 1, new RowData());
		}

		RowData rowData = (RowData) this.tableRows.get(rowIndex - 1);
		rowData.addColumnValue(colIndex, value);
		this.setHTML(rowIndex, colIndex, "" + value.toString() + "");
	}

	/*
	 * sort
	 * 
	 * Implementation of Sortable Interface, this method decribes how to sort
	 * the specified column. It checks the current sort direction and flips it
	 * 
	 * @param columnIndex (int)
	 */
	@SuppressWarnings("unchecked")
	public void sort(int columnIndex) {
		Collections.sort(this.tableRows);
		if (this.sortColIndex != columnIndex) {
			// New Column Header clicked
			// Reset the sortDirection to ASC
			this.sortDirection = SORT_ASC;
		} else {
			// Same Column Header clicked
			// Reverse the sortDirection
			this.sortDirection = (this.sortDirection == SORT_ASC) ? SORT_DESC
					: SORT_ASC;
		}
		this.sortColIndex = columnIndex;
	}

	/*
	 * onCellClicked
	 * 
	 * Implementation of Table Listener Interface, this method decribes what to
	 * do when a cell is clicked It checks for the header row and calls the sort
	 * method to sort the table
	 * 
	 * @param sender (SourcesTableEvents)
	 * 
	 * @param rowIndex (int)
	 * 
	 * @param colIndex (int)
	 */
	public void onCellClicked1(SourcesTableEvents sender, int row, int col) {
		if (row != 0) {
			return;
		}
		this.setSortColIndex(col);
		this.sort(col);
		this.drawTable();
	}

	/*
	 * getSortAscImage
	 * 
	 * Getter for Sort Ascending Image
	 * 
	 * @return String
	 */
	public String getSortAscImage() {
		return sortAscImage;
	}

	/*
	 * setSortAscImage
	 * 
	 * Setter for Sort Ascending Image
	 * 
	 * @param relative path + image name (String) e.g. images/asc.gif
	 */
	public void setSortAscImage(String sortAscImage) {
		this.sortAscImage = sortAscImage;
	}

	/*
	 * getSortDescImage
	 * 
	 * Getter for Sort Descending Image
	 * 
	 * @return String
	 */
	public String getSortDescImage() {
		return sortDescImage;
	}

	/*
	 * setSortDescImgage
	 * 
	 * Setter for Sort Descending Image
	 * 
	 * @param relative path + image name (String) e.g. images/desc.gif
	 */
	public void setSortDescImgage(String sortDescImgage) {
		this.sortDescImage = sortDescImgage;
	}

	/*
	 * getBlankImage
	 * 
	 * Getter for blank Image
	 * 
	 * @return String
	 */
	public String getBlankImage() {
		return blankImage;
	}

	/*
	 * setBlankImage
	 * 
	 * Setter for the blank Image
	 * 
	 * @param relative path + image name (String) e.g. images/blank.gif
	 */
	public void setBlankImage(String blankImage) {
		this.blankImage = blankImage;
	}

	/*
	 * drawTable
	 * 
	 * Renders the header as well as the body of the table
	 */
	protected void drawTable() {
		this.displayTableHeader();
		this.displayTableBody();
	}

	/*
	 * displayTableHeader
	 * 
	 * Renders only the table header
	 */
	@SuppressWarnings("unchecked")
	private void displayTableHeader() {
		int colIndex = 0;
		for (Iterator colHeaderIter = this.tableHeader.iterator(); colHeaderIter
				.hasNext();) {
			String colHeader = (String) colHeaderIter.next();
			this.renderTableHeader(colHeader, colIndex++);
		}
	}

	/*
	 * displayTableBody
	 * 
	 * Renders the body or the remaining rows of the table except the header. It
	 * checks the sort direction and displays the rows accordingly
	 */
	private void displayTableBody() {
		if (this.sortDirection == SORT_ASC || this.sortDirection == -1) {
			// Ascending order and Default Display
			for (int rowIndex = 0; rowIndex < tableRows.size(); rowIndex++) {
				RowData columns = (RowData) tableRows.get(rowIndex);
				for (int colIndex = 0; colIndex < columns.getColumnValues()
						.size(); colIndex++) {
					Object value = columns.getColumnValue(colIndex);
					if (null != value) {
						this.setHTML(rowIndex + 1, colIndex, value.toString());
					}
				}
			}
		} else {
			// Descending Order Display
			for (int rowIndex = tableRows.size() - 1, rowNum = 1; rowIndex >= 0; rowIndex--, rowNum++) {
				RowData columns = (RowData) tableRows.get(rowIndex);
				for (int colIndex = 0; colIndex < columns.getColumnValues()
						.size(); colIndex++) {
					Object value = columns.getColumnValue(colIndex);
					if (null != value) {
						this.setHTML(rowNum, colIndex, value.toString());
					}
				}
			}
		}
	}

	/*
	 * setSortColIndex
	 * 
	 * Sets the current column index being sorted
	 * 
	 * @param column index being sorted (int)
	 */
	private void setSortColIndex(int sortIndex) {
		for (int rowIndex = 0; rowIndex < tableRows.size(); rowIndex++) {
			RowData row = (RowData) tableRows.get(rowIndex);
			row.setSortColIndex(sortIndex);
		}
	}

	/*
	 * renderTableHeader Renders a particular column in the Table Header
	 * 
	 * @param Column Name (String)
	 * 
	 * @param Column Index (int)
	 */
	private void renderTableHeader(String name, int index) {
		StringBuffer headerText = new StringBuffer();
		headerText.append(name);
		headerText.append("&nbsp;<img border='0' src=");
		if (this.sortColIndex == index) {
			if (this.sortDirection == SORT_ASC) {
				headerText.append("'" + this.sortAscImage
						+ "' alt='Ascending' ");
			} else {
				headerText.append("'" + this.sortDescImage
						+ "' alt='Descending' ");
			}
		} else {
			headerText.append("'" + this.blankImage + "'");
		}
		headerText.append("/>");

		this.setHTML(0, index, headerText.toString());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCellClicked(SourcesTableEvents sender, int row, int cell) {		

	}
}

/*
 * SortableTable Widget for GWT library of Google, Inc.
 * 
 * Copyright (c) 2006 Parvinder Thapar http://psthapar.googlepages.com/
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNULesser
 * General Public License for more details. You should have received a copy of
 * the GNU Lesser General PublicLicense along with this library; if not, write
 * to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA 02110-1301 USA
 */

/*
 * Sortable Interface defines the signatures and the constants for the sortable
 * table
 */
interface Sortable {
	// Constants defining the current direction of the
	// sort on a column
	public static int SORT_ASC = 0;
	public static int SORT_DESC = 1;

	/*
	 * sort
	 * 
	 * Defines what happens when the column is sorted
	 * 
	 * @param columnIndex to be sorted (int)
	 */
	public void sort(int columnIndex);
}

/*
 * SortableTable Widget for GWT library of Google, Inc.
 * 
 * Copyright (c) 2006 Parvinder Thapar http://psthapar.googlepages.com/
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNULesser
 * General Public License for more details. You should have received a copy of
 * the GNU Lesser General PublicLicense along with this library; if not, write
 * to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA 02110-1301 USA
 */

/*
 * SortableTable Widget for GWT library of Google, Inc.
 * 
 * Copyright (c) 2006 Parvinder Thapar http://psthapar.googlepages.com/
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNULesser
 * General Public License for more details. You should have received a copy of
 * the GNU Lesser General PublicLicense along with this library; if not, write
 * to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA 02110-1301 USA
 */

/*
 * RowData defines one row in a Sortable Table
 */
@SuppressWarnings("unchecked")
class RowData implements Comparable {

	// Maintains the list of the columns in the table
	List columnValues = new ArrayList();

	// Keeps the current column index being sorted
	int sortColIndex = 0;

	/*
	 * addColumnValue
	 * 
	 * Adds the Comparable Value in the List of columns
	 * 
	 * @param Comparable
	 */
	public void addColumnValue(Comparable value) {
		this.columnValues.add(value);
	}

	/*
	 * addColumnValue
	 * 
	 * Adds the Comparable Value in the specific index in the List of columns
	 * 
	 * @param colIndex (int)
	 * 
	 * @param Comparable
	 */
	public void addColumnValue(int index, Comparable value) {
		if (index >= this.columnValues.size()) {
			addNullColumns(index);
		}
		this.columnValues.set(index, value);
	}

	/*
	 * getColumnValue
	 * 
	 * Retrieves the Comparable Object from the List of columns
	 * 
	 * @param colIndex (int)
	 * 
	 * @return Object
	 */
	public Object getColumnValue(int index) {
		return this.columnValues.get(index);
	}

	/*
	 * addColumnValues
	 * 
	 * Retrieves the list of column values
	 * 
	 * @return List
	 */
	public List getColumnValues() {
		return columnValues;
	}

	/*
	 * setColumnValues
	 * 
	 * Sets the List to the List of column values
	 * 
	 * @param List
	 */
	public void setColumnValues(List columnValues) {
		this.columnValues = columnValues;
	}

	/*
	 * getSortColIndex
	 * 
	 * Returns the current column index being sorted
	 * 
	 * @return colIndex (int)
	 */
	public int getSortColIndex() {
		return sortColIndex;
	}

	/*
	 * setSortColIndex
	 * 
	 * Sets the current column index being sorted
	 * 
	 * @param colIndex (int)
	 */
	public void setSortColIndex(int sortColIndex) {
		this.sortColIndex = sortColIndex;
	}

	/*
	 * compareTo
	 * 
	 * Implementation of Interface Comparable Returns the compare result to
	 * another RowData object
	 * 
	 * @param colIndex (int)
	 */
	public int compareTo(Object other) {
		if (null == other) {
			return -1;
		}
		RowData otherRow = (RowData) other;
		Comparable obj1 = (Comparable) this.getColumnValue(this.sortColIndex);
		Comparable obj2 = (Comparable) otherRow
				.getColumnValue(this.sortColIndex);
		return obj1.compareTo(obj2);
	}

	/*
	 * addNullColumns
	 * 
	 * Adds the Null columns in the table row
	 * 
	 * @param colIndex (int)
	 * 
	 * @deprecated
	 */
	private void addNullColumns(int index) {
		for (int nullIndex = this.columnValues.size(); nullIndex <= index; nullIndex++) {
			columnValues.add(null);
		}
	}
}