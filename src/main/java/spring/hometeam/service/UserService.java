package spring.hometeam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.hometeam.entity.User;
import spring.hometeam.exception.UsernameAlreadyExistsException;
import spring.hometeam.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {

        if (usernameExists(user.getEmail())) {
            throw new UsernameAlreadyExistsException("There is an account with that username: " + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
    private boolean usernameExists(String username) {
        return userRepository.findByName(username).isPresent();
    }
}
