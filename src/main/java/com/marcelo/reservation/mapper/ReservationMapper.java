package com.marcelo.reservation.mapper;

import com.marcelo.reservation.dto.reservation.ReservationDetailedDto;
import com.marcelo.reservation.dto.reservation.ReservationDto;
import com.marcelo.reservation.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {UserMapper.class, BusinessMapper.class, ServiceMapper.class, MemberMapper.class})
public interface ReservationMapper {
    @Mapping(target="userId", source="user.id")
    @Mapping(target="businessId", source="business.id")
    @Mapping(target="serviceId", source="service.id")
    @Mapping(target="memberId", source="member.id")
    ReservationDto mapToDto(Reservation reservation);

    ReservationDetailedDto mapToDetailedDto(Reservation reservation);
    Reservation map(ReservationDto reservationDto);
    List<ReservationDto> mapToDtoList(List<Reservation> reservations);

    List<ReservationDetailedDto> mapToDetailedDtoList(List<Reservation> reservations);
}
