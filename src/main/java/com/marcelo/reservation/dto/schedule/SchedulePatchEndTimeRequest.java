package com.marcelo.reservation.dto.schedule;

import lombok.Data;

import java.time.Instant;

@Data
public class SchedulePatchEndTimeRequest {
    private Long scheduleId;
    private Instant endTime;
}
