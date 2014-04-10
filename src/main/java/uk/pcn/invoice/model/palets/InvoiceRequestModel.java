package uk.pcn.invoice.model.palets;

import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

public class InvoiceRequestModel {
	private StreamedContent pdfInvoiceFile;
	private int fileType = 0;
	private UploadedFile file;
	
	public StreamedContent getPdfInvoiceFile() {
		return pdfInvoiceFile;
	}
	public void setPdfInvoiceFile(StreamedContent pdfInvoiceFile) {
		this.pdfInvoiceFile = pdfInvoiceFile;
	}
	public int getFileType() {
		return fileType;
	}
	public void setFileType(int fileType) {
		this.fileType = fileType;
	}
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	} 
	
}
