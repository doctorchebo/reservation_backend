package com.marcelo.reservation.mapper;

import com.marcelo.reservation.dto.address.AddressDto;
import com.marcelo.reservation.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {GeolocationMapper.class})
public interface AddressMapper {
    @Mapping(target="businessId", source="business.id")
    AddressDto mapToDto(Address address);

    List<AddressDto> mapToDtoList(List<Address> addresses);
}
