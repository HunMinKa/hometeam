package spring.hometeam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.hometeam.domain.entity.Team;
import spring.hometeam.domain.entity.TeamMembership;
import spring.hometeam.domain.entity.User;
import spring.hometeam.repository.TeamMembershipRepository;
import spring.hometeam.repository.TeamRepository;
import spring.hometeam.repository.UserRepository;
import spring.hometeam.service.inf.ITeamService;
import spring.hometeam.service.inf.IUserService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TeamServiceImpl implements ITeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamMembershipRepository teamMembershipRepository;

    @Override
    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public void addUserToTeam(TeamMembership teamMembership) {
        teamMembershipRepository.save(teamMembership);
    }

    //J PA 조인 학습후 진행
    @Override
    public List<User> getUsersInTeam(int teamId) {
        return null;
    }
}

