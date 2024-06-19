package com.marcelo.reservation.dto.member;

import lombok.Data;

@Data
public class MemberPatchFirstNameRequest {
    private Long memberId;
    private String firstName;
}
