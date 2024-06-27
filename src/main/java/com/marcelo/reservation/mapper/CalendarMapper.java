package com.marcelo.reservation.mapper;


import com.marcelo.reservation.dto.calendar.CalendarDto;
import com.marcelo.reservation.model.Calendar;
import com.marcelo.reservation.model.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CalendarMapper {
    @Mapping(target="memberId", source="member.id")
    @Mapping(target="scheduleIds", expression="java(getScheduleIds(calendar.getSchedules()))")
    @Mapping(target="unavailableScheduleIds", expression="java(getUnavailableScheduleIds(calendar.getUnavailableSchedules()))")
    CalendarDto mapToDto(Calendar calendar);

    default List<Long> getScheduleIds(List<Schedule> schedules){
        return schedules.stream().map(schedule-> schedule.getId()).collect(Collectors.toList());
    }

    default List<Long> getUnavailableScheduleIds(List<Schedule> unavailableSchedules){
        return unavailableSchedules.stream().map(schedule-> schedule.getId()).collect(Collectors.toList());
    }
    List<CalendarDto> mapToDtoList(List<Calendar> calendars);
}
