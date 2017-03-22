package se.kawi.taskmanagerservicelib.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Issue.class)
public abstract class Issue_ extends se.kawi.taskmanagerservicelib.model.AbstractEntity_ {

	public static volatile SingularAttribute<Issue, String> title;
	public static volatile SingularAttribute<Issue, String> description;
	public static volatile SingularAttribute<Issue, WorkItem> workItem;
	public static volatile SingularAttribute<Issue, Boolean> openIssue;

}

