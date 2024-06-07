package com.marcelo.reservation.mapper;

import com.marcelo.reservation.dto.duration.DurationDto;
import com.marcelo.reservation.model.Duration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DurationMapper {

    @Mapping(target="serviceId", source="service.id")
    @Mapping(target="businessId", source="business.id")
    DurationDto mapToDto(Duration duration);

    List<DurationDto> mapToDtoList(List<Duration> durations);
}
