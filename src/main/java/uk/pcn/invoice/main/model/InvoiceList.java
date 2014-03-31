package uk.pcn.invoice.main.model;

import java.util.ArrayList;
import java.util.List;

public class InvoiceList {
	
	private List<Invoice> invoiceList;

	public List<Invoice> getInvoiceList() {
		if(invoiceList == null){
			invoiceList = new ArrayList<Invoice>();
		}
		return invoiceList;
	}

	public void setInvoiceList(List<Invoice> invoiceList) {
		this.invoiceList = invoiceList;
	}
	
	public void add(Invoice invoice){
		getInvoiceList().add(invoice);
	}
	
}
