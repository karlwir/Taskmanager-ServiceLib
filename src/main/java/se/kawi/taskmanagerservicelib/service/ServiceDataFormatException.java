package se.kawi.taskmanagerservicelib.service;

public class ServiceDataFormatException extends RuntimeException {

	private static final long serialVersionUID = 2549382532115158655L;

	public ServiceDataFormatException() {
		super("Input data had bad format");
	}

	public ServiceDataFormatException(String message) {
		super(message);
	}

	public ServiceDataFormatException(Throwable cause) {
		super(cause);
	}

	public ServiceDataFormatException(String message, Throwable cause) {
		super(message, cause);
	}

}
