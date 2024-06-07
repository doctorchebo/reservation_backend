package com.marcelo.reservation.dto.business;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BusinessPatchRequest {
    private Long businessId;
    private List<UUID> serviceIds;
}
