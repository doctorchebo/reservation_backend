package com.marcelo.reservation.mapper;

import com.marcelo.reservation.dto.business.BusinessDto;
import com.marcelo.reservation.dto.business.BusinessResponse;
import com.marcelo.reservation.model.Business;
import com.marcelo.reservation.model.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses={
        UserMapper.class,
        CategoryMapper.class,
        GeolocationMapper.class,
        ImageMapper.class,
        AddressMapper.class,
        ServiceMapper.class,
        MemberMapper.class,
        UserMapper.class})
public interface BusinessMapper {
    @Mapping(target="ownerId", source="owner.id")
    @Mapping(target="categoryIds", expression= "java(getCategoryIds(business))")
    BusinessDto mapToDto(Business business);

    @Mapping(target="reservationCount", expression= "java(getReservationCount(business))")
    BusinessResponse mapToResponse(Business business);

    default List<Long> getCategoryIds(Business business){
        return business.getCategories().stream()
                .map(category -> category.getId())
                .collect(Collectors.toList());
    }
    default int getReservationCount(Business business){
        int count = 0;
        for(Service service: business.getServices()){
            count += service.getReservations().size();
        }
        return count;
    }
    List<BusinessDto> mapToDtoList(List<Business> businesses);

    Business map(BusinessDto businessDto);

    List<BusinessResponse> mapToResponseList(List<Business> businesses);
}
