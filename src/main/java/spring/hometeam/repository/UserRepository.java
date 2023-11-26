package spring.hometeam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hometeam.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}