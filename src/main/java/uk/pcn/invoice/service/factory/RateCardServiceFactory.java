package uk.pcn.invoice.service.factory;

import org.springframework.beans.factory.annotation.Autowired;

import uk.pcn.invoice.common.CustomerNames;
import uk.pcn.invoice.common.RateCardXML;
import uk.pcn.invoice.common.ServiceNames;
import uk.pcn.invoice.model.external.parcels.Services;
import uk.pcn.invoice.util.FileUtil;
import uk.pcn.invoice.util.XmlUtils;

public class RateCardServiceFactory {

	private Services services;
	@Autowired
	private static XmlUtils xmlUtils;

	private static final String priceCardsdestination = FileUtil.getClassPath()
			+ "util\\priceCards\\";
	private static final String priceCardExt = ".xml";

	public Services buildRateCard(String shippingAccountName,
			String serviceProvider) {
		String fileName = null;
		if (services != null) {
			// Yodel service
			if (serviceProvider.contains(ServiceNames.YODEL.getValue())) {
				switch (CustomerNames.getCustomerName(shippingAccountName)) {
				case EMOTIVE:
					fileName = RateCardXML.EMOTIVE_LTD_YODEL.toString();
					break;
				case JADE:
					fileName = RateCardXML.JADE_ENGINEERING_LTD_YODEL
							.toString();
					break;
				case MANOR_HYGIENE:
					fileName = RateCardXML.MANOR_HYGIENE_YODEL.toString();
					break;
				case BRIGADE_CLOTHING:
					fileName = RateCardXML.BRIGADE_CLOTHING_YODEL.toString();
					break;
				case SPHINX_IND_SUPPLIERS:
					fileName = RateCardXML.SPHINX_IND_SUPPLIERS_YODEL
							.toString();
					break;
				case THERMOSCREENS_LTD:
					fileName = RateCardXML.THERMOSCREEENS_LTD_YODEL.toString();
					break;
				case VEHICLE_SHIFT_AND_STORE:
					fileName = RateCardXML.VEHICLE_SHIFT_AND_STORE_LTD_YODEL
							.toString();
					break;
				}
			}

			services = (Services) xmlUtils.unmarshal(Services.class,
					priceCardsdestination + fileName + priceCardExt);
		}
		return services;
	}

}
