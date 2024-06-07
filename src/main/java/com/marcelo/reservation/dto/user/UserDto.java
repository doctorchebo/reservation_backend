package com.marcelo.reservation.dto.user;
import lombok.Data;

import java.time.Instant;
@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private boolean isEnabled;
    private Instant created;
}
