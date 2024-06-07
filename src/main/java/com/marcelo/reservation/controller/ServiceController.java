package com.marcelo.reservation.controller;

import com.marcelo.reservation.dto.service.ServiceDto;
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

    @GetMapping("getAvailableServices/{serviceId}/{startDate}")
    public ResponseEntity<List<ServiceDto>> getAvailableServicesByIdAndStartDate(
            @PathVariable("serviceId") UUID serviceId, @PathVariable("startDate") Instant startDate){
        return ResponseEntity.ok(serviceService.getAvailableServicesByIdAndStartDate(serviceId, startDate));
    }

    @PostMapping("create")
    public ResponseEntity<ServiceDto> createService(@Valid @RequestBody ServiceDto serviceDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceService.createService(serviceDto));
    }

    @DeleteMapping("deleteById/{serviceId}")
    public ResponseEntity<ServiceDto> deleteService(@Valid @PathVariable("serviceId") UUID serviceId){
        return ResponseEntity.ok(serviceService.deleteService(serviceId));
    }

    @PutMapping("update")
    public ResponseEntity<ServiceDto> updateService(@Valid @RequestBody ServiceDto serviceDto){
        return ResponseEntity.ok(serviceService.updateService(serviceDto));
    }

    @PatchMapping("patchDurations")
    public ResponseEntity<ServiceDto> patchDurations(@Valid @RequestBody ServiceDto serviceDto){
        return ResponseEntity.ok(serviceService.patchDuration(serviceDto));
    }

    @PatchMapping("patchCategories")
    public ResponseEntity<ServiceDto> patchCategories(@Valid @RequestBody ServiceDto serviceDto){
        return ResponseEntity.ok(serviceService.patchCategories(serviceDto));
    }
}
