package com.marcelo.reservation.dto.business;

import lombok.Data;

import java.util.List;

@Data
public class BusinessPatchMembersRequest {
    private Long businessId;
    private List<Long> memberIds;
}
