package com.marcelo.reservation.service;

import com.marcelo.reservation.dto.geolocation.GeolocationDto;
import com.marcelo.reservation.exception.NotFoundException;
import com.marcelo.reservation.mapper.GeolocationMapper;
import com.marcelo.reservation.model.Address;
import com.marcelo.reservation.model.Geolocation;
import com.marcelo.reservation.repository.AddressRepository;
import com.marcelo.reservation.repository.GeolocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeolocationService {
    private static Logger logger = LoggerFactory.getLogger(GeolocationService.class);

    private final GeolocationMapper geolocationMapper;

    private final GeolocationRepository geolocationRepository;

    private final AddressRepository addressRepository;
    public GeolocationDto getGeolocationByAddressId(Long addressId) {
        Geolocation geolocation = geolocationRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Geolocation with id %s not found", addressId)));
        return geolocationMapper.mapToDto(geolocation);
    }

    public GeolocationDto createGeolocation(GeolocationDto geolocationDto) {
        Address address = addressRepository.findById(geolocationDto.getAddressId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Address with id %s not found", geolocationDto.getAddressId())));
        Geolocation geolocation = Geolocation.builder()
                .address(address)
                .latitude(geolocationDto.getLatitude())
                .longitude(geolocationDto.getLongitude())
                .created(Instant.now())
                .build();

        return geolocationMapper.mapToDto(geolocationRepository.save(geolocation));
    }

    public GeolocationDto deleteGeolocationByAddressId(Long addressId) {
        Geolocation geolocation = geolocationRepository.findByAddressId(addressId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Geolocation for address with id %s not found", addressId)));
        geolocationRepository.delete(geolocation);
        return geolocationMapper.mapToDto(geolocation);
    }

    public List<GeolocationDto> getAllGeolocations() {
        return geolocationMapper.mapToDtoList(geolocationRepository.findAll());
    }
}
