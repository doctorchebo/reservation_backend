package com.marcelo.reservation.repository;

import com.marcelo.reservation.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByBusinessId(Long businessId);
    @Modifying
    @Query("DELETE FROM Address a WHERE a.id = :addressId")
    void deleteById(@Param("addressId") Long addressId);
}
