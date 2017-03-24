package se.kawi.taskmanagerservicelib.service;

public class ServiceDataException extends RuntimeException {

	private static final long serialVersionUID = 2549382532115158655L;

	public ServiceDataException() {
	}

	public ServiceDataException(String message) {
		super(message);
	}

	public ServiceDataException(Throwable cause) {
		super(cause);
	}

	public ServiceDataException(String message, Throwable cause) {
		super(message, cause);
	}

}
