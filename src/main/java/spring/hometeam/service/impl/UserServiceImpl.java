package spring.hometeam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import spring.hometeam.domain.dto.UserDTO;
import spring.hometeam.domain.entity.User;
import spring.hometeam.repository.UserRepository;
import spring.hometeam.service.inf.IUserService;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    
    // 학습후 구현할 예쩡
    @Override
    public User updateUserDetails(int userId, String email, String name, String phone) {
        return null;
    }
}
