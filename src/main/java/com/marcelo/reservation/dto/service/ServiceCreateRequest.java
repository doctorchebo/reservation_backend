package com.marcelo.reservation.dto.service;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ServiceCreateRequest {
    private Long businessId;
    private String name;
    private BigDecimal price;
    private List<Long> durationIds;
    private List<Long> addressIds;
}
