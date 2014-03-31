package uk.pcn.invoice.dao;

import uk.pcn.invoice.model.Invoice;
import uk.pcn.invoice.model.palets.Palet;


public interface IInvoiceDAO {

	/**
	 * Add Invoice
	 * 
	 * @param  Invoice invoice
	 */
	public void addInvoice(Invoice invoice);
	
	/**
	 * Get Invoice
	 * 
	 * @param  String Invoice Id
	 */
	public Invoice getInvoiceById(String invoiceid);

	public void addPalets(Palet palets);

}
