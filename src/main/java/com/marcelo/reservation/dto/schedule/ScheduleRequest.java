package com.marcelo.reservation.dto.schedule;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.Instant;

@Data
public class ScheduleRequest {
    private Long id;
    private Long calendarId;
    private DayOfWeek dayOfWeek;
    private boolean isWholeDay;
    private Instant startTime;
    private Instant endTime;
    private Instant created;
    private Instant modified;
}