package se.kawi.taskmanagerservicelib.service;

import org.springframework.stereotype.Component;

import se.kawi.taskmanagerservicelib.model.User;
import se.kawi.taskmanagerservicelib.model.WorkItem;
import se.kawi.taskmanagerservicelib.model.WorkItem.Status;
import se.kawi.taskmanagerservicelib.repository.UserRepository;

import java.util.Set;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class UserService extends BaseService<User, UserRepository> {

	private static final int USERNAME_MIN_LENGTH = 6;
	private static final int USER_MAX_WORKITEMS = 5;
	private static final int WORKITEM_MAX_USERS = 5;

	@Autowired
	public UserService(UserRepository userRepository) {
		super(userRepository);
	}

	public List<WorkItem> getUserWorkItems(Specification<WorkItem> spec, Pageable pageable) throws ServiceException {
		return workItemService.query(spec, pageable);
	}

	public WorkItem assignNewWorkItem(WorkItem newWorkItem, User user) throws ServiceException {
		return transaction(() -> {
			canBeAssignedWorkItems(user);
			WorkItem savedWorkItem = workItemService.save(newWorkItem);
			user.addWorkItem(savedWorkItem);
			save(user);
			return savedWorkItem;
		});
	}

	public User assignWorkItem(String workItemItemKey, User user) throws ServiceException {
		return execute(() -> {
			canBeAssignedWorkItems(user);
			WorkItem workItem = workItemService.getByItemKey(workItemItemKey);
			canBeAssignedUsers(workItem);
			user.addWorkItem(workItem);
			return save(user);
		});
	}

	public User withdrawWorkItem(String workItemItemKey, User user) throws ServiceException {
		return execute(() -> {
			WorkItem workItem = workItemService.getByItemKey(workItemItemKey);
			user.removeWorkItem(workItem);
			return save(user);
		});
	}

	@Override
	public User save(User user) throws ServiceException {
		return transaction(() -> {
			isValidUsername(user.getUsername());
			if (!user.isActiveUser() && user.getId() != null) {
				Set<WorkItem> workItems = getByItemKey(user.getItemKey()).getWorkItems();
				for(WorkItem w : workItems) {
					user.removeWorkItem(w);
					if (!w.getStatus().equals(Status.ARCHIVED)) {
						w.setStatus(Status.UNSTARTED);
						workItemService.save(w);
					}
				}
			}
			return super.save(user);
		});
	}
	
	public WorkItem unAssignWorkitem(WorkItem workItem) throws ServiceException {
		return transaction(() -> {
			Set<User> users = workItem.getUsers();
			for(User user : users) {
				user.removeWorkItem(workItem);
			}
			repository.save(users);
			return workItem;
		});
	}

	private boolean isValidUsername(String username) {
		if (username.length() < USERNAME_MIN_LENGTH) {
			throw new ServiceDataFormatException("Invalid username");
		} else {
			return true;
		}
	}


	private boolean canBeAssignedUsers(WorkItem workItem) {
		if (workItem.getUsers().size() >= WORKITEM_MAX_USERS) {
			throw new ServiceDataFormatException("WorkItem has max number of users assigned");
		} else {
			return true;
		}
	}
	
	private boolean canBeAssignedWorkItems(User user) {
		if (!user.isActiveUser()) {
			throw new ServiceDataFormatException("Cant assign to inactive user");
		} else if (user.getWorkItems().size() >= USER_MAX_WORKITEMS) {
			throw new ServiceDataFormatException("User cant be assigned to more items");
		} else {
			return true;
		}
	}
}