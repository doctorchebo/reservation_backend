package com.marcelo.reservation.dto.address;
import lombok.Data;

@Data
public class AddressPatchIsMainAddressRequest {
    private Long addressId;
    private Long businessId;
}
