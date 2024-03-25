package spring.hometeam.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spring.hometeam.dto.*;
import spring.hometeam.provider.JwtTokenProvider;
import spring.hometeam.service.EmailService;
import spring.hometeam.service.VerificationService;
import spring.hometeam.utils.JwtUtil;
import spring.hometeam.entity.User;
import spring.hometeam.service.UserService;

import javax.mail.MessagingException;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private VerificationService verificationService;
    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;



    @PostMapping("/")
    public User registerUser(@RequestBody RegisterUserDTO registerUserDTO) throws Exception {

        verificationService.verifyCode(registerUserDTO.getAuthCode());
        return userService.registerUser(registerUserDTO);
    }

    @PutMapping("/")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping("/search/{email}")
    public Optional<UserInfoDTO> getUserByEmail(@PathVariable("email") String email) { return userService.getUserByEmail(email); }

    @GetMapping("/{id}")
    public Optional<UserInfoDTO> getUserById(@PathVariable("id") int id) { return userService.getUserById(id); }

    @GetMapping("restore/{email}")
    public Optional<UserEncKeyDTO> getUserEncKeyByEmail(@PathVariable("email") String email) { return userService.getUserEncKey(email); }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") int id) { userService.deleteUserById(id);}

    @PostMapping("/auth-code")
    public ResponseEntity<?> requestCode(@RequestBody Map<String, String> request) {

        String email = request.get("email");
        String code = verificationService.generateVerificationCode(email);

        try {
            emailService.sendVerificationCode(email, code);
            return ResponseEntity.ok("Verification code sent to: " + email);
        } catch (MessagingException e) {
            return ResponseEntity.internalServerError().body("Failed to send email");
        }
    }

    @PutMapping("/auth-code")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");

        boolean isVerified = verificationService.verifyEmail(email, code);

        if (isVerified) {
            return ResponseEntity.ok("Code verified successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid verification code");
        }
    }


    @PostMapping("web/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginWebDTO loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String jwt = jwtTokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }
    @PutMapping("/challenge")
    public ResponseEntity<?> verifyChallenge(@RequestBody LoginMobDTO loginDto) throws Exception {

        boolean isVerified = verificationService.verifyChallenge(loginDto.getSignature(), loginDto.getPubKey());

        if (isVerified) {
            log.info("pubKey: " + loginDto.getPubKey());
            Optional<UserInfoDTO> userInfoDTOOptional = userService.getUserInfoForToken(loginDto.getPubKey());

            if (userInfoDTOOptional.isPresent()) {
                UserInfoDTO userInfoDTO = userInfoDTOOptional.get();
                log.info("userInfoDTO: " + userInfoDTO.getEmail());
                String jwt = JwtUtil.createJwtToken(userInfoDTO);
                return ResponseEntity.ok(new AuthenticationResponse(jwt));
            } else {
                throw new IllegalArgumentException("No user found with the provided public key");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Challenge verification failed");
        }
    }

    @PostMapping("/challenge")
    public ResponseEntity<?> generateChallengeCode(@RequestBody Map<String, String> request) {
        String pubKey = request.get("pubKey");
        String code = verificationService.generateChallengeCode(pubKey);
        return ResponseEntity.ok(code);
    }


}
