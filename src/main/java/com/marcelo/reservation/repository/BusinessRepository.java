package com.marcelo.reservation.repository;

import com.marcelo.reservation.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    Optional<Business> findById(Long businessId);
    List<Business> findByCategoriesId(Long categoryId);

    @Query("SELECT DISTINCT b FROM Business b " +
            "JOIN b.services s " +
            "LEFT JOIN Reservation r ON r.service = s AND r.startTime < :endDate AND r.endTime > :startDate " +
            "WHERE r.id IS NULL AND s.id = :serviceId")
    List<Business> findAllByServiceIdAndStartDate(@Param("serviceId") UUID serviceId, @Param("startDate") Instant startDate, @Param("endDate") Instant endDate);

    List<Business> findByOwnerId(Long ownerId);

    @Modifying
    @Query("DELETE FROM Business b WHERE b.id = :businessId")
    void deleteById(@Param("businessId") Long businessId);
}
