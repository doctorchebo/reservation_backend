package com.marcelo.reservation.service;

import com.marcelo.reservation.dto.address.*;
import com.marcelo.reservation.exception.NotFoundException;
import com.marcelo.reservation.mapper.AddressMapper;
import com.marcelo.reservation.model.Address;
import com.marcelo.reservation.model.Business;
import com.marcelo.reservation.model.Geolocation;
import com.marcelo.reservation.model.Member;
import com.marcelo.reservation.repository.AddressRepository;
import com.marcelo.reservation.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {
    private static Logger logger = LoggerFactory.getLogger(AddressService.class);

    private final BusinessRepository businessRepository;

    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;
    public AddressDto createAddress(AddressCreateRequest addressCreateRequest) {
        Business business = businessRepository.findById(addressCreateRequest.getBusinessId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Business with id %s not found", addressCreateRequest.getBusinessId())));
        Address address = Address.builder()
                .name(addressCreateRequest.getName())
                .business(business)
                .geolocation(null)
                .isMainAddress(addressCreateRequest.isMainAddress())
                .members(new ArrayList<Member>())
                .services(new ArrayList<com.marcelo.reservation.model.Service>())
                .created(Instant.now())
                .build();
        Address savedAddress = addressRepository.save(address);

        Geolocation geolocation = Geolocation.builder()
                .address(savedAddress)
                .longitude(addressCreateRequest.getLongitude())
                .latitude(addressCreateRequest.getLatitude())
                .created(Instant.now())
                .build();
        savedAddress.setGeolocation(geolocation);

        Address savedAddressWithGeolocation = addressRepository.save(savedAddress);
        // if new address is the main one, make other addresses secondary
        if(addressCreateRequest.isMainAddress()){
            List<Address> addresses = addressRepository.findAllByBusinessId(addressCreateRequest.getBusinessId());
            for(Address existingAddress : addresses){
                if(!addresses.contains(address)) {
                    existingAddress.setMainAddress(false);
                }
            }
            addressRepository.saveAll(addresses);
        }

        return addressMapper.mapToDto(savedAddressWithGeolocation);
    }

    public List<AddressDto> getAllAddresses() {
        return addressMapper.mapToDtoList(addressRepository.findAll());
    }

    public AddressDto deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Business with id %s not found", addressId)));
        addressRepository.deleteById(address.getId());
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

    public AddressDto patchAddressIsMainAddress(AddressPatchIsMainAddressRequest request) {
        Address targetAddress = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Address with id %s not found", request.getAddressId())));
        targetAddress.setMainAddress(true);
        Address savedAddress = addressRepository.save(targetAddress);
        // make rest of addresses not main
        List<Address> addresses = addressRepository.findAllByBusinessId(request.getBusinessId());
        for(Address address : addresses){
            if(!addresses.contains(targetAddress)){
                address.setMainAddress(false);
            }
        }
        addressRepository.saveAll(addresses);

        return addressMapper.mapToDto(addressRepository.save(savedAddress));
    }
}
