package com.marcelo.reservation.service;

import com.marcelo.reservation.dto.address.*;
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

    public List<AddressDto> getAllAddressesByBusinessId(Long businessId) {
        businessRepository.findById(businessId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Business with id %s doesn't exist", businessId)));
        List<Address> addresses = addressRepository.findAllByBusinessId(businessId);
        logger.info("{} addresses found for business with id {}", addresses.size(), businessId);
        return addressMapper.mapToDtoList(addresses);
    }

    public AddressDto patchAddressName(AddressPatchNameRequest request) {
        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Address with id %s not found", request.getAddressId())));
        address.setName(request.getName());
        return addressMapper.mapToDto(addressRepository.save(address));
    }

    public AddressDto patchAddressLatitude(AddressPatchGeolocationLatitudeRequest request) {
        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Address with id %s not found", request.getAddressId())));
        address.getGeolocation().setLatitude(request.getLatitude());
        return addressMapper.mapToDto(addressRepository.save(address));
    }
    public AddressDto patchAddressLongitude(AddressPatchGeolocationLongitudeRequest request) {
        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Address with id %s not found", request.getAddressId())));
        address.getGeolocation().setLongitude(request.getLongitude());
        return addressMapper.mapToDto(addressRepository.save(address));
    }
}
