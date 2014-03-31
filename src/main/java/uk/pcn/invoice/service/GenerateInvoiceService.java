package uk.pcn.invoice.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.pcn.invoice.model.Address;
import uk.pcn.invoice.model.Invoice;
import uk.pcn.invoice.model.Item;
import uk.pcn.invoice.model.external.palets.UkPaletsPrices;
import uk.pcn.invoice.model.palets.Palet;
import uk.pcn.invoice.util.FileUtil;
import uk.pcn.invoice.util.PCNUtil;
import uk.pcn.invoice.util.XmlUtils;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
	@Component
	public class GenerateInvoiceService {
		private Logger log = Logger.getLogger(GenerateInvoiceService.class);
	    public static final Font FONT = new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL );
	    public static final Font BOLD = new Font(FontFamily.HELVETICA, 8, Font.BOLD);
		private String invoiceNumberStr;
		
		@Autowired
		private XmlUtils xmlUtils;
		@Resource(name = "messages")
		private Properties properties; 
		@Autowired
		private IRateCardService rateCardService;
		//"E:\\workspace-pcn\\PCNInvoice"
		private static final String pdfsDestination = FileUtil.getClassPath() + "util\\invoice_pdf\\";
		
		private static final String priceCardsdestination = FileUtil.getClassPath()
				+ "util\\priceCards\\";
		private static final String priceCardExt = ".xml";


		public void createPdf(Invoice invoiceList ){
			Document document = new Document();
			generateInvoiceNumber(invoiceList);
			String path = pdfsDestination + invoiceNumberStr + ".pdf";
			PCNUtil.filePath = path;
		      try {
				PdfWriter.getInstance(document, new FileOutputStream(path));
			    document.open();
			    addMetaData(document);
				Item firstItem = invoiceList.getItems().get(0);
			    addHeader(document, getCustomerName(firstItem.getShippingAccountName()));
			    document.add(Chunk.NEWLINE);
			    addCotent(invoiceList, document);
		        
			    document.close();
			    log.debug("Invoice generarted Succfully :" + path);
			} catch (Exception  e) {
				e.printStackTrace();
			    log.error("Invoice fail to generate :" + e);

			}
		}
		private void generateInvoiceNumber(Invoice invoiceList) {
			Item firstItem = invoiceList.getItems().get(0);
			String ShippingAccountName =firstItem.getShippingAccountName();
			if(firstItem.getShippingAccountName().equalsIgnoreCase(
					"POSTCODE NETWORK - BRIGADE CLOTHING"))	{
				invoiceNumberStr = "BRIG";
			}else if("EMOTIVE".contains(firstItem.getShippingAccountName().split(" ")[0])){
				invoiceNumberStr = "EMOT";
			}else if(ShippingAccountName.contains("MANOR")){
				invoiceNumberStr = "MANO";
			} else if(ShippingAccountName.contains("SPHINX")){
				invoiceNumberStr = "SPHI";
			}else if(ShippingAccountName.contains("THERMOSCREENS")){
				invoiceNumberStr = "THER";
			}else if(ShippingAccountName.contains("BIDDLE")){
				invoiceNumberStr = "BIDD";
			}	
			PCNUtil.invoiceNumStr = invoiceNumberStr;
		}
		private void addCotent(Invoice invoiceList, Document document)
				throws DocumentException {
			double totalPrice = 0;
			double itemPrice = 0;
			PdfPTable itemsTable = new PdfPTable(9);	
			itemsTable.setWidthPercentage(100);
			itemsTable.setWidths(new int[]{4, 2, 4, 2, 1, 4, 2, 1, 1});
			
			PdfPTable totalPriceTable = new PdfPTable(4);	
			totalPriceTable.setWidthPercentage(100);
			totalPriceTable.setWidths(new int[]{4, 4,3,2});

			itemsTable.addCell(new Phrase("DeliveryAddressComapny", BOLD));
			itemsTable.addCell(new Phrase("DeliveryPostcode", BOLD));
			itemsTable.addCell(new Phrase("DeliveryContactName", BOLD));
			itemsTable.addCell(new Phrase("CollectionDate", BOLD));
			itemsTable.addCell(new Phrase("Number", BOLD));
			itemsTable.addCell(new Phrase("CourierConsignmentTrackingNumber", BOLD));
			itemsTable.addCell(new Phrase("Reference1", BOLD));
			itemsTable.addCell(new Phrase("ShipmentWeight", BOLD));
			itemsTable.addCell(new Phrase("Price", BOLD));
			
			
			for (Item item : invoiceList.getItems()) {
				List<Address> addresses = item.getAddresses();
				for (Address address : addresses) {
					if(address.getAddersstype().getType().equals("DELIVERY")){
				    itemsTable.addCell(new Phrase(address.getCompany(), FONT));
				    itemsTable.addCell(new Phrase(address.getPostCode(), FONT));
				    itemsTable.addCell(new Phrase(address.getContactName(), FONT));
					}
				}
			    totalPrice += itemPrice = rateCardService.getOurPrice(item);
			    itemsTable.addCell(new Phrase(new SimpleDateFormat("dd/M/yyyy").format(item.getCollectionDate()), FONT));
			    itemsTable.addCell(new Phrase(item.getNumberOfParcels().toBigInteger().toString(), FONT));
			    itemsTable.addCell(new Phrase(item.getCourierConsignmentTrackingNumber(), FONT));
			    itemsTable.addCell(new Phrase(item.getReference1(), FONT));
			    itemsTable.addCell(new Phrase(Double.toString(item.getParcelWeight()), FONT));
			    itemsTable.addCell(new Phrase("£" +Double.toString(itemPrice), FONT));
			}
			
			document.add(itemsTable);
			
			
			totalPriceTable.addCell(new Phrase("PCN (UK) LTD", BOLD));
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(new Phrase("SUB TOTAL", BOLD));
			totalPriceTable.addCell(new Phrase("£" +Double.toString(roundDouble(totalPrice)), FONT));
			
			totalPriceTable.addCell(new Phrase("UNIT 3 PARAGON WAY", BOLD));
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(new Phrase("FUEL  SURCHARGE", BOLD));
			double fuelSurcharge = totalPrice * (rateCardService.getFuelSurcharge(invoiceList.getItems().get(0))/100);
			totalPriceTable.addCell(new Phrase("£" +Double.toString(roundDouble(fuelSurcharge)), FONT));
			
			totalPriceTable.addCell(new Phrase("BOYTON ROAD INDUSTRIAL ESTATE", BOLD));
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(new Phrase("VAT @ 20%", BOLD));
			double vat = (totalPrice * 0.2);
			totalPriceTable.addCell(new Phrase("£" +Double.toString(roundDouble(vat)), FONT));
			
			totalPriceTable.addCell(new Phrase("COVENTRY", BOLD));
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(new Phrase("INVOICE TOTAL", BOLD));
			totalPriceTable.addCell(new Phrase("£" +Double.toString(roundDouble(totalPrice + fuelSurcharge + vat)), BOLD));
			
			totalPriceTable.addCell(new Phrase("CV7 9QS", BOLD));
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(getBlankCell());
			
			totalPriceTable.addCell(new Phrase("VAT NUMBER 181 097795", BOLD));
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(getBlankCell());
			
			
			document.add(totalPriceTable);
			
			document.add(new Paragraph("Please Note all goods are carried under RHA 1998 and revised", FONT));
			document.add(new Paragraph("All invoice quiries must be rised within 7 days from date of invoice", FONT));
			document.add(new Paragraph("Telephone: 02476 361415 OR 02476 610613", FONT));

		}
		private void addHeader(Document document, String name) throws MalformedURLException, IOException  {
			PdfPTable header = new PdfPTable(3);
		try {
			header.setWidthPercentage(100);
			header.setWidths(new int[] { 5, 4, 4 });

				header.addCell(new Phrase(getAddressProperty(name, ".company"), BOLD));
				header.addCell(getBlankCell());
				header.addCell(new Phrase("INVOICE : " + invoiceNumberStr, BOLD));
				
				header.addCell(new Phrase(getAddressProperty(name, ".addressLine1"), BOLD));
				header.addCell(getBlankCell());
				header.addCell(new Phrase("DATE OF INVOICE : " +  new SimpleDateFormat("dd/M/yyyy").format(new Date()), BOLD));
				
				header.addCell(new Phrase(getAddressProperty(name, ".addressLine2"), BOLD));
				header.addCell(getBlankCell());
				header.addCell(new Phrase("OUR REF : " , BOLD));

			Image companyLogo = Image.getInstance(pdfsDestination + "pcn_logo.png");
			companyLogo.scalePercent(50);
			
			document.add(companyLogo);
			document.add(header);
			

		} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		private static void addMetaData(Document doc) {
				doc.addAuthor("Mark");
				doc.addCreationDate();
				doc.addProducer();
				doc.addCreator("Raju");
				doc.addTitle("Invoice");
				doc.setPageSize(PageSize.LETTER);
			  }
		private double roundDouble(double value){
			return Math.round(( value * 100.0 ) / 100.0);
		}
		
		private PdfPCell getBlankCell(){
			PdfPCell blankCell = new PdfPCell();
			blankCell.setBorder(Rectangle.NO_BORDER);
			return blankCell;
		}
		
		private String getCustomerName(String ShippingAccountName){
			if (ShippingAccountName.contains(
					"BRIGADE")) {
				return "brigade";
			}else if("EMOTIVE".contains(ShippingAccountName.split(" ")[0])){
				return "emotive";
			}else if(ShippingAccountName.contains("MANOR")){
				return "manor";
			} else if(ShippingAccountName.contains("SPHINX")){
				return "sphinx";
			}else if(ShippingAccountName.contains("MANOR")){
				return "manor";
			}else if(ShippingAccountName.contains("THERMOSCREENS")){
				return "thermoscreens";
			}else if(ShippingAccountName.contains("BIDDLE")){
				return "biddle";
			}
			return null;
		}
		private String getAddressProperty(String value, String ext){
			return properties.getProperty(value + ext);
		}
		public String createPaletsPdf(Palet palets) {
			Document document = new Document();
			String path = pdfsDestination + invoiceNumberStr + ".pdf";
			PCNUtil.filePath = path;
		      try {
				PdfWriter.getInstance(document, new FileOutputStream(path));
			    document.open();
			    addMetaData(document);
			    addHeader(document, "");
			    document.add(Chunk.NEWLINE);
			    addCotent(palets, document);
		        
			    document.close();
			    log.debug("Invoice generarted Succfully :" + path);
			} catch (Exception  e) {
				e.printStackTrace();
			    log.error("Invoice fail to generate :");

			}
		      return invoiceNumberStr;
		
		}
		private void addCotent(Palet palets, Document document) throws DocumentException {

			double totalPrice = 0;
			double itemPrice = 0;
			 //uk.pcn.invoice.model.palets.Pitem tempItem = palets.getPitems().get(0);
			String path = priceCardsdestination + "palets/UK_PALETS" + priceCardExt;
			log.debug("Rate card path : " + path);
			UkPaletsPrices ukPaletsPrices = (UkPaletsPrices) xmlUtils.unmarshal(
					UkPaletsPrices.class, path);
			PdfPTable itemsTable = new PdfPTable(8);	
			itemsTable.setWidthPercentage(100);
			itemsTable.setWidths(new int[]{2, 4, 2, 4, 1, 1, 1, 1});
			
			PdfPTable totalPriceTable = new PdfPTable(4);	
			totalPriceTable.setWidthPercentage(100);
			totalPriceTable.setWidths(new int[]{4, 4, 3, 2});

			itemsTable.addCell(new Phrase("Con", BOLD));
			itemsTable.addCell(new Phrase("Details", BOLD));
			itemsTable.addCell(new Phrase("From", BOLD));
			itemsTable.addCell(new Phrase("To/Ref", BOLD));
			itemsTable.addCell(new Phrase("Wgt", BOLD));
			//itemsTable.addCell(new Phrase("Packs", BOLD));
			itemsTable.addCell(new Phrase("Qty", BOLD));
			itemsTable.addCell(new Phrase("Rate", BOLD));
			itemsTable.addCell(new Phrase("£ Net", BOLD));
			
			
			for (uk.pcn.invoice.model.palets.Pitem item : palets.getPitems()) {
			    itemsTable.addCell(new Phrase(Long.toString(item.getCon()), FONT));//com
			    itemsTable.addCell(new Phrase(rateCardService.getPaletDetails(item, ukPaletsPrices), FONT));//details
			    itemsTable.addCell(new Phrase("", FONT));//From
			    itemsTable.addCell(new Phrase(item.getDelTown() + " " + item.getDelPoc(), FONT));//Ref
			    itemsTable.addCell(new Phrase("", FONT));//Wgt
			    itemsTable.addCell(new Phrase(Integer.toString(item.getPlts()), FONT));
			    totalPrice += itemPrice = rateCardService.getPaletPrice(item, ukPaletsPrices);//Qty
			    itemsTable.addCell(new Phrase(Double.toString(itemPrice), FONT));//Rate
			    itemsTable.addCell(new Phrase(Double.toString(itemPrice), FONT));//Net
			}
			
			document.add(itemsTable);
			
			
			totalPriceTable.addCell(new Phrase("PCN (UK) LTD", BOLD));
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(new Phrase("SUB TOTAL", BOLD));
			totalPriceTable.addCell(new Phrase(Double.toString(roundDouble(totalPrice)), FONT));
			
			totalPriceTable.addCell(new Phrase("UNIT 3 PARAGON WAY", BOLD));
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(new Phrase("FUEL  SURCHARGE", BOLD));
		//	double fuelSurcharge = totalPrice * (rateCardService.getFuelSurcharge(invoiceList.getItems().get(0))/100);
			//totalPriceTable.addCell(new Phrase(Double.toString(roundDouble(fuelSurcharge)), FONT));
			totalPriceTable.addCell(getBlankCell());
			
			totalPriceTable.addCell(new Phrase("BOYTON ROAD INDUSTRIAL ESTATE", BOLD));
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(new Phrase("VAT @ 20%", BOLD));
			double vat = (totalPrice * 0.2);
			totalPriceTable.addCell(new Phrase(Double.toString(roundDouble(vat)), FONT));
			
			totalPriceTable.addCell(new Phrase("COVENTRY", BOLD));
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(new Phrase("INVOICE TOTAL", BOLD));
			totalPriceTable.addCell(new Phrase(Double.toString(roundDouble(totalPrice + vat)), BOLD));
			
			totalPriceTable.addCell(new Phrase("CV7 9QS", BOLD));
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(getBlankCell());
			
			totalPriceTable.addCell(new Phrase("VAT NUMBER 181 097795", BOLD));
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(getBlankCell());
			
			
			document.add(totalPriceTable);
			
			document.add(new Paragraph("Please Note all goods are carried under RHA 1998 and revised", FONT));
			document.add(new Paragraph("All invoice quiries must be rised within 7 days from date of invoice", FONT));
			document.add(new Paragraph("Telephone: 02476 361415 OR 02476 610613", FONT));
		
			
		}

}
