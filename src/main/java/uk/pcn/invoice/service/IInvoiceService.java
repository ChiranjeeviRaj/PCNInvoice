package uk.pcn.invoice.service;

import uk.pcn.invoice.model.Invoice;
import uk.pcn.invoice.model.palets.Palet;


/**
 * 
 * @author Raju
 *
 */
public interface IInvoiceService {
	

	public void addInvoice(Invoice invoice);
	
	public Invoice getInvoice(int invoiceId);
	
	public void generateInvoice(Invoice invoice);
	
	public void addPalets(Palet palets);

	public String generatePaletsInvoice(Palet palets);
	
}
