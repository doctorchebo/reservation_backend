package com.marcelo.reservation.controller;

import com.marcelo.reservation.dto.price.PriceDto;
import com.marcelo.reservation.dto.price.PricePatchPriceRequest;
import com.marcelo.reservation.service.PriceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/price/")
@RequiredArgsConstructor
public class PriceController {
    private final PriceService priceService;
    @GetMapping("getAll")
    public ResponseEntity<List<PriceDto>> getAllPrices(){
        return ResponseEntity.ok(priceService.getAllPrices());
    }

    @GetMapping("getAllByBusinessId/{businessId}")
    public ResponseEntity<List<PriceDto>> getAllPriceByBusinessId(@PathVariable Long businessId){
        return ResponseEntity.ok(priceService.getAllPriceByBusinessId(businessId));
    }

    @GetMapping("getByServiceIdAndBusinessId/{serviceId}/{businessId}")
    public ResponseEntity<PriceDto> getPriceByServiceIdAndBusinessId(
            @PathVariable("serviceId")UUID serviceId, @PathVariable("businessId") Long businessId){
        return ResponseEntity.ok(priceService.getPriceByServiceIdAndBusinessId(serviceId, businessId));
    }

    @PostMapping("create")
    public ResponseEntity<PriceDto> createPrice(@Valid @RequestBody PriceDto priceDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(priceService.createPrice(priceDto));
    }
    @DeleteMapping("deleteById/{priceId}")
    public ResponseEntity<PriceDto> deletePriceById(@PathVariable Long priceId){
        return ResponseEntity.ok(priceService.deletePriceById(priceId));
    }

    @PatchMapping("patchPrice")
    public ResponseEntity<PriceDto> patchPricePrice(@RequestBody PricePatchPriceRequest request){
        return ResponseEntity.ok(priceService.patchPricePrice(request));
    }
}
