package se.kawi.taskmanagerservicelib.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import se.kawi.taskmanagerservicelib.model.WorkItem;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WorkItemRepository extends PagingAndSortingRepository<WorkItem, Long>, JpaSpecificationExecutor<WorkItem>, AbstractRepository<WorkItem> {

}
