package com.marcelo.reservation.dto.auth;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Email(message = "Please type a valid email")
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[*+,-./:;<=>@\\[\\]^_`])[A-Za-z0-9 *+,-./:;<=>@\\[\\]^_`]{10,}$",
            message = "Password must contain at least one special character and be at least 10 characters long.")
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
