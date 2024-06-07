package com.marcelo.reservation.dto.auth;

import lombok.Data;

@Data
public class LogoutRequest {
    private String refreshToken;
}
