package com.marcelo.reservation.controller;

import com.marcelo.reservation.dto.address.AddressDto;
import com.marcelo.reservation.dto.address.AddressRequest;
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

    @PostMapping("create")
    public ResponseEntity<AddressDto> createAddress(@RequestBody AddressRequest addressRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.createAddress(addressRequest));
    }

    @DeleteMapping("deleteById/{addressId}")
    public ResponseEntity<AddressDto> deleteAddressById(@PathVariable Long addressId){
        return ResponseEntity.ok(addressService.deleteAddressById(addressId));
    }
}
