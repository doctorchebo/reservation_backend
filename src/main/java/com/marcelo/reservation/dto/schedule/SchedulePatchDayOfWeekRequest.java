package com.marcelo.reservation.dto.schedule;
import lombok.Data;
import java.time.DayOfWeek;

@Data
public class SchedulePatchDayOfWeekRequest {
    private Long scheduleId;
    private DayOfWeek dayOfWeek;
}
