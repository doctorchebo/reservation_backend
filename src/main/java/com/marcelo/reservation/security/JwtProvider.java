package com.marcelo.reservation.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${jwt.auth.expiration.time}")
    private Long authExpTimeInMillis;

    private final JwtEncoder jwtEncoder;
    public String generateToken(Authentication authentication){
        User principal = (User) authentication.getPrincipal();
        // username is set as the email
        return generateTokenWithEmail(principal.getUsername());
    }

    public String generateTokenWithUsername(String username){
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusMillis(authExpTimeInMillis))
                .subject(username)
                .claim("scope", "ROLE_USER")
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    public String generateTokenWithEmail(String email){
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusMillis(authExpTimeInMillis))
                .subject(email)
                .claim("scope", "ROLE_USER")
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }


    public Long getJwtExpirationInMillis(){
        return authExpTimeInMillis;
    }
}
