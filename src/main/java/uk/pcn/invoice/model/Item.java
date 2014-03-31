package uk.pcn.invoice.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the item database table.
 * 
 */
@Entity
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String PHTrackingNumber;

	private String accountNumber;

	private String additionalAccount1;

	private String additionalAccount2;

	private String additionalAccount3;

	private String additionalAccount4;

	private String additionalAccount5;

	private String aliasAccount;

	private double billableWeight;

	@Temporal(TemporalType.DATE)
	private Date collectionDate;

	private String contactName;

	private String courierAlternativeRefNumber;

	private String courierCollectionRefNumber;

	private String courierConsignmentTrackingNumber;

	private String courierParcelTrackingNumber;

	private String DAddressLine1;

	private String DAddressLine2;

	private String DArea;

	private String DCompany;

	private String DCountry;

	private String DCountryCode;

	private String deleted;

	private String DEmail;

	private String DPostCode;

	private String DTown;

	private String enhancement;

	private String exportReason;

	private double girth;

	private String goodsDescription;

	private double height;

	private String inLondonCongestion;

	private String leavesafeInstruction;

	private double length;

	private String month;

	private BigDecimal numberOfParcels;

	private String outOfArea;

	private String parcelDeclaredValue;

	private double parcelWeight;

	private String providerService;

	private String providerServiceCode;

	private String providerServiceId;

	private String reference1;

	private String reference2;

	private String service_ProviderServiceId;

	private String serviceProvider;

	private String serviceType;

	private String shipmentDeclaredCurrency;

	private String shipmentDeclaredValue;

	private double shipmentWeight;

	private String shippinAccountID;

	private String shippingAccountName;

	@Temporal(TemporalType.DATE)
	private Date shippingCreationDate;

	private String specialInstruction;

	private String surcharge;

	private String termsOfTrade;

	private double volWeight;

	private String weekBeginning;

	private double width;

	private String year;

	private String zoneID;

	//bi-directional many-to-one association to Address
	@OneToMany(mappedBy="item")
	private List<Address> addresses;

	//bi-directional many-to-one association to Invoice
	@ManyToOne
	private Invoice invoice;

	public Item() {
	}

	public String getPHTrackingNumber() {
		return this.PHTrackingNumber;
	}

	public void setPHTrackingNumber(String PHTrackingNumber) {
		this.PHTrackingNumber = PHTrackingNumber;
	}

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAdditionalAccount1() {
		return this.additionalAccount1;
	}

	public void setAdditionalAccount1(String additionalAccount1) {
		this.additionalAccount1 = additionalAccount1;
	}

	public String getAdditionalAccount2() {
		return this.additionalAccount2;
	}

	public void setAdditionalAccount2(String additionalAccount2) {
		this.additionalAccount2 = additionalAccount2;
	}

	public String getAdditionalAccount3() {
		return this.additionalAccount3;
	}

	public void setAdditionalAccount3(String additionalAccount3) {
		this.additionalAccount3 = additionalAccount3;
	}

	public String getAdditionalAccount4() {
		return this.additionalAccount4;
	}

	public void setAdditionalAccount4(String additionalAccount4) {
		this.additionalAccount4 = additionalAccount4;
	}

	public String getAdditionalAccount5() {
		return this.additionalAccount5;
	}

	public void setAdditionalAccount5(String additionalAccount5) {
		this.additionalAccount5 = additionalAccount5;
	}

	public String getAliasAccount() {
		return this.aliasAccount;
	}

	public void setAliasAccount(String aliasAccount) {
		this.aliasAccount = aliasAccount;
	}

	public double getBillableWeight() {
		return this.billableWeight;
	}

	public void setBillableWeight(double billableWeight) {
		this.billableWeight = billableWeight;
	}

	public Date getCollectionDate() {
		return this.collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getCourierAlternativeRefNumber() {
		return this.courierAlternativeRefNumber;
	}

	public void setCourierAlternativeRefNumber(String courierAlternativeRefNumber) {
		this.courierAlternativeRefNumber = courierAlternativeRefNumber;
	}

	public String getCourierCollectionRefNumber() {
		return this.courierCollectionRefNumber;
	}

	public void setCourierCollectionRefNumber(String courierCollectionRefNumber) {
		this.courierCollectionRefNumber = courierCollectionRefNumber;
	}

	public String getCourierConsignmentTrackingNumber() {
		return this.courierConsignmentTrackingNumber;
	}

	public void setCourierConsignmentTrackingNumber(String courierConsignmentTrackingNumber) {
		this.courierConsignmentTrackingNumber = courierConsignmentTrackingNumber;
	}

	public String getCourierParcelTrackingNumber() {
		return this.courierParcelTrackingNumber;
	}

	public void setCourierParcelTrackingNumber(String courierParcelTrackingNumber) {
		this.courierParcelTrackingNumber = courierParcelTrackingNumber;
	}

	public String getDAddressLine1() {
		return this.DAddressLine1;
	}

	public void setDAddressLine1(String DAddressLine1) {
		this.DAddressLine1 = DAddressLine1;
	}

	public String getDAddressLine2() {
		return this.DAddressLine2;
	}

	public void setDAddressLine2(String DAddressLine2) {
		this.DAddressLine2 = DAddressLine2;
	}

	public String getDArea() {
		return this.DArea;
	}

	public void setDArea(String DArea) {
		this.DArea = DArea;
	}

	public String getDCompany() {
		return this.DCompany;
	}

	public void setDCompany(String DCompany) {
		this.DCompany = DCompany;
	}

	public String getDCountry() {
		return this.DCountry;
	}

	public void setDCountry(String DCountry) {
		this.DCountry = DCountry;
	}

	public String getDCountryCode() {
		return this.DCountryCode;
	}

	public void setDCountryCode(String DCountryCode) {
		this.DCountryCode = DCountryCode;
	}

	public String getDeleted() {
		return this.deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getDEmail() {
		return this.DEmail;
	}

	public void setDEmail(String DEmail) {
		this.DEmail = DEmail;
	}

	public String getDPostCode() {
		return this.DPostCode;
	}

	public void setDPostCode(String DPostCode) {
		this.DPostCode = DPostCode;
	}

	public String getDTown() {
		return this.DTown;
	}

	public void setDTown(String DTown) {
		this.DTown = DTown;
	}

	public String getEnhancement() {
		return this.enhancement;
	}

	public void setEnhancement(String enhancement) {
		this.enhancement = enhancement;
	}

	public String getExportReason() {
		return this.exportReason;
	}

	public void setExportReason(String exportReason) {
		this.exportReason = exportReason;
	}

	public double getGirth() {
		return this.girth;
	}

	public void setGirth(double girth) {
		this.girth = girth;
	}

	public String getGoodsDescription() {
		return this.goodsDescription;
	}

	public void setGoodsDescription(String goodsDescription) {
		this.goodsDescription = goodsDescription;
	}

	public double getHeight() {
		return this.height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getInLondonCongestion() {
		return this.inLondonCongestion;
	}

	public void setInLondonCongestion(String inLondonCongestion) {
		this.inLondonCongestion = inLondonCongestion;
	}

	public String getLeavesafeInstruction() {
		return this.leavesafeInstruction;
	}

	public void setLeavesafeInstruction(String leavesafeInstruction) {
		this.leavesafeInstruction = leavesafeInstruction;
	}

	public double getLength() {
		return this.length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public BigDecimal getNumberOfParcels() {
		return this.numberOfParcels;
	}

	public void setNumberOfParcels(BigDecimal numberOfParcels) {
		this.numberOfParcels = numberOfParcels;
	}

	public String getOutOfArea() {
		return this.outOfArea;
	}

	public void setOutOfArea(String outOfArea) {
		this.outOfArea = outOfArea;
	}

	public String getParcelDeclaredValue() {
		return this.parcelDeclaredValue;
	}

	public void setParcelDeclaredValue(String parcelDeclaredValue) {
		this.parcelDeclaredValue = parcelDeclaredValue;
	}

	public double getParcelWeight() {
		return this.parcelWeight;
	}

	public void setParcelWeight(double parcelWeight) {
		this.parcelWeight = parcelWeight;
	}

	public String getProviderService() {
		return this.providerService;
	}

	public void setProviderService(String providerService) {
		this.providerService = providerService;
	}

	public String getProviderServiceCode() {
		return this.providerServiceCode;
	}

	public void setProviderServiceCode(String providerServiceCode) {
		this.providerServiceCode = providerServiceCode;
	}

	public String getProviderServiceId() {
		return this.providerServiceId;
	}

	public void setProviderServiceId(String providerServiceId) {
		this.providerServiceId = providerServiceId;
	}

	public String getReference1() {
		return this.reference1;
	}

	public void setReference1(String reference1) {
		this.reference1 = reference1;
	}

	public String getReference2() {
		return this.reference2;
	}

	public void setReference2(String reference2) {
		this.reference2 = reference2;
	}

	public String getService_ProviderServiceId() {
		return this.service_ProviderServiceId;
	}

	public void setService_ProviderServiceId(String service_ProviderServiceId) {
		this.service_ProviderServiceId = service_ProviderServiceId;
	}

	public String getServiceProvider() {
		return this.serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getShipmentDeclaredCurrency() {
		return this.shipmentDeclaredCurrency;
	}

	public void setShipmentDeclaredCurrency(String shipmentDeclaredCurrency) {
		this.shipmentDeclaredCurrency = shipmentDeclaredCurrency;
	}

	public String getShipmentDeclaredValue() {
		return this.shipmentDeclaredValue;
	}

	public void setShipmentDeclaredValue(String shipmentDeclaredValue) {
		this.shipmentDeclaredValue = shipmentDeclaredValue;
	}

	public double getShipmentWeight() {
		return this.shipmentWeight;
	}

	public void setShipmentWeight(double shipmentWeight) {
		this.shipmentWeight = shipmentWeight;
	}

	public String getShippinAccountID() {
		return this.shippinAccountID;
	}

	public void setShippinAccountID(String shippinAccountID) {
		this.shippinAccountID = shippinAccountID;
	}

	public String getShippingAccountName() {
		return this.shippingAccountName;
	}

	public void setShippingAccountName(String shippingAccountName) {
		this.shippingAccountName = shippingAccountName;
	}

	public Date getShippingCreationDate() {
		return this.shippingCreationDate;
	}

	public void setShippingCreationDate(Date shippingCreationDate) {
		this.shippingCreationDate = shippingCreationDate;
	}

	public String getSpecialInstruction() {
		return this.specialInstruction;
	}

	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction;
	}

	public String getSurcharge() {
		return this.surcharge;
	}

	public void setSurcharge(String surcharge) {
		this.surcharge = surcharge;
	}

	public String getTermsOfTrade() {
		return this.termsOfTrade;
	}

	public void setTermsOfTrade(String termsOfTrade) {
		this.termsOfTrade = termsOfTrade;
	}

	public double getVolWeight() {
		return this.volWeight;
	}

	public void setVolWeight(double volWeight) {
		this.volWeight = volWeight;
	}

	public String getWeekBeginning() {
		return this.weekBeginning;
	}

	public void setWeekBeginning(String weekBeginning) {
		this.weekBeginning = weekBeginning;
	}

	public double getWidth() {
		return this.width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getZoneID() {
		return this.zoneID;
	}

	public void setZoneID(String zoneID) {
		this.zoneID = zoneID;
	}

	public List<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Address addAddress(Address address) {
		getAddresses().add(address);
		address.setItem(this);

		return address;
	}

	public Address removeAddress(Address address) {
		getAddresses().remove(address);
		address.setItem(null);

		return address;
	}

	public Invoice getInvoice() {
		return this.invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

}