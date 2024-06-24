package com.marcelo.reservation.controller;

import com.marcelo.reservation.dto.business.*;
import com.marcelo.reservation.service.BusinessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/business/")
@RequiredArgsConstructor
@Validated
public class BusinessController {
    private final BusinessService businessService;

    @GetMapping("getAll")
    public ResponseEntity<List<BusinessResponse>> getAllBusinesses(){
        return ResponseEntity.ok(businessService.getAllBusinesses());
    }

    @GetMapping("getById/{businessId}")
    public ResponseEntity<BusinessResponse> getBusinessById(@PathVariable Long businessId){
        return ResponseEntity.ok(businessService.getBusinessById(businessId));
    }

    @GetMapping("getAllByCategoryId/{categoryId}")
    public ResponseEntity<List<BusinessResponse>> getAllBusinessesByCategoryId(@PathVariable Long categoryId){
        return ResponseEntity.ok(businessService.getAllBusinessesByCategoryId(categoryId));
    }

    @GetMapping("getAllByUserId/{userId}")
    public ResponseEntity<List<BusinessResponse>> getAllBusinessesByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(businessService.getAllBusinessesByUserId(userId));
    }

    @PostMapping("searchAvailable")
    public ResponseEntity<List<BusinessResponse>> getAllAvailableByServiceId(@RequestBody AvailableRequest availableRequest){
        return ResponseEntity.ok(businessService.getAllAvailableByServiceId(availableRequest));
    }

    @PostMapping("create")
    public ResponseEntity<BusinessResponse> createBusiness(@RequestBody BusinessRequest businessRequest){
        return ResponseEntity.ok(businessService.createBusiness(businessRequest));
    }

    @DeleteMapping("delete/{businessId}")
    public ResponseEntity<BusinessResponse> deleteBusiness(@Valid @PathVariable Long businessId){
        return ResponseEntity.ok(businessService.deleteBusiness(businessId));
    }


    @PutMapping("update")
    public ResponseEntity<BusinessResponse> updateBusiness(@Valid @RequestBody BusinessDto businessDto){
        return ResponseEntity.ok(businessService.updateBusiness(businessDto));
    }

    @PatchMapping("patchName")
    public ResponseEntity<BusinessResponse> patchBusinessName(@Valid @RequestBody BusinessPatchNameRequest businessPatchNameRequest){
        return ResponseEntity.ok(businessService.patchBusinessName(businessPatchNameRequest));
    }

    @PatchMapping("patchCategories")
    public ResponseEntity<BusinessResponse> patchBusinessCategories(@Valid @RequestBody BusinessPatchCategoriesRequest businessPatchCategoriesRequest){
        return ResponseEntity.ok(businessService.patchBusinessCategories(businessPatchCategoriesRequest));
    }

    @PatchMapping("patchActiveMembers")
    public ResponseEntity<BusinessResponse> patchBusinessActiveMembers(@Valid @RequestBody BusinessPatchMembersRequest businessPatchCategoriesRequest){
        return ResponseEntity.ok(businessService.patchBusinessActiveMembers(businessPatchCategoriesRequest));
    }

    @PatchMapping(value = "updateImages",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BusinessResponse> updateBusinessImages(
            @Valid @RequestParam("businessId") Long businessId, @RequestPart("images") List<MultipartFile> images){
        return ResponseEntity.ok(businessService.updateBusinessImages(businessId, images));
    }

    @PatchMapping(value = "patchServices")
    public ResponseEntity<BusinessResponse> patchBusinessServices(@Valid @RequestBody BusinessPatchServicesRequest businessPatchServicesRequest){
        return ResponseEntity.ok(businessService.patchBusinessServices(businessPatchServicesRequest));
    }

    @PatchMapping(value = "patchImages")
    public ResponseEntity<BusinessResponse> patchBusinessImages(@Valid @ModelAttribute BusinessPatchImagesRequest request){
        return ResponseEntity.ok(businessService.patchBusinessImages(request));
    }

    @GetMapping("getAvailableByServiceId/{serviceId}/{startDate}")
    public ResponseEntity<List<BusinessResponse>> getAllByServiceIdAndStartDate(@PathVariable("serviceId") UUID serviceId, @PathVariable("startDate") Instant startDate){
        return ResponseEntity.ok(businessService.getAllByServiceIdAndStartDate(serviceId, startDate));
    }
}
