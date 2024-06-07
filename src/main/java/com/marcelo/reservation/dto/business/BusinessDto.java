package com.marcelo.reservation.dto.business;

import com.marcelo.reservation.dto.geolocation.GeolocationDto;
import com.marcelo.reservation.dto.image.ImageDto;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class BusinessDto {
    private Long id;
    private String name;
    private Long ownerId;
    private List<Long> categoryIds;
    private List<GeolocationDto> geolocations;
    private List<ImageDto> images;
    private Instant created;
}
