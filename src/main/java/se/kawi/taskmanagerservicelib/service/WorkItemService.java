package se.kawi.taskmanagerservicelib.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.beans.factory.annotation.Autowired;

import se.kawi.taskmanagerservicelib.model.Issue;
import se.kawi.taskmanagerservicelib.model.WorkItem;
import se.kawi.taskmanagerservicelib.model.WorkItem.Status;
import se.kawi.taskmanagerservicelib.repository.WorkItemRepository;

@Component
public class WorkItemService extends BaseService<WorkItem, WorkItemRepository> {

	@Autowired
	public WorkItemService(WorkItemRepository workItemRepository) {
		super(workItemRepository);
	}

	public List<Issue> getWorkItemIssues(Specification<se.kawi.taskmanagerservicelib.model.Issue> spec,
			Pageable pageable) throws ServiceException {
		return issueService.query(spec, pageable);
	}

	public WorkItem addIssueToWorkItem(String issueItemKey, WorkItem workItem) throws ServiceException {
		return transaction(() -> {
			if (!workItem.getStatus().equals(Status.DONE)) {
				throw new ServiceDataFormatException("Issues can only be added to work items with status DONE.");
			} else {
				Issue issue = issueService.getByItemKey(issueItemKey);
				issue.setWorkItem(workItem);
				workItem.setStatus(Status.UNSTARTED);
				issueService.save(issue);
				return save(workItem);
			}
		});
	}

	public WorkItem removeIssueFromWorkItem(String issueItemKey, WorkItem workItem) throws ServiceException {
		return execute(() -> {
			Issue issue = issueService.getByItemKey(issueItemKey);
			issue.setWorkItem(null);
			issueService.save(issue);
			return workItem;
		});
	}
	
	@Override
	public void delete(WorkItem workItem) throws ServiceException {
		transaction(() -> {
			userService.unAssignWorkitem(workItem);
			repository.delete(workItem);
			return null;
		});
	}
}