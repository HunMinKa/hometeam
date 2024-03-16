package spring.hometeam.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.hometeam.dto.RegisterUserDTO;
import spring.hometeam.dto.UserEncKeyDTO;
import spring.hometeam.dto.UserInfoDTO;
import spring.hometeam.entity.User;
import spring.hometeam.exception.UseremailAlreadyExistsException;
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

        Optional<String> emailOptional = PkiUtils.extractEmailFromCSR(registerUserDTO.getCsr());
        String email = null;

        if (emailOptional.isPresent()) {
            email = emailOptional.get(); // Optional에서 값 추출
        }

        if (userEmailExists(email)) {
            throw new UseremailAlreadyExistsException("There is an account with that user email: " + email);
        }

        String pubKey = PkiUtils.compressPublicKey(PkiUtils.extractPublicKeyFromCSR(registerUserDTO.getCsr()));

        String cert =  PkiUtils.generateCertificate(registerUserDTO.getCsr(),loadPrivateKey());


        User user = new User();
        user.setName(registerUserDTO.getName());
        user.setEmail(email);
        user.setPubKey(pubKey);
        user.setEncKey(registerUserDTO.getEncKey());
        user.setPushId(registerUserDTO.getPushId());
        //user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        user.setCert(cert);

        return userRepository.save(user);
    }

    public User updateUser(User user) { return userRepository.save(user);}

    public Optional<UserInfoDTO> getUserById(int id) {
        return userRepository.findById(id).map(user -> {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId(user.getId());
            userInfoDTO.setPubKey(user.getPubKey());
            userInfoDTO.setName(user.getName());
            userInfoDTO.setEmail(user.getEmail());
            return userInfoDTO;
        });
    }

    public void deleteUserById(int userId) { userRepository.deleteById(userId); }

    public Optional<UserEncKeyDTO> getUserEncKey(String email) {
        return userRepository.findByEmail(email).map(user -> {
        UserEncKeyDTO userEncKeyDTO = new UserEncKeyDTO();
        userEncKeyDTO.setEncKey(user.getEncKey());
        return userEncKeyDTO;
        });
    }
    public Optional<UserInfoDTO> getUserInfoForToken(String pubKey) {
        return userRepository.findByPubKey(pubKey).map(user -> {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId(user.getId());
            userInfoDTO.setPubKey(user.getPubKey());
            userInfoDTO.setName(user.getName());
            userInfoDTO.setEmail(user.getEmail());
            return userInfoDTO;
        });
    }
    private boolean userEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
