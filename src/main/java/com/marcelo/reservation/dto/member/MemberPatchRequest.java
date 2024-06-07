package com.marcelo.reservation.dto.member;

import lombok.Data;

import java.util.List;

@Data
public class MemberPatchRequest {
    private Long businessId;
    private List<Long> memberIds;
}
