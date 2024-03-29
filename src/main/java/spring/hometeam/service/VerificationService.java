package spring.hometeam.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.bouncycastle.asn1.sec.SECNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.jce.ECPointUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import spring.hometeam.entity.User;
import spring.hometeam.repository.UserRepository;
import spring.hometeam.utils.ArrayUtil;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPublicKeySpec;
import java.util.*;
import java.util.concurrent.TimeUnit;


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


    public boolean verifyEmail(String email, String code) {
        String storedCode = redisTemplate.opsForValue().get(email);
        boolean codeVerify = code.equals(storedCode);
        redisTemplate.opsForValue().set(code, codeVerify ? "1" : "0", 10, TimeUnit.MINUTES);
        return codeVerify;
    }
    public void verifyCode(String code) {
        String status = redisTemplate.opsForValue().get(code);
        if(Objects.equals(status, "0")){
            throw new NoSuchElementException("Authentication code not found.");
        }
    }

    public boolean verifyChallenge(String signature, String pubKey) throws Exception {
        Optional<User> user = userRepository.findByPubKey(pubKey);
        String storedCode = redisTemplate.opsForValue().get(pubKey);
        try {
            
            Optional<ECPublicKey> ecPublicKey;
            Signature signatureVerifier;
            
            Security.addProvider (new BouncyCastleProvider());
            ecPublicKey = getPublicKeyFromByteArray (ArrayUtil.toByte ( uncompressPublicKey (pubKey)));
            signatureVerifier = Signature.getInstance ("SHA256withECDSA", "BC");
            signatureVerifier.initVerify (ecPublicKey.orElseThrow ());
            assert storedCode != null;
            signatureVerifier.update (storedCode.getBytes (StandardCharsets.UTF_8));
            return signatureVerifier.verify (ArrayUtil.toByte (signature));

            // 서명 인스턴스 초기화
            /*Signature sig = Signature.getInstance("SHA256withECDSA");
            sig.initVerify(loadPubKeyFromPem(user.getCert()));
            assert storedCode != null;
            log.info("storedCode: " + storedCode);
            sig.update(storedCode.getBytes(StandardCharsets.UTF_8));
            // 서명 검증
            byte[] signatureBytes = Base64.getDecoder().decode(signature);
            log.info("signatureBytes: " + Arrays.toString(signatureBytes));
            return sig.verify(signatureBytes);
            */
        } catch (Exception e) {
            return false;
        }
    }

    public static String uncompressPublicKey(String compressedHexPublicKey) {
        X9ECParameters x9ECParameters;
        ECPoint ecPoint;
        byte[] uncompressedPublicKey;

        x9ECParameters = SECNamedCurves.getByName("secp256k1");
        ecPoint = x9ECParameters.getCurve().decodePoint(
                ArrayUtil.toByte(compressedHexPublicKey)
        );
        uncompressedPublicKey = ecPoint.getEncoded(false);
        return ArrayUtil.toHex(uncompressedPublicKey);
    }
    public static Optional<ECPublicKey> getPublicKeyFromByteArray(byte[] uncompressedPublicKey) {
        ECParameterSpec parameterSpec;
        java.security.spec.ECPoint ecPoint;
        ECPublicKeySpec ecPublicKeySpec;
        Optional<ECPublicKey> ecPublicKey;

        try {
            parameterSpec = getECsParameterSpec();
            ecPoint = ECPointUtil.decodePoint(parameterSpec.getCurve(), uncompressedPublicKey);
            ecPublicKeySpec = new ECPublicKeySpec(ecPoint, parameterSpec);
            ecPublicKey = Optional.ofNullable(
                    (ECPublicKey) KeyFactory.getInstance("EC")
                            .generatePublic(ecPublicKeySpec)
            );
        } catch (Throwable exception) {
            log.error("Can't getPublicKeyFromByteArray !", exception);
            throw new RuntimeException(exception);
        }
        return ecPublicKey;
    }
    private static ECParameterSpec getECsParameterSpec() {
        AlgorithmParameters parameters;

        try {
            parameters = AlgorithmParameters.getInstance("EC", "BC");
            parameters.init(new ECGenParameterSpec("secp256k1"));
            return parameters.getParameterSpec(ECParameterSpec.class);
        } catch (Throwable exception) {
            log.error("Can't get ECParameterSpec !", exception);
            throw new RuntimeException(exception);
        }
    }
}