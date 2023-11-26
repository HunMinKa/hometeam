package spring.hometeam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hometeam.entity.TeamMembership;

public interface TeamMembershipRepository extends JpaRepository<TeamMembership, Integer> {
}