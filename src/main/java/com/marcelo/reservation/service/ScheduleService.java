package com.marcelo.reservation.service;

import com.marcelo.reservation.dto.schedule.*;
import com.marcelo.reservation.exception.NotFoundException;
import com.marcelo.reservation.exception.NotValidDateException;
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

import java.time.*;
import java.util.Date;
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
                // db interprets 1 as 0. DayOfWeek's correct equivalent number is modified in front end
                .dayOfWeek(DayOfWeek.of(request.getDayOfWeek().getValue()))
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

    public ScheduleDto patchScheduleDayOfWeek(SchedulePatchDayOfWeekRequest request) {
        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Schedule with id %s not found", request.getScheduleId())));
        schedule.setDayOfWeek(request.getDayOfWeek());
        return scheduleMapper.mapToDto(scheduleRepository.save(schedule));
    }

    public ScheduleDto patchScheduleIsWholeDay(SchedulePatchIsWholeDayRequest request) {
        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Schedule with id %s not found", request.getScheduleId())));
        schedule.setWholeDay(request.isWholeDay());
        // remove start and end time if isWholeDay is true
        if(request.isWholeDay()){
            schedule.setStartTime(null);
            schedule.setEndTime(null);
        } else {
            // provisionally provide 9 to 5 schedule
            OffsetDateTime dateTime = OffsetDateTime.now();
            schedule.setStartTime(dateTime.with(LocalTime.of(9, 0)).toInstant());
            schedule.setEndTime(dateTime.with(LocalTime.of(17, 0)).toInstant());
        }        return scheduleMapper.mapToDto(scheduleRepository.save(schedule));
    }

    public ScheduleDto patchScheduleStartTime(SchedulePatchStartTimeRequest request) {
        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Schedule with id %s not found", request.getScheduleId())));
        if(request.getStartTime().isAfter(schedule.getEndTime())){
            // start time cannot be after end time
            throw new NotValidDateException("Start Time cannot be after End Time");
        }
        schedule.setStartTime(request.getStartTime());
        return scheduleMapper.mapToDto(scheduleRepository.save(schedule));
    }

    public ScheduleDto patchScheduleEndTime(SchedulePatchEndTimeRequest request) {
        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Schedule with id %s not found", request.getScheduleId())));
        if(request.getEndTime().isBefore(schedule.getStartTime())){
            // end time cannot be before start time
            throw new NotValidDateException("End Time cannot be before Start Time");
        }
        schedule.setEndTime(request.getEndTime());
        return scheduleMapper.mapToDto(scheduleRepository.save(schedule));
    }
}
