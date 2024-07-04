package com.marcelo.reservation.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class ScheduleDto {
    private Long id;
    private Long calendarId;
    private int dayOfWeek;
    @JsonProperty("isWholeDay")
    private boolean isWholeDay;
    private Instant startTime;
    private Instant endTime;
    private Instant created;
    private Instant modified;
}
