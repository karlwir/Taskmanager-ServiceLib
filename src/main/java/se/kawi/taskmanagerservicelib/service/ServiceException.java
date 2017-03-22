package se.kawi.taskmanagerservicelib.service;

import javax.ws.rs.WebApplicationException;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 673945921543649662L;
	
	private WebApplicationException webApplicationException;

	public ServiceException(String message) {
		super(message);
		webApplicationException = new WebApplicationException(500);
	}
	
	public ServiceException(String message, WebApplicationException webApplicationException) {
		super(message);
		this.webApplicationException = webApplicationException;
	}
	
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		webApplicationException = new WebApplicationException(500);
	}

	public ServiceException(String message, Throwable cause, WebApplicationException webApplicationException) {
		super(message, cause);
		this.webApplicationException = webApplicationException;
	}

	public WebApplicationException getWebApplicationException() {
		return webApplicationException;
	}
}
