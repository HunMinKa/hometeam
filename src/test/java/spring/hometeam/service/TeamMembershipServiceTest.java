package spring.hometeam.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import spring.hometeam.entity.TeamMembership;
import spring.hometeam.repository.TeamMembershipRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TeamMembershipServiceTest {

    @Mock
    private TeamMembershipRepository teamMembershipRepository;

    @InjectMocks
    private TeamMembershipService teamMembershipService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTeamMembership() {
        TeamMembership teamMembership = new TeamMembership(); // Assume TeamMembership has proper fields and methods
        when(teamMembershipRepository.save(teamMembership)).thenReturn(teamMembership);

        TeamMembership created = teamMembershipService.createTeamMembership(teamMembership);

        assertEquals(teamMembership, created);
        verify(teamMembershipRepository).save(teamMembership);
    }

    @Test
    public void testGetAllTeamMemberships() {
        List<TeamMembership> teamMemberships = Arrays.asList(new TeamMembership(), new TeamMembership());
        when(teamMembershipRepository.findAll()).thenReturn(teamMemberships);

        List<TeamMembership> found = teamMembershipService.getAllTeamMemberships();

        assertEquals(2, found.size());
        verify(teamMembershipRepository).findAll();
    }
}