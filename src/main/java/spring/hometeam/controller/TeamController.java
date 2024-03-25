package spring.hometeam.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.hometeam.dto.TeamDTO;
import spring.hometeam.entity.Team;
import spring.hometeam.entity.TeamMembership;
import spring.hometeam.service.TeamMembershipService;
import spring.hometeam.service.TeamService;
import spring.hometeam.utils.Common;
import spring.hometeam.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/teams")
public class TeamController {
    private final TeamService teamService;
    private final TeamMembershipService teamMembershipService;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public TeamController(TeamService teamService, TeamMembershipService teamMembershipService) {
        this.teamService = teamService;
        this.teamMembershipService = teamMembershipService;
    }

    // 팀 생성 API 예제
    @PostMapping("/")
    public Team createTeam(HttpServletRequest request, @RequestBody TeamDTO team) {

        String token = jwtUtil.extractTokenFromRequest(request);
        log.info("팀생성" + token);
        return teamService.createTeam(team);
    }

    // 모든 팀 조회 API 예제
    @GetMapping("/")
    public List<Team> getAllTeams(HttpServletRequest request) {
        String token = jwtUtil.extractTokenFromRequest(request);
        log.info("팀조회" + token);
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