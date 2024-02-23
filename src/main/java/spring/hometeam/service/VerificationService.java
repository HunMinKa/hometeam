package spring.hometeam.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import spring.hometeam.entity.User;
import spring.hometeam.repository.UserRepository;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static spring.hometeam.utils.PkiUtils.hashMessage;
import static spring.hometeam.utils.PkiUtils.loadCertificateFromPem;

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
        log.info("pubKey: " + pubKey);
        User user = userRepository.findByPubKey(pubKey);
        log.info("user: " + user.getCert());
        String storedCode = redisTemplate.opsForValue().get(pubKey);
        log.info("storedCode: " + storedCode);
        log.info("signature: " + signature);
        try {
            // 서명 인스턴스 초기화
            log.info("1");
            Signature sig = Signature.getInstance("SHA256withECDSA");
            log.info("2");
            sig.initVerify(loadCertificateFromPem(user.getCert()));
            log.info("3");
            assert storedCode != null;
            sig.update(hashMessage(storedCode));
            log.info("storedCode: " + Arrays.toString(hashMessage(signature)));
            // 서명 검증
            byte[] signatureBytes = Base64.getDecoder().decode(signature);
            return sig.verify(signatureBytes);

        } catch (Exception e) {
            log.info("error: " + e.getMessage().toString());
            return false;
        }
    }
}