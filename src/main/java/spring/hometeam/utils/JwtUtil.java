package spring.hometeam.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.cfgxml.internal.ConfigLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

@Slf4j
@Component
public class JwtUtil {
    public static PrivateKey loadPrivateKey() throws Exception {
        Properties prop = new Properties();
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            prop.load(input);
            String privateKeyPEM = prop.getProperty("ecdsaPrivateKey").replace("-----BEGIN PRIVATE KEY-----", "").replaceAll(System.lineSeparator(), "").replace("-----END PRIVATE KEY-----", "");

            log.debug("privatekey: " + privateKeyPEM);

            byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            return keyFactory.generatePrivate(keySpec);
        }
    }

    public static String createJwtToken(String subject) throws Exception {
        PrivateKey privateKey = loadPrivateKey();

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + 3600000; // 1시간 후 만료
        Date exp = new Date(expMillis);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.ES256, privateKey)
                .compact();
    }

    public static Jws<Claims> verifyJwtToken(String jwtToken, PublicKey publicKey) {
        return Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(jwtToken);
    }



}