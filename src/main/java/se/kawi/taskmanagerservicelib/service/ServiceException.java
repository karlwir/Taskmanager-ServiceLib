package se.kawi.taskmanagerservicelib.service;

import javax.ws.rs.WebApplicationException;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 673945921543649662L;
	

	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(String message, WebApplicationException webApplicationException) {
		super(message);
	}
	
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message, Throwable cause, WebApplicationException webApplicationException) {
		super(message, cause);
	}
}
