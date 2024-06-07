package com.marcelo.reservation.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddressRequest {
    private Long businessId;
    private String name;
    private BigDecimal longitude;
    private BigDecimal latitude;
    @JsonProperty("isMainAddress")
    private boolean isMainAddress;
}
