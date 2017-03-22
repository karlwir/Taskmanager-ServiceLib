package se.kawi.taskmanagerservicelib.model;

import javax.persistence.Id;

import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

@MappedSuperclass
public abstract class AbstractEntity {

	@Id
	@GeneratedValue
	private Long id;
	
	private String itemKey;

	public Long getId() {
		return id;
	}
	
	public String getItemKey() {
		return itemKey;
	}
	
	@PrePersist
	public void createKey() {
		if (itemKey == null) {
			itemKey = UUID.randomUUID().toString();
		} 
	}
}
