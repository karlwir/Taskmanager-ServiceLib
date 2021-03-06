package se.kawi.taskmanagerservicelib.model;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
public abstract class User_ extends se.kawi.taskmanagerservicelib.model.AbstractEntity_ {

	public static volatile SingularAttribute<User, String> firstname;
	public static volatile SingularAttribute<User, String> lastname;
	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SetAttribute<User, WorkItem> workItems;
	public static volatile SetAttribute<User, Team> teams;
	public static volatile SingularAttribute<User, Boolean> active;

}

