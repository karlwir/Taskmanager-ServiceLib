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
@Table(name = "teams")
public class Team extends AbstractEntity {

	@Column(nullable = false, unique = true)
	private String name;
	
	@Column(nullable = false, unique = true)
	private String description;
	
	@Column(nullable = false)
	private boolean active;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private Set<User> users;

	protected Team() {}

	public Team(String name, String description) {
		this.name = name;
		this.description = description;
		this.active = true;
	}
	
	@PrePersist
	void createKey() {
		if (itemKey == null) {
			String uuid = UUID.randomUUID().toString();
			itemKey = "b2db" + uuid.substring(4);
		} 
	}

	public String getName() {
		return name;
	}
	
	public Team setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Set<User> getUsers() {
		return users;
	}
	
	private void setUsers(Set<User> users) {
		this.users = users;
	}

	public Team addUser(User user) {
		this.users.add(user);
		return this;
	}
	
	public Team removeUser(User user) {
		Set<User> newUsers = users.stream()
				.filter(u -> !u.getId().equals(user.getId()))
				.collect(Collectors.toSet());
		setUsers(newUsers);
		return this;
	}
	
	public boolean isActive() {
		return active;
	}

	public Team setActive(boolean active) {
		this.active = active;
		return this;
	}

	@Override
	public String toString() {
		return String.format("Team: %s, %s, %s, active:%s", getId(), name, description, active);
	}

}
