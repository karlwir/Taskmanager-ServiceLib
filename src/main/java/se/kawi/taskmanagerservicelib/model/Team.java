package se.kawi.taskmanagerservicelib.model;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@Entity
@Table(name = "teams")
@JsonIgnoreProperties(value = {"users"}, allowGetters=true, allowSetters=false)
public class Team extends AbstractEntity {

	@Column(nullable = false, unique = true)
	private String teamName;
	
	@Column(nullable = false)
	private boolean activeTeam;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JsonIgnoreProperties(value = {"teams", "workItems"}, allowGetters=false, allowSetters=false)
	private Set<User> users;

	protected Team() {}

	public Team(String teamName) {
		this.teamName = teamName;
		this.activeTeam = true;
	}

	public String getTeamName() {
		return teamName;
	}
	
	public Team setTeamName(String teamName) {
		this.teamName = teamName;
		return this;
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
	
	public boolean isActiveTeam() {
		return activeTeam;
	}

	public Team setActiveTeam(boolean activeTeam) {
		this.activeTeam = activeTeam;
		return this;
	}

	@Override
	public String toString() {
		return String.format("Team: %s, %s, active:%s", getId(), teamName, activeTeam);
	}

}
