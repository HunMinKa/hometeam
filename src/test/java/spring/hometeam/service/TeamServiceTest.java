package spring.hometeam.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.hometeam.entity.Team;
import spring.hometeam.repository.TeamRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTeam() {
        Team team = new Team(); // Populate with necessary data
        when(teamRepository.save(team)).thenReturn(team);

        Team created = teamService.createTeam(team);

        assertEquals(team, created);
        verify(teamRepository).save(team);
    }

    @Test
    public void testGetAllTeams() {
        List<Team> teams = Arrays.asList(new Team(), new Team()); // Populate with necessary data
        when(teamRepository.findAll()).thenReturn(teams);

        List<Team> foundTeams = teamService.getAllTeams();

        assertEquals(teams.size(), foundTeams.size());
        verify(teamRepository).findAll();
    }

    @Test
    public void testGetTeamById() {
        int teamId = 1;
        Team team = new Team(); // Populate with necessary data
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        Optional<Team> foundTeam = teamService.getTeamById(teamId);

        assertTrue(foundTeam.isPresent());
        assertEquals(team, foundTeam.get());
        verify(teamRepository).findById(teamId);
    }
}