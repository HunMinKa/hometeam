package spring.hometeam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.hometeam.domain.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> { }