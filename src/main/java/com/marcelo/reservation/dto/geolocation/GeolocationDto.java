package com.marcelo.reservation.dto.geolocation;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class GeolocationDto {
    private Long id;
    private Long addressId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Instant created;
    private Instant modified;
}
