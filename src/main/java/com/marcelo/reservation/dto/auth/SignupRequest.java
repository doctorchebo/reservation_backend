package com.marcelo.reservation.dto.auth;

import com.marcelo.reservation.validation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@PasswordMatches
public class SignupRequest {
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must contain between 3 and 50 characters")
    private String username;

    @Email(message = "Please type a valid email")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Size(min = 10, max = 100, message = "Password must contain between 10 and 50 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[*+,-./:;<=>@\\[\\]^_`])[A-Za-z0-9 *+,-./:;<=>@\\[\\]^_`]{10,}$",
            message = "Password must contain at least one special character and be at least 10 characters long.")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @Size(min = 10, max = 100, message = "Password must contain between 10 and 50 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[*+,-./:;<=>@\\[\\]^_`])[A-Za-z0-9 *+,-./:;<=>@\\[\\]^_`]{10,}$",
            message = "Password must contain at least one special character and be at least 10 characters long.")
    @NotBlank(message = "Password cannot be blank")
    private String confirmPassword;
}
