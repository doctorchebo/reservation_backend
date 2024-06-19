package com.marcelo.reservation.dto.member;

import lombok.Data;

@Data
public class MemberPatchTitleRequest {
    private Long memberId;
    private String title;
}
