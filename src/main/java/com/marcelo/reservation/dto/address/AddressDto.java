package com.marcelo.reservation.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marcelo.reservation.dto.geolocation.GeolocationDto;
import lombok.Data;

import java.time.Instant;

@Data
public class AddressDto {

    private Long id;
    private Long businessId;
    private GeolocationDto geolocation;
    private String name;
    @JsonProperty("isMainAddress")
    private boolean isMainAddress;
    private Instant created;
    private Instant modified;
}
