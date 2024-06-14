package com.marcelo.reservation.dto.duration;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class DurationDto {
    private Long id;
    private java.time.Duration duration;
    private UUID serviceId;
    private Long businessId;
    private Instant created;
    private Instant modified;
}