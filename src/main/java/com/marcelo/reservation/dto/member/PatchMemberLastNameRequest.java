package com.marcelo.reservation.dto.member;

import lombok.Data;

@Data
public class PatchMemberLastNameRequest {
    private Long memberId;
    private String lastName;
}
