package com.marcelo.reservation.repository;

import com.marcelo.reservation.model.Category;
import com.marcelo.reservation.model.Duration;
import com.marcelo.reservation.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceRepository extends JpaRepository<Service, UUID> {

    Optional<Service> findById(UUID serviceId);
    List<Service> findByCategoriesId(Long categoryId);
    @Query("SELECT s FROM Service s " +
            "WHERE NOT EXISTS (" +
            "    SELECT r FROM Reservation r " +
            "    WHERE r.service = s " +
            "    AND r.startTime < :endDate " +
            "    AND r.endTime > :startDate" +
            ")" +
            "AND s.id = :serviceId")
    List<Service> findAvailableServices(UUID serviceId, Instant endDate, Instant startDate);

    List<Service> findAllByBusinessId(Long businessId);

    Optional<Service> findAllByIdAndBusinessId(UUID serviceId, Long businessId);

    List<Service> findAllByCategories(List<Category> categories);

    List<Service> findByDurations(List<Duration> durations);
}
