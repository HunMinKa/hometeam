package spring.hometeam.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

/*
    설명
    extractUsername, extractExpiration: 토큰에서 사용자 이름과 만료 시간을 추출하는 메서드입니다.
    extractClaim: 토큰에서 특정 클레임을 추출하는 범용 메서드입니다.
    generateToken: 사용자 이름을 받아 새로운 JWT 토큰을 생성합니다. 토큰은 서명 키와 함께 발급 및 만료 시간을 포함합니다.
    validateToken: 제공된 토큰이 유효한지 검증합니다. 토큰이 만료되지 않았고, 저장된 사용자 이름이 토큰에 있는 사용자 이름과 일치하는지 확인합니다.

    주의사항
    @Value 어노테이션을 사용하여 application.properties 또는 application.yml 파일에서 jwt.secret (JWT 서명에 사용되는 비밀 키)과 jwt.expiration (토큰 만료 시간) 값을 주입받습니다. 이 값을 환경에 따라 적절하게 설정해야 합니다.
     io.jsonwebtoken 라이브러리는 pom.xml 또는 build.gradle에 의존성으로 추가되어 있어야 합니다.*/

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpirationInMillis;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMillis))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}