package uk.pcn.invoice.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the addersstype database table.
 * 
 */
@Entity
public class Addersstype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String type;

	//bi-directional many-to-one association to Address
	@OneToMany(mappedBy="addersstype")
	private List<Address> addresses;

	public Addersstype() {
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Address addAddress(Address address) {
		getAddresses().add(address);
		address.setAddersstype(this);

		return address;
	}

	public Address removeAddress(Address address) {
		getAddresses().remove(address);
		address.setAddersstype(null);

		return address;
	}

}