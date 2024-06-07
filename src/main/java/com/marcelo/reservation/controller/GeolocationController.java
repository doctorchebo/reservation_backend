package com.marcelo.reservation.controller;

import com.marcelo.reservation.dto.geolocation.GeolocationDto;
import com.marcelo.reservation.service.GeolocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/geolocation")
@RequiredArgsConstructor
@Validated
public class GeolocationController {
    private final GeolocationService geolocationService;

    @GetMapping("getAll")
    public ResponseEntity<List<GeolocationDto>> getAllGeolocations(){
        return ResponseEntity.ok(geolocationService.getAllGeolocations());
    }

    @GetMapping("getByAddressId/{addressId}")
    public ResponseEntity<GeolocationDto> getGeolocationByBusinessId(@PathVariable Long addressId){
        return ResponseEntity.ok(geolocationService.getGeolocationByAddressId(addressId));
    }

    @PostMapping("create")
    public ResponseEntity<GeolocationDto> createGeolocation(@Valid @RequestBody GeolocationDto geolocationDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(geolocationService.createGeolocation(geolocationDto));
    }

    @DeleteMapping("deleteByAddressId/{businessId}")
    public ResponseEntity<GeolocationDto> deleteGeolocationByBusinessId(@PathVariable Long businessId){
        return ResponseEntity.ok(geolocationService.deleteGeolocationByAddressId(businessId));
    }
}
