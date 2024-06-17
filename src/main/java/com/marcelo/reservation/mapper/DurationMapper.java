package com.marcelo.reservation.mapper;

import com.marcelo.reservation.dto.duration.DurationDto;
import com.marcelo.reservation.model.Duration;
import com.marcelo.reservation.model.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DurationMapper {

    @Mapping(target="serviceIds", expression="java(getServiceIds(duration.services))")
    @Mapping(target="businessId", source="business.id")
    DurationDto mapToDto(Duration duration);

    List<DurationDto> mapToDtoList(List<Duration> durations);

    default List<UUID> getServiceIds(List<Service> services){
        if(services.isEmpty()){
            return Collections.emptyList();
        }
        return services
                .stream()
                .map(service-> service.getId())
                .collect(Collectors.toList());
    }
}
