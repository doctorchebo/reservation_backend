package com.marcelo.reservation.dto.reservation;

import com.marcelo.reservation.dto.business.BusinessResponse;
import com.marcelo.reservation.dto.member.MemberDto;
import com.marcelo.reservation.dto.service.ServiceDto;
import com.marcelo.reservation.dto.user.UserDto;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class ReservationDetailedDto {
    private UUID id;
    private String name;
    private Instant startTime;
    private Instant endTime;
    private UserDto user;
    private BusinessResponse business;
    private ServiceDto service;
    private MemberDto member;
    private Instant created;
    private Instant modified;
}
