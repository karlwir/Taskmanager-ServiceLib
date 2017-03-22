package se.kawi.taskmanagerservicelib.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.kawi.taskmanagerservicelib.model.Issue;
import se.kawi.taskmanagerservicelib.repository.IssueRepository;


@Component
public class IssueService extends BaseService<Issue, IssueRepository> {

	@Autowired
	public IssueService(IssueRepository issueRepository) {
		super(issueRepository);
	}

}