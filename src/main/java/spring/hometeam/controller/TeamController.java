package spring.hometeam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.hometeam.entity.Team;
import spring.hometeam.entity.TeamMembership;
import spring.hometeam.service.TeamMembershipService;
import spring.hometeam.service.TeamService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teams")
public class TeamController {
    private final TeamService teamService;
    private final TeamMembershipService teamMembershipService;

    @Autowired
    public TeamController(TeamService teamService, TeamMembershipService teamMembershipService) {
        this.teamService = teamService;
        this.teamMembershipService = teamMembershipService;
    }

    // 팀 생성 API 예제
    @PostMapping
    public Team createTeam(@RequestBody Team team) {
        return teamService.createTeam(team);
    }

    // 모든 팀 조회 API 예제
    @GetMapping
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    // 특정 팀 조회 API 예제
    @GetMapping("/{teamId}")
    public Optional<Team> getTeamById(@PathVariable int teamId) {
        return teamService.getTeamById(teamId);
    }

    // 팀 멤버십 생성 API 예제
    @PostMapping("/memberships")
    public TeamMembership createTeamMembership(@RequestBody TeamMembership teamMembership) {
        return teamMembershipService.createTeamMembership(teamMembership);
    }

    // 모든 팀 멤버십 조회 API 예제
    @GetMapping("/memberships")
    public List<TeamMembership> getAllTeamMemberships() {
        return teamMembershipService.getAllTeamMemberships();
    }
}