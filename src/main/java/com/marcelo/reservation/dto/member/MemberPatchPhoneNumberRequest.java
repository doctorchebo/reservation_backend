package com.marcelo.reservation.dto.member;

import lombok.Data;

@Data
public class MemberPatchPhoneNumberRequest {
    private Long memberId;
    private String phoneNumber;
}
