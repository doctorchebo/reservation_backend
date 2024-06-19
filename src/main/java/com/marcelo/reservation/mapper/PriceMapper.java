package com.marcelo.reservation.mapper;

import com.marcelo.reservation.dto.price.PriceDto;
import com.marcelo.reservation.model.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PriceMapper {
    @Mapping(target="serviceId", source="service.id")
    @Mapping(target="businessId", source="business.id")
    PriceDto mapToDto(Price price);

    List<PriceDto> mapToDtoList(List<Price> prices);
}
