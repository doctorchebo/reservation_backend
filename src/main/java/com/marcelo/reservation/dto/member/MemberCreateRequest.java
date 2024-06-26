package com.marcelo.reservation.dto.member;
import lombok.Data;

@Data
public class MemberCreateRequest {
    private Long businessId;
    private Long userId;
    private String firstName;
    private String lastName;
    private String title;
    private String phoneNumber;
    private boolean isActive;
    private Long addressId;
}
