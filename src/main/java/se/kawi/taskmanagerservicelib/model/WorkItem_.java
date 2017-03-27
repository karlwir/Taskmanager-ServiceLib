package se.kawi.taskmanagerservicelib.model;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import se.kawi.taskmanagerservicelib.model.WorkItem.Status;

@StaticMetamodel(WorkItem.class)
public abstract class WorkItem_ extends se.kawi.taskmanagerservicelib.model.AbstractEntity_ {

	public static volatile SingularAttribute<WorkItem, String> description;
	public static volatile SingularAttribute<WorkItem, String> title;
	public static volatile SetAttribute<WorkItem, User> users;
	public static volatile SetAttribute<WorkItem, Issue> issues;
	public static volatile SingularAttribute<WorkItem, Status> status;

}

