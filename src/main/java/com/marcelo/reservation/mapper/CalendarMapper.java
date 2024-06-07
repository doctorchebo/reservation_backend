package com.marcelo.reservation.mapper;


import com.marcelo.reservation.dto.calendar.CalendarDto;
import com.marcelo.reservation.model.Calendar;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CalendarMapper {
    CalendarDto mapToDto(Calendar calendar);

    List<CalendarDto> mapToDtoList(List<Calendar> calendars);
}
