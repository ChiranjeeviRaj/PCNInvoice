package uk.pcn.invoice.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import uk.pcn.invoice.model.Invoice;
import uk.pcn.invoice.model.palets.Palet;
import uk.pcn.invoice.util.PCNUtil;

@Component
public class InvoiceDao implements IInvoiceDAO {
	@Autowired
	private SessionFactory sessionFactory;
	

	/**
	 * Get Hibernate Session Factory
	 * 
	 * @return SessionFactory - Hibernate Session Factory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * Set Hibernate Session Factory
	 * 
	 * @param SessionFactory - Hibernate Session Factory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	@Transactional
	public void addInvoice(Invoice invoice) {
		getSessionFactory().getCurrentSession().save(invoice);
		PCNUtil.invoiceNumStr =  "PAR"+invoice.getInvoiceId();
	}

	@Override
	@Transactional(readOnly = true)
	public Invoice getInvoiceById(String invoiceid) {
		@SuppressWarnings("unchecked")
		List<Invoice> list = getSessionFactory().getCurrentSession()
				.createQuery("from Invoice where id=?")
				.setParameter(0, invoiceid).list();
		return list.get(0);
	}

	@Override
	@Transactional
	public void addPalets(Palet palets) {
		getSessionFactory().getCurrentSession().save(palets);
		PCNUtil.invoiceNumStr = "PAL"+palets.getId();
	}

}
