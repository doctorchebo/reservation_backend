package com.marcelo.reservation.dto.business;

import lombok.Data;

@Data
public class BusinessPatchNameRequest {
    private Long businessId;
    private String name;
}
