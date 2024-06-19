package com.marcelo.reservation.dto.service;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ServicePatchPriceRequest {
    private Long businessId;
    private UUID serviceId;
    private BigDecimal price;
}
