package se.kawi.taskmanagerservicelib.model;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {

	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String firstname;
	
	@Column(nullable = false)
	private String lastname;
	
	@Column
	private String email;

	@Column(nullable = false)
	private boolean active;
	
	@ManyToMany(mappedBy = "users", fetch=FetchType.EAGER)
	private Set<Team> teams;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private Set<WorkItem> workItems;
	
	protected User() {}

	public User(String username, String firstname, String lastname, String email) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.active = true;
	}
	
	@PrePersist
	void createKey() {
		if (itemKey == null) {
			String uuid = UUID.randomUUID().toString();
			itemKey = "b2dc" + uuid.substring(4);
		} 
	}

	public String getUsername() {
		return username;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}
	
	public String getEmail() {
		return email;
	}

	public boolean isActiveUser() {
		return active;
	}

	public Set<Team> getTeams() {
		return teams;
	}

	public User setTeams(Set<Team> teams) {
		this.teams = teams;
		return this;
	}
	
	public User setFirstName(String firstname) {
		this.firstname = firstname;
		return this;
	}

	public User setLastName(String lastname) {
		this.lastname = lastname;
		return this;
	}

	public User setUsername(String username) {
		this.username = username;
		return this;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public User setActiveUser(boolean active) {
		this.active = active;
		return this;
	}
	
	public User setWorkItems(Set<WorkItem> workItems) {
		this.workItems = workItems;
		return this;
	}

	public Set<WorkItem> getWorkItems() {
		return workItems;
	}

	public User addWorkItem(WorkItem workItem) {
		this.workItems.add(workItem);
		return this;
	}
	
	public User removeWorkItem(WorkItem workItem) {
		Set<WorkItem> newWorkItems = workItems.stream()
				.filter(w -> !w.getId().equals(workItem.getId()))
				.collect(Collectors.toSet());
		setWorkItems(newWorkItems);
		return this;
	}
	
	@Override
	public String toString() {
		return String.format("User: %s, %s, %s, %s, %s, active:%s", getId(), username, firstname, lastname, email, active);
	}
	
	@Override
	public boolean equals(Object other) {
		if (other != null && other instanceof User) {
			User otherUser = (User) other;
			return this.getId() == otherUser.getId();
		}
		return false;
	}

}
