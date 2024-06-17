package com.marcelo.reservation.repository;

import com.marcelo.reservation.model.Duration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DurationRepository extends JpaRepository<Duration, Long> {
    Optional<Duration> findAllByServicesIdAndBusinessId(UUID serviceId, Long businessId);

    List<Duration> findAllByBusinessId(Long businessId);
}
