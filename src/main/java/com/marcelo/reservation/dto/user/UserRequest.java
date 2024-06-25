package com.marcelo.reservation.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String email;
    @JsonProperty("isSuperUser")
    private boolean isSuperUser;
}
