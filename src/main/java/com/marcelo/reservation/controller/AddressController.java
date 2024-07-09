package com.marcelo.reservation.controller;

import com.marcelo.reservation.dto.address.*;
import com.marcelo.reservation.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address/")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping("getAll")
    public ResponseEntity<List<AddressDto>> getAllAddresses(){
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    @GetMapping("getAllByBusinessId/{businessId}")
    public ResponseEntity<List<AddressDto>> getAllAddressesByBusinessId(@PathVariable Long businessId){
        return ResponseEntity.ok(addressService.getAllAddressesByBusinessId(businessId));
    }

    @PostMapping("create")
    public ResponseEntity<AddressDto> createAddress(@RequestBody AddressCreateRequest addressCreateRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.createAddress(addressCreateRequest));
    }

    @DeleteMapping("delete/{addressId}")
    public ResponseEntity<AddressDto> deleteAddressId(@PathVariable Long addressId){
        return ResponseEntity.ok(addressService.deleteAddress(addressId));
    }

    @PatchMapping("patchName")
    public ResponseEntity<AddressDto> patchAddressName(@RequestBody AddressPatchNameRequest addressPatchNameRequest){
        return ResponseEntity.ok(addressService.patchAddressName(addressPatchNameRequest));
    }
    @PatchMapping("patchLatitude")
    public ResponseEntity<AddressDto> patchAddressLatitude(@RequestBody AddressPatchGeolocationLatitudeRequest request){
        return ResponseEntity.ok(addressService.patchAddressLatitude(request));
    }
    @PatchMapping("patchLongitude")
    public ResponseEntity<AddressDto> patchAddressLongitude(@RequestBody AddressPatchGeolocationLongitudeRequest request){
        return ResponseEntity.ok(addressService.patchAddressLongitude(request));
    }
    @PatchMapping("patchIsMainAddress")
    public ResponseEntity<AddressDto> patchAddressIsMainAddress(@RequestBody AddressPatchIsMainAddressRequest request){
        return ResponseEntity.ok(addressService.patchAddressIsMainAddress(request));
    }
}
