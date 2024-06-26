package com.marcelo.reservation.controller;

import com.marcelo.reservation.dto.reservation.ReservationDetailedDto;
import com.marcelo.reservation.dto.reservation.ReservationDto;
import com.marcelo.reservation.dto.reservation.ReservationRequest;
import com.marcelo.reservation.service.ReservationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservation/")
@AllArgsConstructor
@Validated
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("getAllByBusinessId/{businessId}")
    public ResponseEntity<List<ReservationDto>> getAllByBusinessId(
            @Positive @PathVariable("businessId") Long businessId){
        return ResponseEntity.ok(reservationService.getAllByBusinessId(businessId));
    }

    @GetMapping("getAllByStartDate/{serviceId}/{businessId}/{date}")
    public ResponseEntity<List<ReservationDto>> getAllReservationsByStartDate(
            @Valid @PathVariable("serviceId") UUID serviceId,
            @PathVariable("businessId") Long businessId,
            @PathVariable("date") Instant date){
        return ResponseEntity.ok(reservationService.getAllReservationsByDate(serviceId, businessId, date));
    }

    @GetMapping("getAllByMemberAndStartDate/{memberId}/{serviceId}/{businessId}/{date}")
    public ResponseEntity<List<ReservationDto>> getAllReservationsByMemberAndStartDate(
            @Valid @PathVariable("memberId") Long memberId,
            @PathVariable("serviceId") UUID serviceId,
            @PathVariable("businessId") Long businessId,
            @PathVariable("date") Instant date){
        return ResponseEntity.ok(reservationService.getAllReservationsByMemberAndStartDate(memberId, serviceId, businessId, date));
    }

    @GetMapping("getAllByUserId/{userId}")
    public ResponseEntity<List<ReservationDto>> getAllByUserId(
            @Positive @PathVariable("userId") Long userId){
        return ResponseEntity.ok(reservationService.getAllByUserId(userId));
    }

    @GetMapping("getAllForCurrentUser")
    public ResponseEntity<List<ReservationDetailedDto>> getAllForCurrentUser(){
        return ResponseEntity.ok(reservationService.getAllForCurrentUser());
    }
    @PostMapping("create")
    public ResponseEntity<ReservationDto> createReservation(@Valid @RequestBody ReservationRequest reservationRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.createReservation(reservationRequest));
    }

    @DeleteMapping("delete/{reservationId}")
    public ResponseEntity<ReservationDetailedDto> deleteReservation(@PathVariable("reservationId") UUID reservationId){
        return ResponseEntity.ok(reservationService.deleteReservation(reservationId));
    }
}
