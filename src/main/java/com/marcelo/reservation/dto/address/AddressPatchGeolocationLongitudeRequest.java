package com.marcelo.reservation.dto.address;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddressPatchGeolocationLongitudeRequest {
    private Long addressId;
    private BigDecimal longitude;
}
