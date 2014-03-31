package uk.pcn.invoice.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * The persistent class for the invoice database table.
 * 
 */
@Entity 
public class Invoice implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@GenericGenerator(name="table-hilo-generator", strategy="org.hibernate.id.TableHiLoGenerator",
			  parameters={@Parameter(value="hibernate_id_generation", name="table")})

			 //I use the generator configured above
	@GeneratedValue(generator="table-hilo-generator")
	@Id 
	private int invoiceId;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="invoice")
	private List<Item> items;

	public Invoice() {
	}

	public int getInvoiceId() {
		return this.invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Item addItem(Item item) {
		getItems().add(item);
		item.setInvoice(this);

		return item;
	}

	public Item removeItem(Item item) {
		getItems().remove(item);
		item.setInvoice(null);

		return item;
	}

}