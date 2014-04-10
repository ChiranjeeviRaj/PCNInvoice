package uk.pcn.invoice.model.palets;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.IndexColumn;


/**
 * The persistent class for the palets database table.
 * 
 */
//@SequenceGenerator(name = "id", sequenceName = "some_set_seq", initialValue = 1, allocationSize = 1)
@Entity
@Table(name="palet")
public class Palet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="palet_id")
	private int id;
	
	@Temporal(TemporalType.DATE)
	private Date invoiceDate;

	//bi-directional many-to-one association to Pitem
    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="palet_id")
    @IndexColumn(name="idx")
	private List<Pitem> pitems;

	public Palet() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Pitem> getPitems() {
		return this.pitems;
	}

	public void setPitems(List<Pitem> pitems) {
		this.pitems = pitems;
	}

	public Pitem addPitem(Pitem pitem) {
		getPitems().add(pitem);
		pitem.setPalet(this);

		return pitem;
	}

	public Pitem removePitem(Pitem pitem) {
		getPitems().remove(pitem);
		pitem.setPalet(null);

		return pitem;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}


}