package com.marcelo.reservation.repository;

import com.marcelo.reservation.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findAllByBusinessId(Long businessId);
    Optional<Price> findByServiceIdAndBusinessId(UUID serviceId, Long businessId);

}
