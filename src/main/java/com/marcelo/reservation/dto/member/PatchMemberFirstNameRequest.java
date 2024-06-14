package com.marcelo.reservation.dto.member;

import lombok.Data;

@Data
public class PatchMemberFirstNameRequest {
    private Long memberId;
    private String firstName;
}
