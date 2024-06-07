package com.marcelo.reservation.dto.duration;

import lombok.Data;

import java.time.Duration;
import java.util.UUID;

@Data
public class DurationRequest {
    private Duration duration;
    private UUID serviceId;
    private Long businessId;
}
