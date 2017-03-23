package se.kawi.taskmanagerservicelib.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import se.kawi.taskmanagerservicelib.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User>, AbstractRepository<User> {

}