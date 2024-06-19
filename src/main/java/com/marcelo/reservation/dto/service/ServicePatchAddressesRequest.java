package com.marcelo.reservation.dto.service;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ServicePatchAddressesRequest {
    private UUID serviceId;
    private List<Long> addressIds;
}
