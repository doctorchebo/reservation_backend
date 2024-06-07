package com.marcelo.reservation.mapper;

import com.marcelo.reservation.dto.geolocation.GeolocationDto;
import com.marcelo.reservation.model.Geolocation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GeolocationMapper {
    @Mapping(target="addressId", source="address.id")
    GeolocationDto mapToDto(Geolocation geolocation);

    List<GeolocationDto> mapToDtoList(List<Geolocation> geolocations);
}
