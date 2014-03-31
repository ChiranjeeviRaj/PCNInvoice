package uk.pcn.invoice.main.model;

import java.math.BigDecimal;

public class Address {
	
	public AddressType getAddressType() {
		return AddressType;
	}

	public void setAddressType(AddressType addressType) {
		AddressType = addressType;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getCotactTelephone() {
		return cotactTelephone;
	}

	public void setCotactTelephone(String cotactTelephone) {
		this.cotactTelephone = cotactTelephone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	private AddressType AddressType;
	
	private String company;

	private String addressLine1;

	private String addressLine2;

	private String area;

	private String contactName;

	private String cotactTelephone;

	private String country;

	private String countryCode;

	private String email;

	private String postCode;

	private String town;
}
