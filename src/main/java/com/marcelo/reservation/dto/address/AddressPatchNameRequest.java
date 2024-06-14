package com.marcelo.reservation.dto.address;

import lombok.Data;

@Data
public class AddressPatchNameRequest {
    private Long addressId;
    private String name;
}
