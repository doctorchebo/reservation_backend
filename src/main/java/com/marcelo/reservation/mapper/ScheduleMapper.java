package com.marcelo.reservation.mapper;

import com.marcelo.reservation.dto.schedule.ScheduleDto;
import com.marcelo.reservation.model.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    @Mapping(target="calendarId", source="scheduleCalendar.id")
    ScheduleDto mapToDto(Schedule schedule);

    List<ScheduleDto> mapToDtoList(List<Schedule> schedules);
}
