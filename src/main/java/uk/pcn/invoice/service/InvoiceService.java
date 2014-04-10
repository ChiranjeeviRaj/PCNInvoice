package uk.pcn.invoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import uk.pcn.invoice.dao.IInvoiceDAO;
import uk.pcn.invoice.model.Invoice;
import uk.pcn.invoice.model.palets.Palet;

@Component
public class InvoiceService implements IInvoiceService{
	
	@Autowired
	private IInvoiceDAO invoiceDAO;
	
	@Autowired
	GenerateInvoiceService generateInvoiceService;

	@Override
	public void addInvoice(Invoice invoice) {
		invoiceDAO.addInvoice(invoice);
	}

	@Override
	public Invoice getInvoice(int invoiceId) {
		return null;
	}

	@Override
	public void generateInvoice(Invoice invoice) {
		generateInvoiceService.createPdf(invoice);
	}
	
	@Override
	public String generatePaletsInvoice(Palet palets) {
		return generateInvoiceService.createPaletsPdf(palets);
	}

	@Override
	public void addPalets(Palet palets) {
		invoiceDAO.addPalets(palets);
	}

}
