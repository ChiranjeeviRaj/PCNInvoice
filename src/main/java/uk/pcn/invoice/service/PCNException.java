package uk.pcn.invoice.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PCNException extends RuntimeException {

	private static final long serialVersionUID = 3563989932405242658L;

	private static Logger LOGGER = LoggerFactory.getLogger(PCNException.class);

	public PCNException(String message) {
		super(message);
		LOGGER.error(message);
	}

	public PCNException(Throwable e) {
		super(e);
		LOGGER.error(ExceptionUtils.getStackTrace(e));
	}

	public PCNException(String message, Throwable t) {
		super(message, t);
		LOGGER.error(message, t);
	}

}
