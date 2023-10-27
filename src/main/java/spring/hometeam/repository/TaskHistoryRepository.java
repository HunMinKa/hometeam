package spring.hometeam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.hometeam.domain.entity.User;

@Repository
public interface TaskHistoryRepository extends JpaRepository<User, Long> { }