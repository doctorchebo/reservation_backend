package com.marcelo.reservation.dto.schedule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.Instant;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduleCreateRequest {
    private Long calendarId;
    private DayOfWeek dayOfWeek;
    @JsonProperty("isWholeDay")
    private boolean isWholeDay;
    private Instant startTime;
    private Instant endTime;
}
