package com.marcelo.reservation.service;

import com.marcelo.reservation.dto.schedule.ScheduleDto;
import com.marcelo.reservation.dto.schedule.ScheduleCreateRequest;
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

import java.time.DayOfWeek;
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

    public  List<ScheduleDto> getAllSchedulesByBusinessId(Long businessId) {
        return scheduleMapper.mapToDtoList(scheduleRepository.findAllByScheduleCalendarMemberBusinessId(businessId));
    }

    public List<ScheduleDto> getAllSchedulesByMemberId(Long memberId) {
        return scheduleMapper.mapToDtoList(scheduleRepository.findAllByScheduleCalendarMemberId(memberId));
    }

    public List<ScheduleDto> getAllSchedulesByCalendarId(Long calendarId) {
        return scheduleMapper.mapToDtoList(scheduleRepository.findAllByScheduleCalendarId(calendarId));
    }
    public ScheduleDto createSchedule(ScheduleCreateRequest request) {
        Calendar calendar = calendarRepository.findById(request.getCalendarId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Calendar with id %s not found", request.getCalendarId())));
        Schedule schedule = Schedule.builder()
                .scheduleCalendar(calendar)
                .unavailableDateCalendar(calendar)
                // db interprets 1 as 0
                .dayOfWeek(DayOfWeek.of(request.getDayOfWeek().getValue() - 1))
                .isWholeDay(request.isWholeDay())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .created(Instant.now())
                .build();
        return scheduleMapper.mapToDto(scheduleRepository.save(schedule));
    }

    public ScheduleDto deleteSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Schedule with id %s not found", scheduleId)));
        scheduleRepository.deleteById(schedule.getId());
        return scheduleMapper.mapToDto(schedule);
    }
}
