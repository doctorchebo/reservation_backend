package com.marcelo.reservation.dto.member;

import lombok.Data;

@Data
public class MemberPatchAddressRequest {
    private Long memberId;
    private Long addressId;
}
