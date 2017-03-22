package se.kawi.taskmanagerservicelib.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
class ServiceTransaction {

	@Transactional
	public <T> T execute(Action<T> action) throws ServiceException {
		return action.execute();
	}

	public static interface Action<T> {
		T execute() throws ServiceException;

	}
}
