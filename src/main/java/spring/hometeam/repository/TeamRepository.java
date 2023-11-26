package spring.hometeam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hometeam.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}