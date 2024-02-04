package spring.hometeam.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.hometeam.dto.RegisterUserDTO;
import spring.hometeam.entity.User;
import spring.hometeam.exception.UsernameAlreadyExistsException;
import spring.hometeam.repository.UserRepository;
import spring.hometeam.utils.PkiUtils;

import java.util.Optional;

import static spring.hometeam.utils.JwtUtil.loadPrivateKey;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(RegisterUserDTO registerUserDTO) throws Exception {

        String email = String.valueOf(PkiUtils.extractEmailFromCSR(registerUserDTO.getCsr()));

        if (userEmailExists(email)) {
            throw new UsernameAlreadyExistsException("There is an account with that user email: " + email);
        }

        String pubKey = PkiUtils.compressPublicKey(PkiUtils.extractPublicKeyFromCSR(registerUserDTO.getCsr()));

        String cert =  PkiUtils.generateCertificate(registerUserDTO.getCsr(),loadPrivateKey());


        User user = new User();
        user.setName(registerUserDTO.getName());
        user.setEmail(email);
        user.setPubKey(pubKey);
        user.setEncKey(registerUserDTO.getEncKey());
        //user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        user.setCert(cert);

        return userRepository.save(user);
    }

    public User updateUser(User user) { return userRepository.save(user);}

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public void deleteUserById(int userId) { userRepository.deleteById(userId); }
    private boolean userEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
