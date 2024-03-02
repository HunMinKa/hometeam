package spring.hometeam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hometeam.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User>  findByPubKey(String pubKey);
}