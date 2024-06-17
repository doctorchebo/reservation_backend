package com.marcelo.reservation.dto.service;

import com.marcelo.reservation.dto.duration.DurationDto;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class ServiceResponse {
    private UUID id;
    private String name;
    private List<Long> categoryIds;
    private List<DurationDto> durations;
    private List<Long> businessIds;
    private Instant created;
    private Instant modified;
}
