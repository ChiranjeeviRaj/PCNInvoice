package uk.pcn.invoice.common;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import uk.pcn.invoice.service.PCNException;

public enum CustomerNames {
	EMOTIVE("EMOTIVE (DEESIDE)"),EMOTIVE_N("EMOTIVE(NUNEATON)"), BRIGADE_CLOTHING("POSTCODE NETWORK - BRIGADE CLOTHING"), JADE("JADE"), 
	THERMOSCREENS_LTD("THERMOSCREENS LTD"), BIDDLE_AIR_SYSTEMS_LTD("BIDDLE AIR SYSTEMS LTD"),
	MANOR_HYGIENE("MANOR HYGIENE SUPPLIES LTD"), SPHINX_IND_SUPPLIERS("SPHINX IND SUPPLIERS LTD"), 
	VEHICLE_SHIFT_AND_STORE("VEHICLE SHIFT AND STORE LTD");
	
	private String name;

	private CustomerNames(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.name;
	}
	
	public static CustomerNames getCustomerName(String name){
		CustomerNames customerNames = null;
		for (CustomerNames customerName : CustomerNames.values()) {
			if(customerName.getValue().equalsIgnoreCase(name) || customerName.getValue().contains(name.split(" ")[0])){
				customerNames = customerName;
			}
		}
		if(customerNames == null){
	        FacesMessage warMsg = new FacesMessage("Warning", "Customer is not registered on our system : "+ name);  
	        FacesContext.getCurrentInstance().addMessage(null, warMsg); 
			throw new PCNException("Customer is not registered on our system : "+ name);
		}
		return customerNames;
	}
}
