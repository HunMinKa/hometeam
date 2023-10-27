package spring.hometeam.service.inf;

import spring.hometeam.domain.entity.Team;
import spring.hometeam.domain.entity.TeamMembership;
import spring.hometeam.domain.entity.User;

import java.util.List;

public interface ITeamService {
    Team createTeam(Team team);
    void addUserToTeam(TeamMembership teamMembership);
    List<User> getUsersInTeam(int teamId);
}
