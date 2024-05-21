package com.project.reservation.security.util.impl;

import com.project.reservation.auth.service.SignService;
import com.project.reservation.security.util.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements TokenProvider {

    @Value("${jwt.token.key.roles}")
    private String keyRoles;
    @Value("${jwt.token.expired_time}")
    private Long tokenExpireTime;

    // SecretKey는 JwtConfig에 Bean으로 등록
    private final SecretKey secretKey;
    private final SignService signService;


    /**
     * Jwt 생성 로직
     */
    @Override
    public String generateToken(String email, List<String> roles) {

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + tokenExpireTime);

        return Jwts.builder()
                .subject(email)
                .claim(keyRoles, roles)
                .signWith(secretKey)
                .issuedAt(now)
                .expiration(expiredDate)
                .compact();
    }

    /**
     * JWT 유효기간이 지나지 않았는지 확인
     */
    @Override
    public boolean validateToken(String token) {
        return !parseClaims(token).getExpiration().before(new Date());
    }

    /**
     * JWT 받아서 Authentication 생성
     */
    @Override
    public Authentication getAuthentication(String token) {

        UserDetails userDetails = signService.loadByEmail(getEmail(token));

        return new UsernamePasswordAuthenticationToken
                (userDetails, "", userDetails.getAuthorities());
    }


    /**
     * JWT를 Claims로 반환
     */
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey).build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    /**
     * JWT 받아서 email 반환
     */
    private String getEmail(String token) {
        return parseClaims(token).getSubject();
    }
}
