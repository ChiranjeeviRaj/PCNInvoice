package uk.pcn.invoice.service;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import uk.pcn.invoice.common.AddressType;
import uk.pcn.invoice.common.CustomerNames;
import uk.pcn.invoice.common.RateCardXML;
import uk.pcn.invoice.common.ServiceNames;
import uk.pcn.invoice.common.ServiceType;
import uk.pcn.invoice.model.Address;
import uk.pcn.invoice.model.Item;
import uk.pcn.invoice.model.external.palets.Economy;
import uk.pcn.invoice.model.external.palets.Kgs;
import uk.pcn.invoice.model.external.palets.NextDay;
import uk.pcn.invoice.model.external.palets.PaletsService;
import uk.pcn.invoice.model.external.palets.Plts;
import uk.pcn.invoice.model.external.palets.UkPaletsPrices;
import uk.pcn.invoice.model.external.palets.Zone;
import uk.pcn.invoice.model.external.parcels.CongestionCharge;
import uk.pcn.invoice.model.external.parcels.PricePerWeight;
import uk.pcn.invoice.model.external.parcels.Service;
import uk.pcn.invoice.model.external.parcels.Services;
import uk.pcn.invoice.util.PCNUtil;
import uk.pcn.invoice.util.XmlUtils;

@Component
public class RateCardService implements IRateCardService {
	
	private Logger log = Logger.getLogger(RateCardService.class);
	@Autowired
	private XmlUtils xmlUtils;

	//private final String priceCardsdestination = xmlUtils.getClassPath()	+ "util\\priceCards\\";
	private static final String priceCardExt = ".xml";

	// this method parse the rate card by using given service provider, shipping
	// account name and id then returns the
	// price.
	@Override
	public double getOurPrice(Item item) {
		double surcharge = 0;
		double ourPrice = 0;
		double weight = 0;
		double londonSurcharge = 0;
		String path = xmlUtils.getFile(getRateCardFile(item.getShippingAccountName(), 
				item.getServiceProvider()) + priceCardExt	, "parcels");
		log.debug("Parcels Rate card path : " + path);
		/*String path =  getClassPath()
				+ "util\\priceCards\\"
				+ getRateCardFile(item.getShippingAccountName(),
						item.getServiceProvider()) + priceCardExt;*/
		//log.debug("Rate card path : " + path);
		Services rateCards = (Services) xmlUtils.unmarshal(
				Services.class, path);
		// if it follow the default rates, then gets the default rates
		if (rateCards.getPickPrices().equalsIgnoreCase("Default")) {
			List<CongestionCharge> congestionCharges = rateCards
					.getCongestionCharges().getCongestionCharge();
			for (CongestionCharge congestionCharge : congestionCharges) {
				// checks SINGLE or MULTIPLE Service
				if (checkSingleOrNot(item, congestionCharge)) {
					// Checks the weight of the item and compare the rate cards weight,
					// if the item weight is less than the rate card then returns the price
					//Please check rate card xml for more details
					if(item.getNumberOfParcels().intValue() != 1){
						weight = item.getParcelWeight()/item.getNumberOfParcels().intValue();
						item.setNumberOfParcels(new BigDecimal(1));
					}else{
						weight = item.getParcelWeight();
					}
					
					if (weight < congestionCharge.getPricePerWeights()
							.getPricePerWeight().get(0).getDefaultMaxWeight()) {
						ourPrice = congestionCharge.getPricePerWeights()
								.getPricePerWeight().get(0).getPrice();

					} else if (congestionCharge.getPricePerWeights()
							.getPricePerWeight().size() > 1
							&& weight < congestionCharge.getPricePerWeights()
									.getPricePerWeight().get(1)
									.getDefaultMaxWeight()) {
						ourPrice = congestionCharge.getPricePerWeights()
								.getPricePerWeight().get(1).getPrice();
					}
					break;
					
				}else if(congestionCharge.getServiceType().trim().equalsIgnoreCase("MULTIPLE")){
					//1. if the no of items are one but the item weight is more than the default weight then it would be multiple
					if (item.getParcelWeight() < congestionCharge.getPricePerWeights()
							.getPricePerWeight().get(0).getDefaultMaxWeight()) {
						ourPrice = congestionCharge.getPricePerWeights()
								.getPricePerWeight().get(0).getPrice();
					} else{
						//if its more than the default Max Weight then conignment Charge Per Kilo there after
						//total item weight - rate Card MaxWeight * consignment price.
						double remainingWeight = item.getParcelWeight() - congestionCharge.getPricePerWeights()
								.getPricePerWeight().get(0).getDefaultMaxWeight();
						double thereAfter =  congestionCharge.getConsignmentPerKilo();
						ourPrice = congestionCharge.getPricePerWeights()
								.getPricePerWeight().get(0).getPrice();
						ourPrice = ourPrice + (remainingWeight * thereAfter);
					}
					break;
				}
			}
		} else {
			// for Normal
			for (Service service : rateCards.getService()) {
				if (Double.parseDouble((item.getProviderServiceId())) == service
						.getId()) {
					surcharge = service.getSurCharge();
					// if its normal
					if (item.getParcelWeight() <= service.getMaxWeight()
							&& item.getLength() <= getLengthInCms(service
									.getMaxLength())) {
						ourPrice = service.getOurPrice();
					} else {
						// if More than the default weight the price would be 64
						//ourPrice = rateCards.getMaxWegightPrice();
						ourPrice = 64;
					}
				}
			}
		}

		// Follows the normal prices and if default price adds surchage.
		
		//add london srcharge;
		londonSurcharge =  addLondonSurcharge(item, rateCards.getLondonSurcharge());
		
		return ourPrice + surcharge + londonSurcharge;
	}

	private double addLondonSurcharge(Item item, double londonSurcharge) {
		String postCode = null;
		for (Address address : item.getAddresses()) {
			if(address.getAddersstype().equalsIgnoreCase(AddressType.DELIVERY.toString())){
				postCode = address.getPostCode();
			}
		}
		if(item.getInLondonCongestion().trim().equalsIgnoreCase("Yes") || PCNUtil.lodonPostCodeCheck(postCode)){
			return londonSurcharge;
		}
		return 0;
	}

	private boolean checkSingleOrNot(Item item,
			CongestionCharge congestionCharge) {
		//Rate card congestion charge should be single 
		//and the no of items are 1 should not exceed the maximum weight
		//single item if it exceeds return false it would be consider as multiple item even though that should not exceed maximum weight 31.5 for single item.
		if(congestionCharge.getServiceType().equalsIgnoreCase("SINGLE")){
			//no of items are 1 should not exceed the maximum weight
			if(item.getNumberOfParcels().intValue() == 1 && item.getShipmentWeight() > getDefaultMaxWeightForSingle(congestionCharge)){
				return false;
			}else{
				return true;
			}
		}
		return false;
	}

	private double getDefaultMaxWeightForSingle(
			CongestionCharge congestionCharge) {
		double weight = 0;
		for (PricePerWeight pricePerWeight : congestionCharge.getPricePerWeights().getPricePerWeight()) {
			weight = Math.max(weight, pricePerWeight.getDefaultMaxWeight());
		}
		return weight;
	}

	private double getLengthInCms(double maxLength) {
		return maxLength * 100;
	}

	private String getRateCardFile(String shippingAccountName,
			String serviceProvider) {
		// Yodel service
		if (serviceProvider.contains(ServiceNames.YODEL.getValue())) {
			switch (CustomerNames.getCustomerName(shippingAccountName)) {
			case EMOTIVE:
				return RateCardXML.EMOTIVE_LTD_YODEL.toString();
			case EMOTIVE_N:
				return RateCardXML.EMOTIVE_LTD_YODEL.toString();
			case JADE:
				return RateCardXML.JADE_ENGINEERING_LTD_YODEL.toString();
			case MANOR_HYGIENE:
				return RateCardXML.MANOR_HYGIENE_YODEL.toString();
			case BRIGADE_CLOTHING:
				return RateCardXML.BRIGADE_CLOTHING_YODEL.toString();
			case SPHINX_IND_SUPPLIERS:
				return RateCardXML.SPHINX_IND_SUPPLIERS_YODEL.toString();
			case THERMOSCREENS_LTD:
				return RateCardXML.THERMOSCREEENS_LTD_YODEL.toString();
			case BIDDLE_AIR_SYSTEMS_LTD:
				return RateCardXML.THERMOSCREEENS_LTD_YODEL.toString();
			case VEHICLE_SHIFT_AND_STORE:
				return RateCardXML.VEHICLE_SHIFT_AND_STORE_LTD_YODEL.toString();
			}
		}
		return null;
	}
	@Override
	public double getFuelSurcharge(Item item){
/*		String path =  getClassPath()
				+ "util\\priceCards\\"
				+ getRateCardFile(item.getShippingAccountName(),
						item.getServiceProvider()) + priceCardExt;*/
		String path = xmlUtils.getFile(getRateCardFile(item.getShippingAccountName(), 
				item.getServiceProvider()) + priceCardExt	, "parcels");
		log.debug("Rate card path : " + path);
		Services rateCards = (Services) xmlUtils.unmarshal(
				Services.class, path);
		rateCards.getService();
		
		return rateCards.getFuelSurCharges().getFuelSurChargePer();
	}

	@Override
	public double calulateTotalPrice(double fuelCharge, double totalPrice) {
		//20 per vat
		final double vatPer = 0.2;
		return totalPrice + fuelCharge + (totalPrice * vatPer);
	}

	@Override
	public double getPaletPrice(uk.pcn.invoice.model.palets.Pitem item, UkPaletsPrices ukPaletsPrices) {
			Zone zone = getZone(ukPaletsPrices.getZones().getZone(),
					item.getDelPoc());
			double ourPrice = 0;
			NextDay nextDay = null;
			Economy economy = null;
			Plts plt = null;
			Kgs kgs = null;
			//int days = item.getInput().getDay() - item.getPodDate().getDay();
			//next day
			//if(days == 1 || (zone.getName().equals("ZONE 6") && days == 2)){
			switch(getServiceType(item.getService())){
			case NEXTDAY : 
				nextDay = zone.getNextDay();
				plt = getPlt(nextDay.getPlts(), item.getPlts());
				//default 1-250 
				kgs = plt.getKgs().get(0);
				break;
			case ECONOMY: 
				economy = zone.getEconomy();
				plt = getPlt(economy.getPlts(), item.getPlts());
				//default 1-250 
				kgs = plt.getKgs().get(0);
				break;
			}
			ourPrice = Double.parseDouble(kgs.getValue().toString());
			ourPrice += getPaletsService(item.getService(), ukPaletsPrices.getPlatesServices().getPaletsService());
			return	ourPrice;
	}
	@Override
	public String getPaletDetails(uk.pcn.invoice.model.palets.Pitem item, UkPaletsPrices ukPaletsPrices){
		Zone zone = getZone(ukPaletsPrices.getZones().getZone(),
				item.getDelPoc());
		if(zone == null){
			throw new PCNException("No Zone found with the post code :" + item.getDelPoc());
		}
		return zone.getName() + " " + getServiceType(item.getService()) + " " +/* getPaletsServiceDetails(item.getService(), ukPaletsPrices.getPlatesServices().getPaletsService()) 
				+*/" 0-250 KG ";
	}

/*	private String getPaletsServiceDetails(String serviceCode,
			List<PaletsService> list) {
		for (PaletsService paletsService : list) {
			if(paletsService.getCode().equalsIgnoreCase(serviceCode)){
				return paletsService.getType() + " ";
			}
		}
		return " ";
	}*/

	private double getPaletsService(String serviceCode, List<PaletsService> list) {
		for (PaletsService paletsService : list) {
			if(paletsService.getCode().equalsIgnoreCase(serviceCode)){
				return Double.parseDouble(paletsService.getCharges().toString());
			}
		}
		return 0;
	}

	private Plts getPlt(List<Plts> pltsList, int nofPlts) {
		for (Plts plts : pltsList) {
			if(nofPlts <= plts.getMax().intValue()){
				return plts;
			}
		}
		return null;
	}

	private String getZoneName(String delPoc) {
		return null;
	}
	
	private Zone getZone(List<Zone> postCodesPerZoneList, String postCode){
		for (Zone zone : postCodesPerZoneList) {
			if(PCNUtil.lodonPostCodeCheck(zone.getPostCodes().split(", "), postCode))
				return zone;
		}
		return null;
	}
	
	private ServiceType getServiceType(String serviceCode){
		char x = serviceCode.charAt(0);
		if(x == '1'){
			return ServiceType.NEXTDAY;
		}else{
			return ServiceType.ECONOMY;
		}
	}
/*	public String getClassPath(){
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String webContentRoot = servletContext.getContextPath();
        log.debug("webContentRoot :" +webContentRoot);
		URL url = XmlUtils.class.getResource("XmlUtils.class");
        log.debug("url :" +url);
		String[] split = StringUtils.split(url.toString(), webContentRoot);
		log.debug("split :" +split);
		String path =  split[0].replaceAll("file:/", "");
		log.debug("path :" +path);
		return path + webContentRoot + "\\";
	}*/
}
