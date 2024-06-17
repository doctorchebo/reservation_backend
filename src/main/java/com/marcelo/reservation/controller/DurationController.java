package com.marcelo.reservation.controller;


import com.marcelo.reservation.dto.duration.DurationDto;
import com.marcelo.reservation.dto.duration.DurationRequest;
import com.marcelo.reservation.service.DurationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/duration")
@RequiredArgsConstructor
public class DurationController {
    private final DurationService durationService;

    @GetMapping("getAll")
    public ResponseEntity<List<DurationDto>> getAllDurations(){
        return ResponseEntity.ok(durationService.getAllDurations());
    }
    @GetMapping("getAllByServiceIdAndBusinessId/{serviceId}/{businessId}")
    public ResponseEntity<DurationDto> getAllByServiceIdAndBusinessId(
            @PathVariable("serviceId") UUID serviceId, @PathVariable("businessId") Long businessId){
        return ResponseEntity.ok(durationService.getAllByServiceIdAndBusinessId(serviceId, businessId));
    }
    @GetMapping("getAllByBusinessId/{businessId}")
    public ResponseEntity<List<DurationDto>> getAllDurationsByBusinessId(@PathVariable Long businessId){
        return ResponseEntity.ok(durationService.getAllDurationsByBusinessId(businessId));
    }

    @PostMapping("create")
    public ResponseEntity<DurationDto> createDuration(@RequestBody DurationRequest durationRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(durationService.createDuration(durationRequest));
    }

    @DeleteMapping("deleteById/{durationId}")
    public ResponseEntity<DurationDto> deleteDuration(@Valid @PathVariable("durationId") Long durationId){
        return ResponseEntity.ok(durationService.deleteDuration(durationId));
    }
}
