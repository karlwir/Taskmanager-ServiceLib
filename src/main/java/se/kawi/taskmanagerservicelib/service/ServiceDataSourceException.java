package se.kawi.taskmanagerservicelib.service;

public class ServiceDataSourceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public ServiceDataSourceException() {
		super("Error in application data source.");
	}

	public ServiceDataSourceException(String message) {
		super(message);
	}

	public ServiceDataSourceException(String message, Throwable cause) {
		super(message, cause);
	}


}
