package com.marcelo.reservation.dto.user;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    @JsonProperty("isEnabled")
    private boolean isEnabled;
    @JsonProperty("isSuperUser")
    private boolean isSuperUser;
    private Instant created;
}
