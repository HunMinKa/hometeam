package spring.hometeam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hometeam.entity.Task;
import spring.hometeam.entity.TeamMembership;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}