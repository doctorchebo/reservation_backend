package com.marcelo.reservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Geolocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "geolocation")
    private Address address;

    @NotNull(message = "Longitude cannot be null")
    @Column(precision = 18, scale = 15)
    private BigDecimal longitude;

    @NotNull(message = "Latitude cannot be null")
    @Column(precision = 18, scale = 15)
    private BigDecimal latitude;

    private Instant created;

    private Instant modified;
}
