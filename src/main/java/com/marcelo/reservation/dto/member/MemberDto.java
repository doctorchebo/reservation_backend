package com.marcelo.reservation.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private Long businessId;
    private Long userId;
    @NotBlank(message = "First Name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    private String lastName;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    private String phoneNumber;
    @JsonProperty("isActive")
    private boolean isActive;
    private Long addressId;
    private Instant created;
    private Instant modified;
}
