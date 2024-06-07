package com.marcelo.reservation.dto.reservation;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class ReservationRequest {
    private String name;
    private Instant startTime;
    private Long userId;
    private Long businessId;
    private UUID serviceId;
    private Long memberId;
    private Instant created;
    private Instant modified;
}
