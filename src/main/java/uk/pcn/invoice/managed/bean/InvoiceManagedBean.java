package uk.pcn.invoice.managed.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.pcn.invoice.main.model.AddressType;
import uk.pcn.invoice.model.Addersstype;
import uk.pcn.invoice.model.Address;
import uk.pcn.invoice.model.Invoice;
import uk.pcn.invoice.model.Item;
import uk.pcn.invoice.model.palets.Palet;
import uk.pcn.invoice.service.IInvoiceService;
import uk.pcn.invoice.util.FileUtil;
import uk.pcn.invoice.util.PCNUtil;
import uk.pcn.invoice.util.XlsReader;


@Component("invoiceMB")
@ManagedBean(name="invoiceMB")
@RequestScoped
public class InvoiceManagedBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(InvoiceManagedBean.class);
	private UploadedFile file; 
	private StreamedContent pdfInvoiceFile;
	private int fileType = 0;
	private String destination = FileUtil.getClassPath() + "util\\xls\\";

	@Autowired
	IInvoiceService invoiceService;
	
	public InvoiceManagedBean() {
        log.debug("From InvoiceManagedBean ******************");
	}

	 public void uploadXlsAndGenerateInvoice() {  
		 	String message;
		 	if(file != null){
	        if(fileType!=0){
	        try {
	            copyFile(file.getFileName(), file.getInputstream());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        XlsReader xlsReader = new XlsReader(destination + file.getFileName());
	        if(fileType == 1){

	        Invoice invloice = convertParcelXlsToInvoice(xlsReader);
	        //set unique id
	        invoiceService.generateInvoice(invloice);
	        // add to data base
	       invoiceService.addInvoice(invloice);
	        
	        message = "Successfully Parcels invoice uploaded";
	        } // palets
	        else{
	        	
	        	 Palet palets = convertPaletsXlsToInvoice(xlsReader);
	 	        //set unique id
	        	 invoiceService.generatePaletsInvoice(palets);
	 	        // add to data base
	 	       invoiceService.addPalets(palets);
	 	        
	 	       message = "Successfully Palets invoice uploaded";
	        }
	        }else{
		        FacesMessage warMsg = new FacesMessage("Warning", "Please select one of those Parcels/Palets ");  
		        FacesContext.getCurrentInstance().addMessage(null, warMsg); 
		        return;
	        }
	        
			InputStream stream = null;
			File invoicePdf = new File(PCNUtil.filePath);
			try {
				stream = new FileInputStream(invoicePdf);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			setPdfInvoiceFile(new DefaultStreamedContent(stream, externalContext.getMimeType(invoicePdf.getName()),
					invoicePdf.getName()));
					
	        log.debug(message  +  file.getFileName());
	        FacesMessage msg = new FacesMessage(message, file.getFileName());  
	        FacesContext.getCurrentInstance().addMessage(null, msg);  
		 	}
	    }  

	    private Palet convertPaletsXlsToInvoice(XlsReader xlsReader) {
	    	String sheet = "Consignments";
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
    		Palet palets = new Palet();
    		int temp = 0;
    		Date tempDate = null;
    		List<uk.pcn.invoice.model.palets.Pitem> items = new ArrayList<uk.pcn.invoice.model.palets.Pitem>();
	    	for (int i = 1; i < xlsReader.getRowCount(sheet) - 1; i++) {
	    		uk.pcn.invoice.model.palets.Pitem item = new uk.pcn.invoice.model.palets.Pitem();
	    		temp = !(xlsReader.getCellData(sheet, "CON", i, 1).equals("")) ? Integer.parseInt(xlsReader.getCellData(sheet, "CON", i, 1)) : 0;
	    		item.setCon(temp);
	    		temp = !(xlsReader.getCellData(sheet, "PLTS", i, 1).equals("")) ? Integer.parseInt(xlsReader.getCellData(sheet, "PLTS", i, 1)) : 0;
	    		item.setPlts(temp);
	    		item.setRef(xlsReader.getCellData(sheet, "REF", i));
	    		item.setDelTown(xlsReader.getCellData(sheet, "DEL TOWN", i));
	    		item.setDelPoc(xlsReader.getCellData(sheet, "DEL PC", i));
	    		item.setService(xlsReader.getCellData(sheet, "SERVICE", i));
	    		item.setTimeReq(xlsReader.getCellData(sheet, "TIME REQ'D", i));
	    		item.setPodName(xlsReader.getCellData(sheet, "POD NAME", i));
	    		item.setPodTime(xlsReader.getCellData(sheet, "POD TIME", i));

		    	try {
		    		tempDate = !(xlsReader.getCellData(sheet, "INPUT", i).equals("")) ? sdf.parse(xlsReader.getCellData(sheet, "INPUT", i)) : null;
		    		item.setInput(tempDate);
		    		tempDate = !(xlsReader.getCellData(sheet, "POD DATE", i).equals("") || xlsReader.getCellData(sheet, "POD DATE", i).isEmpty()) ? sdf.parse(xlsReader.getCellData(sheet, "POD DATE", i)) : null;
		    		item.setPodDate(tempDate);
		    		tempDate = !(xlsReader.getCellData(sheet, "DATE REQ'D", i).equals("")) ? sdf.parse(xlsReader.getCellData(sheet, "DATE REQ'D", i)) : null;
		    		item.setDateReq(tempDate);

				} catch (ParseException e) {
					e.printStackTrace();
					FacesContext.getCurrentInstance().addMessage("published_year", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalid Date",e.getMessage()));
				}

		    	items.add(item);
			}
	    	palets.setPitems(items);
	    	return palets;
	}

		private Invoice convertParcelXlsToInvoice(XlsReader xlsReader) {
	    	String sheet = "Sheet1";
	    	Invoice itemList = new Invoice();
	    	List<Item> items = new ArrayList<Item>();
	    	for (int i = 1; i < xlsReader.getRowCount(sheet); i++) {
	    		Item item = new Item();
		    	item.setShippinAccountID( xlsReader.getCellData(sheet, "ShippingAccountName", i));
		    	item.setShippingAccountName( xlsReader.getCellData(sheet, "ShippingAccountName", i));
		    	item.setServiceProvider(xlsReader.getCellData(sheet, "ServiceProvider", i));
		    	item.setServiceType(xlsReader.getCellData(sheet, "ServiceType", i));
		    	item.setProviderService(xlsReader.getCellData(sheet, "ProviderService", i));
		    	item.setProviderServiceCode(xlsReader.getCellData(sheet, "ProviderServiceCode", i));
		    	item.setProviderServiceId(xlsReader.getCellData(sheet, "ProviderServiceID", i));
		    	
		    	List<Address> address = new ArrayList<Address>(); 
		    	
		    	Address collectionAddress = new Address();
		    	Addersstype addersstype = new Addersstype();
		    	addersstype.setType(AddressType.COLLECTION.toString());
		    	collectionAddress.setAddersstype(addersstype);
		    	collectionAddress.setCompany(xlsReader.getCellData(sheet, "CollectionAddressCompany", i));
		    	collectionAddress.setAddressLine1(xlsReader.getCellData(sheet, "CollectionAddressLine1", i));
		    	collectionAddress.setAddressLine2(xlsReader.getCellData(sheet, "CollectionAddressLine2", i));
		    	collectionAddress.setTown(xlsReader.getCellData(sheet, "CollectionPostTown", i));
		    	collectionAddress.setArea(xlsReader.getCellData(sheet, "CollectionArea", i));
		    	collectionAddress.setPostCode(xlsReader.getCellData(sheet, "CollectionPostcode", i));
		    	collectionAddress.setCountry(xlsReader.getCellData(sheet, "CollectionCountryText", i));
		    	collectionAddress.setCountryCode(xlsReader.getCellData(sheet, "CollectionCountryCode", i));
		    	collectionAddress.setContactName(xlsReader.getCellData(sheet, "CollectionContactName", i));
		    	collectionAddress.setCotactTelephone(xlsReader.getCellData(sheet, "CollectionContactTelephone", i));
		    	collectionAddress.setEmail(xlsReader.getCellData(sheet, "CollectionContactEMail", i));
		    	collectionAddress.setItem(item);
		    	address.add(collectionAddress);
		    	
		    	Address deliveryAddress = new Address();
		    	Addersstype dAddersstype = new Addersstype();
		    	dAddersstype.setType(AddressType.DELIVERY.toString());
		    	deliveryAddress.setAddersstype(dAddersstype);
		    	deliveryAddress.setCompany(xlsReader.getCellData(sheet, "DeliveryAddressCompany", i));
		    	deliveryAddress.setAddressLine1(xlsReader.getCellData(sheet, "DeliveryAddressLine1", i));
		    	deliveryAddress.setAddressLine2(xlsReader.getCellData(sheet, "DeliveryAddressLine2", i));
		    	deliveryAddress.setTown(xlsReader.getCellData(sheet, "DeliveryPostTown", i));
		    	deliveryAddress.setArea(xlsReader.getCellData(sheet, "DeliveryArea", i));
		    	deliveryAddress.setPostCode(xlsReader.getCellData(sheet, "DeliveryPostcode", i));
		    	deliveryAddress.setCountry(xlsReader.getCellData(sheet, "DeliveryCountryText", i));
		    	deliveryAddress.setCountryCode(xlsReader.getCellData(sheet, "DeliveryCountryCode", i));
		    	deliveryAddress.setContactName(xlsReader.getCellData(sheet, "DeliveryContactName", i));
		    	deliveryAddress.setCotactTelephone(xlsReader.getCellData(sheet, "DeliveryContactTelephone", i));
		    	deliveryAddress.setEmail(xlsReader.getCellData(sheet, "DeliveryContactEMail", i));
		    	deliveryAddress.setItem(item);
		    	address.add(deliveryAddress);
		    	
		    	item.setAddresses(address);

		    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		    	try {
					item.setCollectionDate(sdf.parse(xlsReader.getCellData(sheet, "CollectionDate", i)));
			    	item.setShippingCreationDate(sdf.parse(xlsReader.getCellData(sheet, "ShipmentCreationDate", i)));

				} catch (ParseException e) {
					e.printStackTrace();
					FacesContext.getCurrentInstance().addMessage("published_year", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalid Date",e.getMessage()));
				}
		    	item.setNumberOfParcels(new BigDecimal(xlsReader.getCellData(sheet, "NumberOfParcel", i, Cell.CELL_TYPE_NUMERIC)));
		    	item.setCourierConsignmentTrackingNumber(xlsReader.getCellData(sheet, "CourierConsignmentTrackingNumber", i));
		    	item.setCourierParcelTrackingNumber(xlsReader.getCellData(sheet, "CourierParcelTrackingNumber", i));
		    	item.setCourierAlternativeRefNumber(xlsReader.getCellData(sheet, "CourierAlternativeRefNumber", i));
		    	item.setCourierCollectionRefNumber(xlsReader.getCellData(sheet, "CourierCollectionRefNumber", i));
		    	item.setPHTrackingNumber(xlsReader.getCellData(sheet, "PHTrackingNumber", i));
		    	item.setReference1(xlsReader.getCellData(sheet, "Reference1", i));
		    	item.setReference2(xlsReader.getCellData(sheet, "Reference2", i));
		    	item.setSpecialInstruction(xlsReader.getCellData(sheet, "SpecialInstruction", i));
		    	item.setLeavesafeInstruction(xlsReader.getCellData(sheet, "LeavesafeInstruction", i));
		    	item.setShipmentWeight(new Double(xlsReader.getCellData(sheet, "ShipmentWeight", i)));
		    	item.setParcelWeight(new Double(xlsReader.getCellData(sheet, "ParcelWeight", i)));
		    	item.setBillableWeight(new Double(xlsReader.getCellData(sheet, "BillableWeight", i)));
		    	item.setLength(new Double(xlsReader.getCellData(sheet, "Length", i)));
		    	item.setWidth(new Double(xlsReader.getCellData(sheet, "Width", i)));
		    	item.setHeight(new Double(xlsReader.getCellData(sheet, "Height", i)));
		    	item.setVolWeight(new Double(xlsReader.getCellData(sheet, "VolWeight", i)));
		    	item.setGirth(new Double(xlsReader.getCellData(sheet, "Girth", i)));
		    	item.setDeleted(xlsReader.getCellData(sheet, "Deleted", i));
		    	item.setAliasAccount(xlsReader.getCellData(sheet, "AliasAccount", i));
		    	item.setAccountNumber(xlsReader.getCellData(sheet, "AccountNumber", i));
		    	item.setAdditionalAccount1(xlsReader.getCellData(sheet, "AdditionalAccount1", i));
		    	item.setAdditionalAccount2(xlsReader.getCellData(sheet, "AdditionalAccount2", i));
		    	item.setAdditionalAccount3(xlsReader.getCellData(sheet, "AdditionalAccount3", i));
		    	item.setAdditionalAccount4(xlsReader.getCellData(sheet, "AdditionalAccount4", i));
		    	item.setAdditionalAccount5(xlsReader.getCellData(sheet, "AdditionalAccount5", i));
		    	item.setEnhancement(xlsReader.getCellData(sheet, "Enhancement", i));
		    	item.setShipmentDeclaredValue(xlsReader.getCellData(sheet, "ShipmentDeclaredValue", i));
		    	item.setParcelDeclaredValue(xlsReader.getCellData(sheet, "ParcelDeclaredValue", i));
		    	item.setGoodsDescription(xlsReader.getCellData(sheet, "GoodsDescription", i));
		    	item.setTermsOfTrade(xlsReader.getCellData(sheet, "TermsOfTrade", i));
		    	item.setExportReason(xlsReader.getCellData(sheet, "ExportReason", i));
		    	item.setWeekBeginning(xlsReader.getCellData(sheet, "WeekBeginning", i));
		    	item.setMonth(xlsReader.getCellData(sheet, "Month", i));
		    	item.setYear(xlsReader.getCellData(sheet, "Year", i));
		    	item.setOutOfArea(xlsReader.getCellData(sheet, "OutOfArea", i));
		    	item.setZoneID(xlsReader.getCellData(sheet, "ZoneID", i));
		    	item.setInLondonCongestion(xlsReader.getCellData(sheet, "InLondonCongestion", i));
		    	item.setSurcharge(xlsReader.getCellData(sheet, "Surcharge", i));
		    	
		    	item.setInvoice(itemList);
		    	items.add(item);
		    	
			}
	    	itemList.setItems(items);
	    	return itemList;
	}

		public void copyFile(String fileName, InputStream in) {
	           try {
	                // write the inputStream to a FileOutputStream
	                OutputStream out = new FileOutputStream(new File(destination + fileName));
	             
	                int read = 0;
	                byte[] bytes = new byte[1024];
	             
	                while ((read = in.read(bytes)) != -1) {
	                    out.write(bytes, 0, read);
	                }
	             
	                in.close();
	                out.flush();
	                out.close();
	             
	                System.out.println("New file created!");
	                } catch (IOException e) {
	                System.out.println(e.getMessage());
	                }
	    } 

	
	public UploadedFile getFile() {
		return file;
	}


	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}
	
/*	public void prepDownload() throws Exception {
	    File file = new File("C:\\file.csv");
	    InputStream input = new FileInputStream(file);
	    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	    setDownload(new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()), file.getName()));
	    System.out.println("PREP = " + download.getName());
	}
*/
	public StreamedContent getPdfInvoiceFile() {
		return this.pdfInvoiceFile;
	}
	
	public void setPdfInvoiceFile(StreamedContent pdfInvoiceFile){
		this.pdfInvoiceFile = pdfInvoiceFile;
	}


}