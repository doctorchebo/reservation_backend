package com.marcelo.reservation.service;

import com.marcelo.reservation.dto.auth.*;
import com.marcelo.reservation.dto.user.UserDto;
import com.marcelo.reservation.exception.NotFoundException;
import com.marcelo.reservation.mapper.UserMapper;
import com.marcelo.reservation.model.RefreshToken;
import com.marcelo.reservation.model.User;
import com.marcelo.reservation.repository.RefreshTokenRepository;
import com.marcelo.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.marcelo.reservation.security.JwtProvider;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final RefreshTokenRepository refreshTokenRepository;
    private static Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    private final RefreshTokenService refreshTokenService;

    public UserDto signup(SignupRequest signupRequest){
        logger.info("Creating new user");
        User user = User.builder()
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .created(Instant.now())
                .isEnabled(true)
                .build();
        return userMapper.mapToDto(userRepository.save(user));
    }
    public AuthenticationResponse login(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return AuthenticationResponse.builder()
                .email(loginRequest.getEmail())
                .authenticationToken(jwtProvider.generateToken(authentication))
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        return AuthenticationResponse.builder()
                .authenticationToken(jwtProvider.generateTokenWithUsername(refreshTokenRequest.getEmail()))
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .email(refreshTokenRequest.getEmail())
                .build();
    }

    public String logout(LogoutRequest logoutRequest) {
        logger.info("Deleting refresh token to logout");
        RefreshToken refreshToken = refreshTokenRepository.findByToken(logoutRequest.getRefreshToken())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Refresh token not found")));
        refreshTokenRepository.delete(refreshToken);
        return "Refresh Token deleted successfully";
    }
    public boolean authenticate() {
        return true;
    }
}
