package com.marcelo.reservation.mapper;

import com.marcelo.reservation.dto.service.ServiceDto;
import com.marcelo.reservation.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface ServiceMapper {
    @Mapping(target="durationIds", expression="java(getDurationsIds(service.durations))")
    @Mapping(target="categoryIds", expression="java(getCategoriesIds(service.categories))")
    @Mapping(target="businessId", source="business.id")
    @Mapping(target="addressIds", expression="java(getAddressIds(service.addresses))")
    ServiceDto mapToDto(Service service);

    List<ServiceDto> mapToDtoList(List<Service> services);

    @Mapping(target="id", ignore = true)
    @Mapping(target="created", source="serviceDto.created")
    @Mapping(target="modified", expression="java(java.time.Instant.now())")
    @Mapping(target="name", source="serviceDto.name")
    @Mapping(target="business", source="business")
    Service map(ServiceDto serviceDto, Business business);

    default List<Long> getDurationsIds(List<Duration> durations){
        if(durations == null){
            return Collections.emptyList();
        }
        return durations.stream()
                .map(duration -> duration.getId())
                .collect(Collectors.toList());
    }

    default List<Long> getCategoriesIds(List<Category> categories){
        if(categories == null){
            return Collections.emptyList();
        }
        return categories.stream()
                .map(category -> category.getId())
                .collect(Collectors.toList());
    }
    default List<Long> getBusinessIds(List<Business> businesses){
        if(businesses == null){
            return Collections.emptyList();
        }
        return businesses.stream()
                .map(business -> business.getId())
                .collect(Collectors.toList());
    }
    default List<Long> getAddressIds(List<Address> addresses){
        if(addresses == null){
            return Collections.emptyList();
        }
        return addresses.stream()
                .map(address -> address.getId())
                .collect(Collectors.toList());
    }
}

