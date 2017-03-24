package se.kawi.taskmanagerservicelib.service;

import org.springframework.stereotype.Component;

import se.kawi.taskmanagerservicelib.model.Team;
import se.kawi.taskmanagerservicelib.model.User;
import se.kawi.taskmanagerservicelib.model.WorkItem;
import se.kawi.taskmanagerservicelib.repository.TeamRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Component
public class TeamService extends BaseService<Team, TeamRepository> {

	private static final int TEAM_MAX_SIZE = 10;
	private static final int USER_MAX_TEAMS = 3;

	@Autowired
	public TeamService(TeamRepository teamRepository) {
		super(teamRepository);
	}

	public List<User> getTeamMembers(Specification<User> spec, Pageable pageable) throws ServiceException {
		return userService.query(spec, pageable);
	}

	public Team addTeamMember(String userItemKey, Team team) throws ServiceException {
		return execute(() -> {
			if (team.getUsers().size() >= TEAM_MAX_SIZE) {
				throw new ServiceDataException("Team is full. Max number of members in each team is: " + TEAM_MAX_SIZE);
			}
			User user = userService.getByItemKey(userItemKey);
			if (user.getTeams().size() >= USER_MAX_TEAMS) {
				throw new ServiceDataException(
						"User has to many teams. Max number of teams per user is: " + USER_MAX_TEAMS);
			}
			team.addUser(user);
			return super.save(team);
		});
	}

	public Team removeTeamMember(String userItemKey, Team team) throws ServiceException {
		return execute(() -> {
			User user = userService.getByItemKey(userItemKey);
			team.removeUser(user);
			return super.save(team);
		});
	}

	public List<WorkItem> getTeamWorkItems(Specification<WorkItem> spec, Pageable pageable) throws ServiceException {
		return workItemService.query(spec, pageable);
	}

}