package uk.pcn.invoice.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
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
import uk.pcn.invoice.model.palets.Pitem;
import uk.pcn.invoice.util.PCNUtil;
import uk.pcn.invoice.util.XmlUtils;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
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
	    public static final Font TITLE = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
		private String invoiceNumberStr;
		
		@Autowired
		private XmlUtils xmlUtils;
		@Resource(name = "messages")
		private Properties properties; 
		@Autowired
		private IRateCardService rateCardService;

		public void createPdf(Invoice invoiceList ){
			Document document = new Document();
			generateInvoiceNumber(invoiceList);
			String path = System.getProperty("java.io.tmpdir")  + File.separatorChar +PCNUtil.invoiceNumStr + ".pdf";
			PCNUtil.filePath = path;
		      try {
				PdfWriter.getInstance(document, new FileOutputStream(path));
			    document.open();
			    addMetaData(document);
				Item firstItem = invoiceList.getItems().get(0);
				document.add(new Phrase("PCN (UK) LTD", TITLE));
				document.add(Chunk.NEWLINE);
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
			PCNUtil.invoiceNumStr = invoiceNumberStr + PCNUtil.invoiceNumStr;
		}
		private void addCotent(Invoice invoiceList, Document document)
				throws DocumentException {
			double totalPrice = 0;
			double itemPrice = 0;
			PdfPTable itemsTable = new PdfPTable(10);	
			itemsTable.setWidthPercentage(100);
			itemsTable.setWidths(new int[]{4, 4, 2, 4, 2, 1, 4, 2, 1, 1});
			
			PdfPTable totalPriceTable = new PdfPTable(4);	
			totalPriceTable.setWidthPercentage(100);
			totalPriceTable.setWidths(new int[]{4, 4,3,2});

			itemsTable.addCell(new Phrase("Service", BOLD));
			itemsTable.addCell(new Phrase("Address", BOLD));
			itemsTable.addCell(new Phrase("Postcode", BOLD));
			itemsTable.addCell(new Phrase("Name", BOLD));
			itemsTable.addCell(new Phrase("Date", BOLD));
			itemsTable.addCell(new Phrase("Parcels", BOLD));
			itemsTable.addCell(new Phrase("Consignment no", BOLD));
			itemsTable.addCell(new Phrase("Reference1", BOLD));
			itemsTable.addCell(new Phrase("Weight", BOLD));
			itemsTable.addCell(new Phrase("Price", BOLD));
			
			
			for (Item item : invoiceList.getItems()) {
				List<Address> addresses = item.getAddresses();
				itemsTable.addCell(new Phrase(item.getProviderService(), FONT));
				for (Address address : addresses) {
					if(address.getAddersstype().equals("DELIVERY")){
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
			    itemsTable.addCell(new Phrase(dF(item.getParcelWeight()), FONT));
			    itemsTable.addCell(new Phrase(properties.getProperty("pound.currency") +dF(round(itemPrice, 3)), FONT));
			}
			
			document.add(itemsTable);
			
			
			totalPriceTable.addCell(new Phrase("PCN (UK) LTD", BOLD));
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(new Phrase("SUB TOTAL", BOLD));
			totalPriceTable.addCell(new Phrase(properties.getProperty("pound.currency") +dF(round(totalPrice, 3)), FONT));
			
			totalPriceTable.addCell(new Phrase("UNIT 3 PARAGON WAY", BOLD));
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(new Phrase("FUEL  SURCHARGE", BOLD));
			double fuelSurcharge = totalPrice * (rateCardService.getFuelSurcharge(invoiceList.getItems().get(0))/100);
			totalPriceTable.addCell(new Phrase(properties.getProperty("pound.currency") +dF(round(fuelSurcharge, 3)), FONT));
			
			totalPriceTable.addCell(new Phrase("BOYTON ROAD INDUSTRIAL ESTATE", BOLD));
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(new Phrase("VAT @ 20%", BOLD));
			double vat = (totalPrice * 0.2);
			totalPriceTable.addCell(new Phrase(properties.getProperty("pound.currency") +dF(round(vat, 3)), FONT));
			
			totalPriceTable.addCell(new Phrase("COVENTRY", BOLD));
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(new Phrase("INVOICE TOTAL", BOLD));
			totalPriceTable.addCell(new Phrase(properties.getProperty("pound.currency") +dF(round(totalPrice + fuelSurcharge + vat, 3)), BOLD));
			
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
				header.addCell(new Phrase("INVOICE : " + PCNUtil.invoiceNumStr, BOLD));
				
				header.addCell(new Phrase(getAddressProperty(name, ".addressLine1"), BOLD));
				header.addCell(getBlankCell());
				header.addCell(new Phrase("DATE OF INVOICE : " +  new SimpleDateFormat("dd/M/yyyy").format(new Date()), BOLD));
				
				header.addCell(new Phrase(getAddressProperty(name, ".addressLine2"), BOLD));
				header.addCell(getBlankCell());
				header.addCell(new Phrase("OUR REF : " , BOLD));

			//Image companyLogo = Image.getInstance(getClassPath() + "util\\invoice_pdf\\" + "pcn_logo.png");
			//companyLogo.scalePercent(50);
			//Company logo is removed base on requirement.
			//document.add(companyLogo);
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
		public double round(double value, int places) {
		    if (places < 0) throw new IllegalArgumentException();

		    long factor = (long) Math.pow(10, places);
		    value = value * factor;
		    long tmp = Math.round(value);
		    return (double) tmp / factor;
		}
		
		public String dF(double d){
			return new DecimalFormat("##.00").format(d);
		}
		
		private PdfPCell getBlankCell(){
			PdfPCell blankCell = new PdfPCell();
			blankCell.setBorder(Rectangle.NO_BORDER);
			return blankCell;
		}
		private String getCustomerName(String name){
			if (name.contains(
					"BRIGADE")) {
				return "brigade";
			}else if("EMOTIVE".contains(name.split(" ")[0])){
				return "emotive";
			}else if(name.contains("MANOR")){
				return "manor";
			} else if(name.contains("SPHINX")){
				return "sphinx";
			}else if(name.contains("MANOR")){
				return "manor";
			}else if(name.contains("THERMOSCREENS") || name.equalsIgnoreCase(
					"oscreens") || name.equalsIgnoreCase(
							"moscreen")){
				return "thermoscreens";
			}else if(name.contains("BIDDLE") || name.equalsIgnoreCase(
					"C/biddle") || name.equalsIgnoreCase(
							"C/BIDDLE")){
				return "biddle";
			}
			return null;
		}
		private String getAddressProperty(String value, String ext){
			return properties.getProperty(value + ext);
		}
		public String createPaletsPdf(Palet palets) {
			Document document = new Document();
			generateInvoiceNumber(palets);
			String path = System.getProperty("java.io.tmpdir")  + File.separatorChar +PCNUtil.invoiceNumStr + ".pdf";
			//String path = getClassPath() + "util\\invoice_pdf\\" + invoiceNumberStr + ".pdf";
			PCNUtil.filePath = path;
		      try {
				PdfWriter.getInstance(document, new FileOutputStream(path));
			    document.open();
			    addMetaData(document);
				document.add(new Phrase("PCN (UK) LTD", TITLE));
				document.add(Chunk.NEWLINE);
			    Pitem pitem = palets.getPitems().get(0);
			    addHeader(document, getCustomerName(pitem.getRef()));
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
		private void generateInvoiceNumber(Palet palets) {
			//to do
			//oscreens , moscreen, MOSCREEN,  C/biddle, C/BIDDLE

			Pitem firstItem = palets.getPitems().get(0);
			String ref =firstItem.getRef();
			if(ref.equalsIgnoreCase(
					"oscreens") || ref.equalsIgnoreCase(
							"moscreen"))	{
				invoiceNumberStr = "THER";
			}else if(ref.equalsIgnoreCase(
					"C/biddle") || ref.equalsIgnoreCase(
							"C/BIDDLE")){
				invoiceNumberStr = "BIDD";
			}
			PCNUtil.invoiceNumStr = invoiceNumberStr + PCNUtil.invoiceNumStr;
		
		}
		private void addCotent(Palet palets, Document document) throws DocumentException {

			double totalPrice = 0;
			double itemPrice = 0;
			 //uk.pcn.invoice.model.palets.Pitem tempItem = palets.getPitems().get(0);
			//String path = getClassPath()+ "util\\priceCards\\" + "palets/UK_PALETS" + priceCardExt;
			String path = xmlUtils.getFile("UK_PALETS.xml", "palets");
			log.debug("Palets Rate card path : " + path);
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
			itemsTable.addCell(new Phrase(properties.getProperty("pound.currency") +" Net", BOLD));
			
			
			for (uk.pcn.invoice.model.palets.Pitem item : palets.getPitems()) {
			    itemsTable.addCell(new Phrase(Long.toString(item.getCon()), FONT));//com
			    itemsTable.addCell(new Phrase(rateCardService.getPaletDetails(item, ukPaletsPrices), FONT));//details
			    itemsTable.addCell(new Phrase("", FONT));//From
			    itemsTable.addCell(new Phrase(item.getDelTown() + " " + item.getDelPoc(), FONT));//Ref
			    itemsTable.addCell(new Phrase("", FONT));//Wgt
			    itemsTable.addCell(new Phrase(Integer.toString(item.getPlts()), FONT));
			    totalPrice += itemPrice = rateCardService.getPaletPrice(item, ukPaletsPrices);//Qty
			    itemsTable.addCell(new Phrase(dF(itemPrice), FONT));//Rate
			    itemsTable.addCell(new Phrase(dF(itemPrice), FONT));//Net
			}
			
			document.add(itemsTable);
			
			
			totalPriceTable.addCell(new Phrase("PCN (UK) LTD", BOLD));
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(new Phrase("SUB TOTAL", BOLD));
			totalPriceTable.addCell(new Phrase(dF(round(totalPrice, 3)), FONT));
			
			totalPriceTable.addCell(new Phrase("UNIT 3 PARAGON WAY", BOLD));
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(new Phrase("FUEL  SURCHARGE", BOLD));
		//	double fuelSurcharge = totalPrice * (rateCardService.getFuelSurcharge(invoiceList.getItems().get(0))/100);
			//totalPriceTable.addCell(new Phrase(dF(roundDouble(fuelSurcharge)), FONT));
			totalPriceTable.addCell(getBlankCell());
			
			totalPriceTable.addCell(new Phrase("BOYTON ROAD INDUSTRIAL ESTATE", BOLD));
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(new Phrase("VAT @ 20%", BOLD));
			double vat = (totalPrice * 0.2);
			totalPriceTable.addCell(new Phrase(dF(round(vat, 3)), FONT));
			
			totalPriceTable.addCell(new Phrase("COVENTRY", BOLD));
			totalPriceTable.addCell(getBlankCell());
			totalPriceTable.addCell(new Phrase("INVOICE TOTAL", BOLD));
			totalPriceTable.addCell(new Phrase(dF(round(totalPrice + vat, 3)), BOLD));
			
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
