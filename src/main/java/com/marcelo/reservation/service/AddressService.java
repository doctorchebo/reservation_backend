package com.marcelo.reservation.service;

import com.marcelo.reservation.dto.address.AddressDto;
import com.marcelo.reservation.dto.address.AddressRequest;
import com.marcelo.reservation.exception.NotFoundException;
import com.marcelo.reservation.mapper.AddressMapper;
import com.marcelo.reservation.model.Address;
import com.marcelo.reservation.model.Business;
import com.marcelo.reservation.model.Geolocation;
import com.marcelo.reservation.repository.AddressRepository;
import com.marcelo.reservation.repository.BusinessRepository;
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
public class AddressService {
    private static Logger logger = LoggerFactory.getLogger(AddressService.class);

    private final BusinessRepository businessRepository;

    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;
    public AddressDto createAddress(AddressRequest addressRequest) {
        Business business = businessRepository.findById(addressRequest.getBusinessId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Business with id %s not found", addressRequest.getBusinessId())));
        Address address = Address.builder()
                .name(addressRequest.getName())
                .business(business)
                .geolocation(null)
                .isMainAddress(addressRequest.isMainAddress())
                .created(Instant.now())
                .build();
        Address savedAddress = addressRepository.save(address);

        Geolocation geolocation = Geolocation.builder()
                .address(savedAddress)
                .longitude(addressRequest.getLongitude())
                .latitude(addressRequest.getLatitude())
                .created(Instant.now())
                .build();
        savedAddress.setGeolocation(geolocation);

        Address savedAddressWithGeolocation = addressRepository.save(savedAddress);
        return addressMapper.mapToDto(savedAddressWithGeolocation);
    }

    public List<AddressDto> getAllAddresses() {
        return addressMapper.mapToDtoList(addressRepository.findAll());
    }

    public AddressDto deleteAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Business with id %s not found", addressId)));
        addressRepository.delete(address);
        return addressMapper.mapToDto(address);
    }
}
