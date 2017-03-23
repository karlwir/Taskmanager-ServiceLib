package se.kawi.taskmanagerservicelib.service;

import java.util.List;

import javax.ws.rs.WebApplicationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

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

	public List<Issue> getWorkItemIssues(Specification<se.kawi.taskmanagerservicelib.model.Issue> spec, Pageable pageable) throws ServiceException {
		return issueService.query(spec, pageable);
	}

	public WorkItem addIssueToWorkItem(String issueItemKey, WorkItem workItem) throws ServiceException {
		if (!workItem.getStatus().equals(Status.DONE)) {
			throw new ServiceException("Work item needs to be done", new WebApplicationException("Work item needs to be done", 400));
		}
		else {
			return transaction(() -> {
				Issue issue = issueService.getByItemKey(issueItemKey);
				issue.setWorkItem(workItem);
				workItem.setStatus(Status.UNSTARTED);
				issueService.save(issue);
				return save(workItem);
			});
		}		
	}
	
	public WorkItem removeIssueFromWorkItem(String issueItemKey, WorkItem workItem) throws ServiceException {
		Issue issue = issueService.getByItemKey(issueItemKey);
		issue.setWorkItem(null);
		issueService.save(issue);
		return workItem;
	}
}