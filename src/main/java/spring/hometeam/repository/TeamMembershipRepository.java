package spring.hometeam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.hometeam.domain.entity.TeamMembership;

@Repository
public interface TeamMembershipRepository extends JpaRepository<TeamMembership, Long> {
    void findById(int id);
}