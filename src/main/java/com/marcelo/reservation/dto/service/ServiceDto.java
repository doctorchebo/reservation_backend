package com.marcelo.reservation.dto.service;

import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class ServiceDto {
    private UUID id;
    private String name;
    private List<Long> categoryIds;
    private List<Long> durationIds;
    private List<Long> businessIds;
    private List<Long> addressIds;
    private List<Long> priceIds;
    private Instant created;
    private Instant modified;
}
