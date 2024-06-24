package com.marcelo.reservation.service;

import com.marcelo.reservation.controller.ReservationController;
import com.marcelo.reservation.dto.reservation.ReservationDetailedDto;
import com.marcelo.reservation.dto.reservation.ReservationDto;
import com.marcelo.reservation.dto.reservation.ReservationRequest;
import com.marcelo.reservation.dto.user.UserDto;
import com.marcelo.reservation.exception.NotFoundException;
import com.marcelo.reservation.exception.NotValidDateException;
import com.marcelo.reservation.mapper.ReservationMapper;
import com.marcelo.reservation.model.*;
import com.marcelo.reservation.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {
    private final DurationRepository durationRepository;

    private static Logger logger = LoggerFactory.getLogger(ReservationController.class);
    private final ReservationRepository reservationRepository;

    private final MemberRepository memberRepository;

    private final ReservationMapper reservationMapper;

    private final BusinessRepository businessRepository;

    private final UserRepository userRepository;

    private final ServiceRepository serviceRepository;

    private final UserService userService;

    private final AuthService authService;

    public List<ReservationDto> getAllByBusinessId(Long businessId){
        logger.info("Getting all reservations for business with id {}", businessId);
        List<Reservation> reservations = reservationRepository.findAllByBusinessId(businessId);
        return reservationMapper.mapToDtoList(reservations);
    }

    public List<ReservationDto> getAllByUserId(Long userId) {
        logger.info("Getting available dates for user with id {}", userId);
        return reservationMapper.mapToDtoList(reservationRepository.findAllByUserId(userId));
    }

    public List<ReservationDetailedDto> getAllForCurrentUser() {
        UserDto currentUser = userService.getCurrentUserDto();
        List<Reservation> reservations = reservationRepository.findAllByUserId(currentUser.getId());
        logger.info("{} reservations found", reservations.size());
        return reservationMapper.mapToDetailedDtoList(reservations);
    }

    public ReservationDto createReservation(ReservationRequest reservationRequest) {
        User user = userRepository.findById(reservationRequest.getUserId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with id %s not found", reservationRequest.getUserId())));

        Business business = businessRepository.findById(reservationRequest.getBusinessId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Business with id %s not found", reservationRequest.getBusinessId())));

        Member member = memberRepository.findById(reservationRequest.getMemberId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Member with id %s not found", reservationRequest.getMemberId())));

        com.marcelo.reservation.model.Service service = serviceRepository.findById(reservationRequest.getServiceId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Service with id %s not found", reservationRequest.getMemberId())));

        Duration duration = durationRepository.findAllByServicesIdAndBusinessId(reservationRequest.getServiceId(), reservationRequest.getBusinessId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Duration with service id %s and business id %s not found",
                                reservationRequest.getServiceId(),
                                reservationRequest.getBusinessId())));

        Instant endTime = reservationRequest.getStartTime().plus(duration.getDuration()).minusMillis(1);
        List<Reservation> reservations = reservationRepository.findAllByReservationTimeBetween(
                reservationRequest.getStartTime(), endTime);
        if(!reservations.isEmpty()){
            throw new NotValidDateException("Reservation time not available");
        }
        Reservation reservation = Reservation.builder()
                .name(reservationRequest.getName())
                .startTime(reservationRequest.getStartTime())
                // Subtracting 1 millisecond from endDate to avoid reservation date overlapping
                .endTime(endTime)
                .business(business)
                .member(member)
                .service(service)
                .user(user)
                .created(Instant.now())
                .build();

        return reservationMapper.mapToDto(reservationRepository.save(reservation));
    }

    public List<ReservationDto> getAllReservationsByDate(UUID serviceId, Long businessId, Instant date) {
        Instant endDate = date.plus(1, ChronoUnit.DAYS);
        List<Reservation> reservations = reservationRepository.findAllByServiceIdAndBusinessIdAndDate(serviceId, businessId, date, endDate);
        return reservationMapper.mapToDtoList(reservations);
    }

    @Transactional
    public ReservationDetailedDto deleteReservation(UUID reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Reservation with id %s not found", reservationId)));

        reservationRepository.deleteByReservationId(reservation.getId());

        return reservationMapper.mapToDetailedDto(reservation);
    }
}
