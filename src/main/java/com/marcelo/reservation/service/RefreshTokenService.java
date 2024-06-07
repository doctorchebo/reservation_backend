package com.marcelo.reservation.service;

import com.marcelo.reservation.exception.NotFoundException;
import com.marcelo.reservation.model.RefreshToken;
import com.marcelo.reservation.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken(){
        String token = UUID.randomUUID().toString();
        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .created(Instant.now())
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshToken(String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(()-> new NotFoundException("Refresh token not found"));
    }
}
