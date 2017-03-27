package se.kawi.taskmanagerservicelib.service;

public class ServiceEntityNotFoundException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public ServiceEntityNotFoundException() {
		super("Not found");
	}

	public ServiceEntityNotFoundException(String message) {
		super(message);
	}

}
