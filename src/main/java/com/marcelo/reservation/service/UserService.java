package com.marcelo.reservation.service;

import com.marcelo.reservation.dto.user.UserDto;
import com.marcelo.reservation.dto.user.UserRequest;
import com.marcelo.reservation.exception.NotFoundException;
import com.marcelo.reservation.mapper.UserMapper;
import com.marcelo.reservation.model.User;
import com.marcelo.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAllUsers(){
        return userMapper.mapToDtoList(userRepository.findAll());
    }

    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %s not found", userId)));
        return userMapper.mapToDto(user);
    }

    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("User with email %s not found", email)));
        return userMapper.mapToDto(user);
    }

    public UserDto deleteById(Long userId) {
        logger.info("Deleting user with id {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %s not found", userId)));
        userRepository.delete(user);
        return userMapper.mapToDto(user);
    }

    public UserDto create(UserRequest userRequest) {
        User user = User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .email(userRequest.getEmail())
                .isEnabled(true)
                .created(Instant.now())
                .build();

        return userMapper.mapToDto(user);
    }

    @Transactional(readOnly = true)
    public UserDto getCurrentUserDto(){
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(principal.getSubject())
                .orElseThrow(() -> new NotFoundException(String.format("User with email %s not found", principal.getClaim("username"))));
        return userMapper.mapToDto(user);
    }

    @Transactional(readOnly = true)
    public User getCurrentUser(){
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(principal.getSubject())
                .orElseThrow(() -> new NotFoundException(String.format("User with email %s not found", principal.getClaim("username"))));
        return user;
    }
}
