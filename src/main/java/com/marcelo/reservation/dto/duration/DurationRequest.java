package com.marcelo.reservation.dto.duration;

import lombok.Data;

import java.time.Duration;

@Data
public class DurationRequest {
    private Duration duration;
    private Long businessId;
}
