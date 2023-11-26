package spring.hometeam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hometeam.entity.User;
import spring.hometeam.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(User user) {
        // 사용자 등록 로직
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        // 사용자 변경 로직
        return userRepository.save(user);
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public void deleteUserById(int userId) {
        // 사용자 삭제 로직
        userRepository.deleteById(userId);
    }
}
