package com.marcelo.reservation.repository;

import com.marcelo.reservation.model.Geolocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeolocationRepository extends JpaRepository<Geolocation, Long> {
    Optional<Geolocation> findByAddressId(Long addressId);
}
