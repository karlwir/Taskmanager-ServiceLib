package se.kawi.taskmanagerservicelib.repository;

public interface AbstractRepository<E> {

	E findByItemKey(String itemKey);
	
}
