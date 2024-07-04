package com.marcelo.reservation.dto.schedule;

import lombok.Data;

import java.time.Instant;

@Data
public class SchedulePatchStartTimeRequest {
    private Long scheduleId;
    private Instant startTime;
}
