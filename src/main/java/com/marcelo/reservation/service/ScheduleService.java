package com.marcelo.reservation.service;

import com.marcelo.reservation.dto.schedule.ScheduleDto;
import com.marcelo.reservation.dto.schedule.ScheduleRequest;
import com.marcelo.reservation.exception.NotFoundException;
import com.marcelo.reservation.mapper.ScheduleMapper;
import com.marcelo.reservation.model.Calendar;
import com.marcelo.reservation.model.Schedule;
import com.marcelo.reservation.repository.CalendarRepository;
import com.marcelo.reservation.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {
    private static Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    private final ScheduleRepository scheduleRepository;

    private final CalendarRepository calendarRepository;

    private final ScheduleMapper scheduleMapper;
    public List<ScheduleDto> getAllSchedules() {
        return scheduleMapper.mapToDtoList(scheduleRepository.findAll());
    }

    public ScheduleDto createSchedule(ScheduleRequest scheduleRequest) {
        Calendar calendar = calendarRepository.findById(scheduleRequest.getCalendarId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Calendar with id %s not found", scheduleRequest.getCalendarId())));
        Schedule schedule = Schedule.builder()
                .scheduleCalendar(calendar)
                .unavailableDateCalendar(calendar)
                .dayOfWeek(scheduleRequest.getDayOfWeek())
                .isWholeDay(scheduleRequest.isWholeDay())
                .startTime(scheduleRequest.getStartTime())
                .endTime(scheduleRequest.getEndTime())
                .created(Instant.now())
                .build();
        return scheduleMapper.mapToDto(scheduleRepository.save(schedule));
    }
}
