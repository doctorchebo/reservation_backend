package com.marcelo.reservation.dto.address;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddressPatchGeolocationLatitudeRequest {
    private Long addressId;
    private BigDecimal latitude;
}
