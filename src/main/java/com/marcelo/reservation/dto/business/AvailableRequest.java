package com.marcelo.reservation.dto.business;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class AvailableRequest {
    private UUID serviceId;
    private Instant startDate;
}
