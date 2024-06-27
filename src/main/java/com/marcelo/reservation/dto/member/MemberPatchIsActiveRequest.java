package com.marcelo.reservation.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MemberPatchIsActiveRequest {
    private Long memberId;
    @JsonProperty("isActive")
    private boolean isActive;
}
