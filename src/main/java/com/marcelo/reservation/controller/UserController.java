package com.marcelo.reservation.controller;

import com.marcelo.reservation.dto.user.UserDto;
import com.marcelo.reservation.dto.user.UserRequest;
import com.marcelo.reservation.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/getById/{userId}")
    public ResponseEntity<UserDto> getUserById(@Positive @PathVariable("userId") Long userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email){
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PostMapping("create")
    public ResponseEntity<UserDto> create(@Valid @RequestBody() UserRequest userRequest){
        return ResponseEntity.ok(userService.create(userRequest));
    }

    @DeleteMapping("/deleteById/{userId}")
    public ResponseEntity<UserDto> deleteById(@Positive @PathVariable("userId") Long userId){
        return ResponseEntity.ok(userService.deleteById(userId));
    }

    @GetMapping("/getCurrentUser")
    public ResponseEntity<UserDto> getCurrentUser(){
        return ResponseEntity.ok(userService.getCurrentUser());
    }
}
