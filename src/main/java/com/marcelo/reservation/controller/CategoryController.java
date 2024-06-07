package com.marcelo.reservation.controller;

import com.marcelo.reservation.dto.category.CategoryDto;
import com.marcelo.reservation.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/category/")
@RequiredArgsConstructor
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("getAll")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping(value = "create",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> createCategory(
            @Valid @RequestPart("data") CategoryDto categoryDto, @RequestPart("image") MultipartFile image){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryDto, image));
    }

    @DeleteMapping("delete/{categoryId}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable("categoryId") Long categoryId){
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }

    @PatchMapping("updateName")
    public ResponseEntity<CategoryDto> updateCategoryName(@RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.updateCategoryName(categoryDto));
    }

    @PatchMapping(value = "updateImage",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> updateCategoryImage(
            @RequestPart("image") MultipartFile image, @RequestPart("data") CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.updateCategoryImage(image, categoryDto));
    }

    @PatchMapping("updateServices")
    public ResponseEntity<CategoryDto> updateServices(@RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.updateServices(categoryDto));
    }
}
