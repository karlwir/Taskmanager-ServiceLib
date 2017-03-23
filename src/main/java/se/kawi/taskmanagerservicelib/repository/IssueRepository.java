package se.kawi.taskmanagerservicelib.repository;

import se.kawi.taskmanagerservicelib.model.Issue;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IssueRepository extends PagingAndSortingRepository<Issue, Long>, JpaSpecificationExecutor<Issue>, AbstractRepository<Issue> {

}