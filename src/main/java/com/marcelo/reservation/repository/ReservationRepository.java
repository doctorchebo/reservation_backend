package com.marcelo.reservation.repository;

import com.marcelo.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findAllByBusinessId(Long businessId);

    List<Reservation> findAllByUserId(Long userId);

    @Query("SELECT r FROM Reservation r WHERE r.startTime < :endDate AND r.endTime > :startDate")
    List<Reservation> findAllByReservationTimeBetween(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);

    @Query("SELECT r FROM Reservation r WHERE r.startTime < :endDate AND r.endTime > :startDate AND r.service.id = :serviceId AND r.business.id = :businessId")
    List<Reservation> findAllByServiceIdAndBusinessIdAndDate(@Param("serviceId") UUID serviceId, @Param("businessId") Long businessId, @Param("startDate") Instant startDate, @Param("endDate") Instant endDate);

    @Query("SELECT r FROM Reservation r WHERE r.startTime < :endDate AND r.endTime > :startDate AND r.service.id = :serviceId AND r.business.id = :businessId AND (:memberId IS NULL OR r.member.id = :memberId)")
    List<Reservation> findAllByMemberIdAndServiceIdAndBusinessIdAndDate(@Param("memberId") Long memberId, @Param("serviceId") UUID serviceId, @Param("businessId") Long businessId, @Param("startDate") Instant startDate, @Param("endDate") Instant endDate);

    @Modifying
    @Query("DELETE FROM Reservation r WHERE r.id = :reservationId")
    void deleteByReservationId(@Param("reservationId") UUID reservationId);
}
