package spring.hometeam.provider;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtTokenProvider {

    private String secretKey = "your-secret-key"; // 비밀키를 설정합니다. 실제 환경에서는 보안을 위해 복잡하고 안전하게 관리해야 합니다.

    // JWT 토큰 생성 메소드
    public String createToken(Authentication authentication) {
        // 인증된 사용자의 정보를 가져옵니다.
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        Claims claims = Jwts.claims().setSubject(principal.getUsername());
        claims.put("auth", principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        Date now = new Date();
        // 토큰 유효시간 (예: 1시간)
        long validityInMilliseconds = 3600000;
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey) // 여기서 사용되는 알고리즘과 비밀키는 프로젝트 요구사항에 따라 달라질 수 있습니다.
                .compact();
    }


    // JWT 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtException("Expired or invalid JWT token");
        }
    }

    // 토큰에서 사용자 정보 추출
    public String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }}
