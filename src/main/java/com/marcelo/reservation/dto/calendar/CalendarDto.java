package com.marcelo.reservation.dto.calendar;
import com.marcelo.reservation.dto.schedule.ScheduleDto;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class CalendarDto {
    private Long id;
    private List<ScheduleDto> schedules;
    private List<ScheduleDto> unavailableDates;
    private Long memberId;
    private Instant created;
    private Instant modified;
}
