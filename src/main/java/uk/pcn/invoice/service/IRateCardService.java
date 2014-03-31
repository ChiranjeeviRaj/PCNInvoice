package uk.pcn.invoice.service;

import uk.pcn.invoice.model.Item;
import uk.pcn.invoice.model.external.palets.UkPaletsPrices;


public interface IRateCardService {
	//this method parse the rate card by using given service provider, shipping account name and id then returns the 
	// price.
	public double getOurPrice(Item item);
	
	//this method calculate the total prices by using given fuel sur charges and vat 20%
	public double calulateTotalPrice(double fuelCharge, double totalPrice);

	public double getFuelSurcharge(Item item);

	double getPaletPrice(uk.pcn.invoice.model.palets.Pitem item,
			UkPaletsPrices ukPaletsPrices);

	String getPaletDetails(uk.pcn.invoice.model.palets.Pitem item,
			UkPaletsPrices ukPaletsPrices);
}
