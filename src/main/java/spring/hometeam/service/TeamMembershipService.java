package spring.hometeam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.hometeam.entity.TeamMembership;
import spring.hometeam.repository.TeamMembershipRepository;

import java.util.List;

@Service
public class TeamMembershipService {
    private final TeamMembershipRepository teamMembershipRepository;

    @Autowired
    public TeamMembershipService(TeamMembershipRepository teamMembershipRepository) {
        this.teamMembershipRepository = teamMembershipRepository;
    }

    public TeamMembership createTeamMembership(TeamMembership teamMembership) {
        return teamMembershipRepository.save(teamMembership);
    }

    public List<TeamMembership> getAllTeamMemberships() {
        return teamMembershipRepository.findAll();
    }
}