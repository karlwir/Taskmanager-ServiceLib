package se.kawi.taskmanagerservicelib.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import se.kawi.taskmanagerservicelib.model.AbstractEntity;
import se.kawi.taskmanagerservicelib.repository.AbstractRepository;
import se.kawi.taskmanagerservicelib.service.ServiceException;
import se.kawi.taskmanagerservicelib.service.ServiceTransaction.Action;

public abstract class BaseService<E extends AbstractEntity, R extends PagingAndSortingRepository<E, Long> & JpaSpecificationExecutor<E> & AbstractRepository<E>> {

	protected R repository;
	
	@Autowired
	protected ServiceTransaction serviceTransaction;
	
	@Autowired
	protected UserService userService;
	@Autowired
	protected WorkItemService workItemService;
	@Autowired
	protected IssueService issueService;
	@Autowired
	protected TeamService teamService;
	
	protected BaseService(R repository) {
		this.repository = repository;
	}

	protected <T> T execute(Action<T> action) throws ServiceException {
		try {
			return action.execute();			
		} catch (DataIntegrityViolationException e) {
			throw new ServiceException("Execute failed: " + e.getMessage(), e);
		} catch (DataAccessException e) {
			throw new ServiceDataSourceException("Execute failed: " + e.getMessage(), e);
		} catch (ServiceDataFormatException e) {
			throw new ServiceDataFormatException("Execute failed: " + e.getMessage(), e);
		}
	}
	
	protected <T> T transaction(Action<T> action) throws ServiceException {
		return serviceTransaction.executeInTransaction(() -> execute(action));
	}
	
	public E save(E entity) throws ServiceException {
		return execute(() -> repository.save(entity));
	}

	public E getByItemKey(String itemKey) throws ServiceException {
		return execute(() -> {
			E entity = repository.findByItemKey(itemKey);
			if (entity == null) {
				throw new ServiceEntityNotFoundException("No entity found for item key: " + itemKey);
			}
			return entity;
		});
	}
	
	public List<E> query(Specification<E> spec, Pageable pageable) throws ServiceException {
		return execute(() -> repository.findAll(spec, pageable)).getContent();
	}
	
	public Long count(Specification<E> spec) throws ServiceException {
		return execute(() -> repository.count(spec));
	}

	public void delete(E entity) throws ServiceException {
		execute(() -> {
			repository.delete(entity);
			return null;
		});
	}

}
