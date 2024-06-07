package com.marcelo.reservation.dto.calendar;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class CalendarDto {
    private Long id;
    private List<Long> scheduleIds;
    private List<Long> unavailableDateIds;
    private Long memberId;
    private Instant created;
    private Instant modified;
}
