package se.kawi.taskmanagerservicelib.model;

import javax.persistence.Id;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AbstractEntity {

	@Id
	@GeneratedValue
	private Long id;
	
	protected String itemKey;
	
	@CreatedDate
	protected LocalDateTime createdDate;
	
	@LastModifiedDate
	protected LocalDateTime updateDate;

	public Long getId() {
		return id;
	}
	
	public String getItemKey() {
		return itemKey;
	}
	
	abstract void createKey();
	
	public LocalDateTime getUpdateDate() {
		return updateDate;
	}
	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
}
