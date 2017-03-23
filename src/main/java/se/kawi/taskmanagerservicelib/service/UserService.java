package se.kawi.taskmanagerservicelib.service;

import org.springframework.stereotype.Component;

import se.kawi.taskmanagerservicelib.model.User;
import se.kawi.taskmanagerservicelib.model.WorkItem;
import se.kawi.taskmanagerservicelib.model.WorkItem.Status;
import se.kawi.taskmanagerservicelib.repository.UserRepository;

import java.util.List;
import java.util.Set;

import javax.ws.rs.WebApplicationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Component
public class UserService extends BaseService<User, UserRepository> {

	private static final int USERNAME_MIN_LENGTH = 6;
	private static final int USER_MAX_WORKITEMS = 5;

	@Autowired
	public UserService(UserRepository userRepository) {
		super(userRepository);
	}
	
	public List<WorkItem> getUserWorkItems(Specification<WorkItem> spec, Pageable pageable) throws ServiceException {
		return workItemService.query(spec, pageable);
	}
	
	public User assignWorkItem(String workItemItemKey, User user) throws ServiceException {
		if(!user.isActiveUser()) {			
			throw new ServiceException("Cant assign to inactive user", new WebApplicationException("Cant assign to inactive user", 400));
		} else if(user.getWorkItems().size() >= USER_MAX_WORKITEMS) {
			throw new ServiceException("User cant be assigned to more items", new WebApplicationException("User cant be assigned to more items", 400));
		} else {
			WorkItem workItem = workItemService.getByItemKey(workItemItemKey);
			user.addWorkItem(workItem);
			return save(user);
		}
	}
	
	public User withdrawWorkItem(String workItemItemKey, User user) throws ServiceException {
		WorkItem workItem = workItemService.getByItemKey(workItemItemKey);
		user.removeWorkItem(workItem);
		return save(user);
	}

	@Override
	public User save(User user) throws ServiceException {
		return transaction(() -> {
			if (user.getUsername().length() < USERNAME_MIN_LENGTH) {
				throw new ServiceException("Invalid username", new WebApplicationException("Invalid username", 400));
			}
			if (!user.isActiveUser() && user.getId() != null) {
				// Handle users work items before making user inactive 
				Set<WorkItem> workItems = getById(user.getId()).getWorkItems();
				workItems.stream()
					 .filter(w -> !w.getStatus().equals(Status.ARCHIVED))
					 .forEach(w -> {
						try {
							w.setStatus(Status.UNSTARTED);
							user.removeWorkItem(w);
							workItemService.save(w);
						} catch (ServiceException e) {
							e.printStackTrace();
						}
					 });
			}
			return super.save(user);
		});
	}

}