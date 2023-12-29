package spring.hometeam.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spring.hometeam.dto.RegisterUserDTO;
import spring.hometeam.provider.JwtTokenProvider;
import spring.hometeam.dto.AuthenticationResponse;
import spring.hometeam.dto.LoginDto;
import spring.hometeam.service.EmailService;
import spring.hometeam.service.VerificationService;
import spring.hometeam.utils.JwtUtil;
import spring.hometeam.entity.User;
import spring.hometeam.service.UserService;

import javax.mail.MessagingException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
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



    @PostMapping("/register")
    public User registerUser(@RequestBody RegisterUserDTO registerUserDTO) throws Exception {
        return userService.registerUser(registerUserDTO);
    }

    @PutMapping("/")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable int id) { return userService.getUserById(id); }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable int userId) { userService.deleteUserById(userId);}

    @PostMapping("/request-code")
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

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");

        boolean isVerified = verificationService.verifyCode(email, code);

        if (isVerified) {
            return ResponseEntity.ok("Code verified successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid verification code");
        }
    }


    @PostMapping("web/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String jwt = jwtTokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }
    @PutMapping("/challenge")
    public ResponseEntity<?> verifyChallenge(@RequestBody LoginDto loginDto) throws Exception {
        boolean isVerified = verificationService.verifyChallenge(loginDto.getSignature(), loginDto.getPubKey());

        if (isVerified) {
            String jwt = JwtUtil.createJwtToken(String.valueOf(loginDto));
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Challenge verification failed");
        }
    }

    @PostMapping("/challenge")
    public ResponseEntity<?> generateChallengeCode(@RequestBody Map<String, String> request) {
        String pubKey = request.get("pubKey");
        String code = verificationService.generateChallengeCode(pubKey);
        return ResponseEntity.ok("Verification code sent to: " + code);
    }


}
