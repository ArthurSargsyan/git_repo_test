package beans;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Invoice {

	private int invoiceID=0;
	private String invoiceNo="";
	private String vender="";
	private String currancy="";
	private String date=null;
	private Set<ItemsInStore> items=null;
	
	
	public Invoice( String invoiceNo, String vender, String currancy, String date, HashSet<ItemsInStore> itemsSet) {
		this.invoiceNo = invoiceNo;
		this.vender = vender;
		this.currancy = currancy;
		this.date = date;
		this.items = itemsSet;
	}

	public Invoice() {
	}
	
	public Set<ItemsInStore> getItems() {
		return items;
	}
	public void setItems(Set<ItemsInStore> items) {
		this.items = items;
	}
	public int getInvoiceID() {
		return invoiceID;
	}
	public void setInvoiceID(int invoiceID) {
		this.invoiceID = invoiceID;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getVender() {
		return vender;
	}
	public void setVender(String vender) {
		this.vender = vender;
	}
	public String getCurrancy() {
		return currancy;
	}
	public void setCurrancy(String currancy) {
		this.currancy = currancy;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
