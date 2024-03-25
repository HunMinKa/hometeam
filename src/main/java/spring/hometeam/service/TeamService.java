package spring.hometeam.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.hometeam.dto.TeamDTO;
import spring.hometeam.dto.TeamMemberDTO;
import spring.hometeam.dto.UserInfoDTO;
import spring.hometeam.entity.Team;
import spring.hometeam.entity.User;
import spring.hometeam.repository.TeamRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    public Team createTeam(TeamDTO teamDTO) {

        Team team = new Team();
        team.setTeamId(teamDTO.getTeamId());
        team.setTeamName(teamDTO.getTeamName());
        team.setCreateDate(new Date());
        team.setUpdateDate(new Date());
        return teamRepository.save(team);
    }


    public Optional<TeamMemberDTO> inviteTeamMember(TeamMemberDTO teamMemberDTO) {
        TeamMemberDTO teamMember = new TeamMemberDTO();
        return Optional.of(teamMember);
    }


    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Optional<Team> getTeamById(int teamId) {
        return teamRepository.findById(teamId);
    }
}