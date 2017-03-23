package se.kawi.taskmanagerservicelib.service;

import java.util.List;

import javax.ws.rs.WebApplicationException;

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
			throw new ServiceException("Execute failed: " + e.getMessage(), e, new WebApplicationException(400));
		} catch (DataAccessException e) {
			throw new ServiceException("Execute failed: " + e.getMessage(), e);
		}
	}
	
	protected <T> T transaction(Action<T> action) throws ServiceException {
		try {
			return serviceTransaction.execute(() -> action.execute());			
		} catch (DataIntegrityViolationException e) {
			throw new ServiceException("Transaction failed: " + e.getMessage(), e, new WebApplicationException(400));
		} catch (DataAccessException e) {
			throw new ServiceException("Transaction failed: " + e.getMessage(), e);
		}
	}
	
	public E save(E entity) throws ServiceException {
		return execute(() -> repository.save(entity));
	}
	
	public E getById(Long id) throws ServiceException {
		return execute(() -> repository.findOne(id));
	}

	public E getByItemKey(String itemKey) throws ServiceException {
		return execute(() -> repository.findByItemKey(itemKey));
	}
	
	public List<E> query(Specification<E> spec, Pageable pageable) throws ServiceException {
		return execute(() -> repository.findAll(spec, pageable)).getContent();
	}
	
	public Long count(Specification<E> spec) throws ServiceException {
		return execute(() -> repository.count(spec));
	}

	public void delete(E entity) throws ServiceException {
		transaction(() -> {
			repository.delete(entity);
			return null;
		});
	}

}
