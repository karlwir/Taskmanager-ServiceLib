package se.kawi.taskmanagerservicelib.model;

import java.time.LocalDateTime;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(AbstractEntity.class)
public abstract class AbstractEntity_ {

	public static volatile SingularAttribute<AbstractEntity, Long> id;
	public static volatile SingularAttribute<AbstractEntity, String> itemKey;
	public static volatile SingularAttribute<AbstractEntity, LocalDateTime> updateDate;
	public static volatile SingularAttribute<AbstractEntity, LocalDateTime> createdDate;
}

