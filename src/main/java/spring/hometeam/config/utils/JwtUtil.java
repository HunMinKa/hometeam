package spring.hometeam.config.utils;

import io.jsonwebtoken.*;

public class JwtUtil {
    private String secretKey = "hometeam!";

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}