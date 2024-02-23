package spring.hometeam.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import spring.hometeam.entity.User;
import spring.hometeam.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static spring.hometeam.utils.PkiUtils.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class VerificationService {

    private final UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String generateVerificationCode(String email) {
        String code = RandomStringUtils.randomNumeric(6);
        redisTemplate.opsForValue().set(email, code, 10, TimeUnit.MINUTES); // 10분 후 만료
        return code;
    }

    public String generateChallengeCode(String pubKey) {
        String code = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(pubKey, code, 10, TimeUnit.MINUTES); // 10분 후 만료
        return code;
    }


    public boolean verifyCode(String email, String code) {
        String storedCode = redisTemplate.opsForValue().get(email);
        return code.equals(storedCode);
    }

    public boolean verifyChallenge(String signature, String pubKey) throws Exception {
        User user = userRepository.findByPubKey(pubKey);
        String storedCode = redisTemplate.opsForValue().get(pubKey);
        try {
            // 서명 인스턴스 초기화
            Signature sig = Signature.getInstance("SHA256withECDSA");
            sig.initVerify(loadPubKeyFromPem(user.getCert()));
            assert storedCode != null;
            log.info("storedCode: " + storedCode);
            sig.update(storedCode.getBytes(StandardCharsets.UTF_8));
            // 서명 검증
            byte[] signatureBytes = Base64.getDecoder().decode(signature);
            log.info("signatureBytes: " + Arrays.toString(signatureBytes));
            return sig.verify(signatureBytes);

        } catch (Exception e) {
            return false;
        }
    }
}