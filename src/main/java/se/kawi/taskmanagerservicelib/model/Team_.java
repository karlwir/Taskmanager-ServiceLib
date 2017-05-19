package se.kawi.taskmanagerservicelib.model;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Team.class)
public abstract class Team_ extends se.kawi.taskmanagerservicelib.model.AbstractEntity_ {

	public static volatile SingularAttribute<Team, String> name;
	public static volatile SingularAttribute<Team, String> description;
	public static volatile SingularAttribute<Team, Boolean> active;
	public static volatile SetAttribute<Team, User> users;

}

