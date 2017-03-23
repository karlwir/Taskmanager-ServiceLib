package se.kawi.taskmanagerservicelib.model;

import javax.persistence.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {

	@Id
	@GeneratedValue
	private Long id;
	
	protected String itemKey;

	public Long getId() {
		return id;
	}
	
	public String getItemKey() {
		return itemKey;
	}
	
	abstract void createKey();
}
