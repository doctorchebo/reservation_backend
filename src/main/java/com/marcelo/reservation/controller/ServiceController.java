package com.marcelo.reservation.controller;

import com.marcelo.reservation.dto.service.*;
import com.marcelo.reservation.service.ServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/service")
@RequiredArgsConstructor
@Validated
public class ServiceController {
    private final ServiceService serviceService;

    @GetMapping("getAll")
    public ResponseEntity<List<ServiceDto>> getAllServices(){
        return ResponseEntity.ok(serviceService.getAllServices());
    }

    @GetMapping("getById/{serviceId}")
    public ResponseEntity<ServiceDto> getServiceById(@PathVariable UUID serviceId){
        return ResponseEntity.ok(serviceService.getServiceById(serviceId));
    }

    @GetMapping("getAllByBusinessId/{businessId}")
    public ResponseEntity<List<ServiceDto>> getAllByBusinessId(@PathVariable() Long businessId){
        return ResponseEntity.ok(serviceService.getAllByBusinessId(businessId));
    }

    @GetMapping("getAllByCategoryId/{categoryId}")
    public ResponseEntity<List<ServiceDto>> getAllByCategoryId(@PathVariable() Long categoryId){
        return ResponseEntity.ok(serviceService.getAllByCategoryId(categoryId));
    }

    @GetMapping("getAllAvailableByBusinessId/{businessId}")
    public ResponseEntity<List<ServiceDto>> getAllServicesAvailableByBusinessId(@PathVariable Long businessId){
        return ResponseEntity.ok(serviceService.getAllServicesAvailableByBusinessId(businessId));
    }

    @GetMapping("getAvailableServices/{serviceId}/{startDate}")
    public ResponseEntity<List<ServiceDto>> getAvailableServicesByIdAndStartDate(
            @PathVariable("serviceId") UUID serviceId, @PathVariable("startDate") Instant startDate){
        return ResponseEntity.ok(serviceService.getAvailableServicesByIdAndStartDate(serviceId, startDate));
    }

    @PostMapping("create")
    public ResponseEntity<ServiceDto> createService(@Valid @RequestBody ServiceCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceService.createService(request));
    }

    @DeleteMapping("deleteById/{serviceId}")
    public ResponseEntity<ServiceDto> deleteService(@Valid @PathVariable("serviceId") UUID serviceId){
        return ResponseEntity.ok(serviceService.deleteService(serviceId));
    }

    @PutMapping("update")
    public ResponseEntity<ServiceDto> updateService(@Valid @RequestBody ServiceDto serviceDto){
        return ResponseEntity.ok(serviceService.updateService(serviceDto));
    }

    @PatchMapping("patchName")
    public ResponseEntity<ServiceDto> patchServiceName(@Valid @RequestBody ServicePatchNameRequest request){
        return ResponseEntity.ok(serviceService.patchServiceName(request));
    }

    @PatchMapping("patchDurations")
    public ResponseEntity<ServiceDto> patchServiceDurations(@Valid @RequestBody ServicePatchDurationsRequest request){
        return ResponseEntity.ok(serviceService.patchDurations(request));
    }
    @PatchMapping("patchAddresses")
    public ResponseEntity<ServiceDto> patchServiceAddresses(@Valid @RequestBody ServicePatchAddressesRequest request){
        return ResponseEntity.ok(serviceService.patchServiceAddresses(request));
    }

    @PatchMapping("patchCategories")
    public ResponseEntity<ServiceDto> patchCategories(@Valid @RequestBody ServiceDto serviceDto){
        return ResponseEntity.ok(serviceService.patchCategories(serviceDto));
    }
    @PatchMapping("patchPrice")
    public ResponseEntity<ServiceDto> patchServicePrice(@Valid @RequestBody ServicePatchPriceRequest request){
        return ResponseEntity.ok(serviceService.patchServicePrice(request));
    }
}
