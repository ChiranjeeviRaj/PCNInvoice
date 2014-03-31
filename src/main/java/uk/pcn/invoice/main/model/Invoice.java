package uk.pcn.invoice.main.model;

import java.util.Date;

public class Invoice {
	private String PHTrackingNumber;

	private String accountNumber;

	private String additionalAccount1;

	private String additionalAccount2;

	private String additionalAccount3;

	private String additionalAccount4;

	private String additionalAccount5;

	private String aliasAccount;

	private double billableWeight;

	private Date collectionDate;

	private String courierAlternativeRefNumber;

	private String courierConsignmentTrackingNumber;
	
	private String courierParcelTrackingNumber;
	
	private String courierCollectionRefNumber;

	private String deleted;

	private String enhancement;

	private String exportReason;

	private double girth;

	private String goodsDescription;

	private double height;

	private String inLondonCongestion;

	private String leavesafeInstruction;

	private double length;

	private String month;

	private double numberOfParcels;

	private String outOfArea;

	private String parcelDeclaredValue;

	private double parcelWeight;

	private String reference1;

	private String reference2;

	private String shipmentDeclaredCurrency;

	private String shipmentDeclaredValue;

	private double shipmentWeight;

	private String shippinAccountID;

	private String shippingAccountName;

	private Date shippingCreationDate;

	private String specialInstruction;

	private String surcharge;

	private String termsOfTrade;

	private double volWeight;

	private String weekBeginning;

	private double width;

	private String year;

	private String zoneID;
	
	private Address collectionAddress;
	
	private Address deliveryAddress;
	
	private String providerServiceId;

	private String providerService;

	private String providerServiceCode;

	private String serviceProvider;

	private String serviceType;

	public String getPHTrackingNumber() {
		return PHTrackingNumber;
	}

	public void setPHTrackingNumber(String pHTrackingNumber) {
		PHTrackingNumber = pHTrackingNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAdditionalAccount1() {
		return additionalAccount1;
	}

	public void setAdditionalAccount1(String additionalAccount1) {
		this.additionalAccount1 = additionalAccount1;
	}

	public String getAdditionalAccount2() {
		return additionalAccount2;
	}

	public void setAdditionalAccount2(String additionalAccount2) {
		this.additionalAccount2 = additionalAccount2;
	}

	public String getAdditionalAccount3() {
		return additionalAccount3;
	}

	public void setAdditionalAccount3(String additionalAccount3) {
		this.additionalAccount3 = additionalAccount3;
	}

	public String getAdditionalAccount4() {
		return additionalAccount4;
	}

	public void setAdditionalAccount4(String additionalAccount4) {
		this.additionalAccount4 = additionalAccount4;
	}

	public String getAdditionalAccount5() {
		return additionalAccount5;
	}

	public void setAdditionalAccount5(String additionalAccount5) {
		this.additionalAccount5 = additionalAccount5;
	}

	public String getAliasAccount() {
		return aliasAccount;
	}

	public void setAliasAccount(String aliasAccount) {
		this.aliasAccount = aliasAccount;
	}

	public double getBillableWeight() {
		return billableWeight;
	}

	public void setBillableWeight(double billableWeight) {
		this.billableWeight = billableWeight;
	}

	public Date getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	public String getCourierAlternativeRefNumber() {
		return courierAlternativeRefNumber;
	}

	public void setCourierAlternativeRefNumber(String courierAlternativeRefNumber) {
		this.courierAlternativeRefNumber = courierAlternativeRefNumber;
	}

	public String getCourierConsignmentTrackingNumber() {
		return courierConsignmentTrackingNumber;
	}

	public void setCourierConsignmentTrackingNumber(
			String courierConsignmentTrackingNumber) {
		this.courierConsignmentTrackingNumber = courierConsignmentTrackingNumber;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getEnhancement() {
		return enhancement;
	}

	public void setEnhancement(String enhancement) {
		this.enhancement = enhancement;
	}

	public String getExportReason() {
		return exportReason;
	}

	public void setExportReason(String exportReason) {
		this.exportReason = exportReason;
	}

	public double getGirth() {
		return girth;
	}

	public void setGirth(double girth) {
		this.girth = girth;
	}

	public String getGoodsDescription() {
		return goodsDescription;
	}

	public void setGoodsDescription(String goodsDescription) {
		this.goodsDescription = goodsDescription;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getInLondonCongestion() {
		return inLondonCongestion;
	}

	public void setInLondonCongestion(String inLondonCongestion) {
		this.inLondonCongestion = inLondonCongestion;
	}

	public String getLeavesafeInstruction() {
		return leavesafeInstruction;
	}

	public void setLeavesafeInstruction(String leavesafeInstruction) {
		this.leavesafeInstruction = leavesafeInstruction;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public double getNumberOfParcels() {
		return numberOfParcels;
	}

	public void setNumberOfParcels(double numberOfParcels) {
		this.numberOfParcels = numberOfParcels;
	}

	public String getOutOfArea() {
		return outOfArea;
	}

	public void setOutOfArea(String outOfArea) {
		this.outOfArea = outOfArea;
	}

	public String getParcelDeclaredValue() {
		return parcelDeclaredValue;
	}

	public void setParcelDeclaredValue(String parcelDeclaredValue) {
		this.parcelDeclaredValue = parcelDeclaredValue;
	}

	public double getParcelWeight() {
		return parcelWeight;
	}

	public void setParcelWeight(double parcelWeight) {
		this.parcelWeight = parcelWeight;
	}

	public String getReference1() {
		return reference1;
	}

	public void setReference1(String reference1) {
		this.reference1 = reference1;
	}

	public String getReference2() {
		return reference2;
	}

	public void setReference2(String reference2) {
		this.reference2 = reference2;
	}

	public String getShipmentDeclaredCurrency() {
		return shipmentDeclaredCurrency;
	}

	public void setShipmentDeclaredCurrency(String shipmentDeclaredCurrency) {
		this.shipmentDeclaredCurrency = shipmentDeclaredCurrency;
	}

	public String getShipmentDeclaredValue() {
		return shipmentDeclaredValue;
	}

	public void setShipmentDeclaredValue(String shipmentDeclaredValue) {
		this.shipmentDeclaredValue = shipmentDeclaredValue;
	}

	public double getShipmentWeight() {
		return shipmentWeight;
	}

	public void setShipmentWeight(double shipmentWeight) {
		this.shipmentWeight = shipmentWeight;
	}

	public String getShippinAccountID() {
		return shippinAccountID;
	}

	public void setShippinAccountID(String shippinAccountID) {
		this.shippinAccountID = shippinAccountID;
	}

	public String getShippingAccountName() {
		return shippingAccountName;
	}

	public void setShippingAccountName(String shippingAccountName) {
		this.shippingAccountName = shippingAccountName;
	}

	public Date getShippingCreationDate() {
		return shippingCreationDate;
	}

	public void setShippingCreationDate(Date shippingCreationDate) {
		this.shippingCreationDate = shippingCreationDate;
	}

	public String getSpecialInstruction() {
		return specialInstruction;
	}

	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction;
	}

	public String getSurcharge() {
		return surcharge;
	}

	public void setSurcharge(String surcharge) {
		this.surcharge = surcharge;
	}

	public String getTermsOfTrade() {
		return termsOfTrade;
	}

	public void setTermsOfTrade(String termsOfTrade) {
		this.termsOfTrade = termsOfTrade;
	}

	public double getVolWeight() {
		return volWeight;
	}

	public void setVolWeight(double volWeight) {
		this.volWeight = volWeight;
	}

	public String getWeekBeginning() {
		return weekBeginning;
	}

	public void setWeekBeginning(String weekBeginning) {
		this.weekBeginning = weekBeginning;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getZoneID() {
		return zoneID;
	}

	public void setZoneID(String zoneID) {
		this.zoneID = zoneID;
	}

	public Address getCollectionAddress() {
		if(collectionAddress == null){
			collectionAddress = new Address();
			collectionAddress.setAddressType(AddressType.COLLECTION);
		}		
		return collectionAddress;
	}

	public void setCollectionAddress(Address collectionAddress) {
		this.collectionAddress = collectionAddress;
	}

	public Address getDeliveryAddress() {
		if(deliveryAddress == null){
			deliveryAddress = new Address();
			deliveryAddress.setAddressType(AddressType.DELIVERY);
		}
		return deliveryAddress;
	}

	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getProviderServiceId() {
		return providerServiceId;
	}

	public void setProviderServiceId(String providerServiceId) {
		this.providerServiceId = providerServiceId;
	}

	public String getProviderService() {
		return providerService;
	}

	public void setProviderService(String providerService) {
		this.providerService = providerService;
	}

	public String getProviderServiceCode() {
		return providerServiceCode;
	}

	public void setProviderServiceCode(String providerServiceCode) {
		this.providerServiceCode = providerServiceCode;
	}

	public String getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getCourierParcelTrackingNumber() {
		return courierParcelTrackingNumber;
	}

	public void setCourierParcelTrackingNumber(
			String courierParcelTrackingNumber) {
		this.courierParcelTrackingNumber = courierParcelTrackingNumber;
	}

	public String getCourierCollectionRefNumber() {
		return courierCollectionRefNumber;
	}

	public void setCourierCollectionRefNumber(String courierCollectionRefNumber) {
		this.courierCollectionRefNumber = courierCollectionRefNumber;
	}
	
	
}
