package com.marcelo.reservation.controller;

import com.marcelo.reservation.dto.category.CategoryCreateRequest;
import com.marcelo.reservation.dto.category.CategoryDto;
import com.marcelo.reservation.dto.category.CategoryPatchImageRequest;
import com.marcelo.reservation.dto.category.CategoryPatchNameRequest;
import com.marcelo.reservation.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "create")
    public ResponseEntity<CategoryDto> createCategory(@ModelAttribute CategoryCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(request));
    }

    @DeleteMapping("delete/{categoryId}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable("categoryId") Long categoryId){
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }

    @PatchMapping("patchName")
    public ResponseEntity<CategoryDto> patchCategoryName(@RequestBody CategoryPatchNameRequest request){
        return ResponseEntity.ok(categoryService.patchCategoryName(request));
    }

    @PatchMapping(value = "patchImage")
    public ResponseEntity<CategoryDto> patchCategoryImage(@Valid @ModelAttribute CategoryPatchImageRequest request){
        return ResponseEntity.ok(categoryService.patchCategoryImage(request));
    }

    @PatchMapping("updateServices")
    public ResponseEntity<CategoryDto> updateServices(@RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.updateServices(categoryDto));
    }
}
