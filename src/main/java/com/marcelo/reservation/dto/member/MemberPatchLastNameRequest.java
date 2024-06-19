package com.marcelo.reservation.dto.member;

import lombok.Data;

@Data
public class MemberPatchLastNameRequest {
    private Long memberId;
    private String lastName;
}
