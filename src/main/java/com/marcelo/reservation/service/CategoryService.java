package com.marcelo.reservation.service;

import com.marcelo.reservation.dto.category.CategoryCreateRequest;
import com.marcelo.reservation.dto.category.CategoryDto;
import com.marcelo.reservation.dto.category.CategoryPatchImageRequest;
import com.marcelo.reservation.dto.category.CategoryPatchNameRequest;
import com.marcelo.reservation.exception.NotFoundException;
import com.marcelo.reservation.mapper.CategoryMapper;
import com.marcelo.reservation.model.Category;
import com.marcelo.reservation.repository.CategoryRepository;
import com.marcelo.reservation.repository.ServiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final ServiceRepository serviceRepository;
    private static Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final ImageUploadService imageUploadService;

    private final S3Service s3Service;
    public CategoryDto createCategory(CategoryCreateRequest request) {
        String imageUrl = imageUploadService.uploadFile("media/images/category/", request.getImage());
        Category category = Category.builder()
                .name(request.getName())
                .services(new ArrayList<com.marcelo.reservation.model.Service>())
                .imageUrl(imageUrl)
                .build();
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.mapToDto(presignImageUrls(savedCategory));
    }

    public List<CategoryDto> getAllCategories() {
        logger.info("Getting all categories");
        List<Category> categories = categoryRepository.findAllByOrderByNameAsc();
        logger.info("{} categories found", categories.size());
        for(Category category : categories){
            if(category.getImageUrl() != null){
                String imageUrl = s3Service.generatePresignedUrl(category.getImageUrl());
                category.setImageUrl(imageUrl);
            }
        }
        return categoryMapper.mapToDtoList(categories);
    }

    @Transactional
    public CategoryDto deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Category with id %s not found", categoryId)));

        categoryRepository.delete(category);
        // delete image from AWS
        if(category.getImageUrl() != null){
            imageUploadService.deleteFileFromS3Bucket(category.getImageUrl());

        }
        return categoryMapper.mapToDto(category);
    }

    public CategoryDto patchCategoryName(CategoryPatchNameRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Category with id %s not found", request.getCategoryId())));
        category.setName(request.getName());
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.mapToDto(presignImageUrls(savedCategory));
    }

    public CategoryDto patchCategoryImage(CategoryPatchImageRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Category with id %s not found", request.getCategoryId())));

        // delete old image
        if(!category.getImageUrl().isEmpty()){
            imageUploadService.deleteFileFromS3Bucket(category.getImageUrl());

        }
        String imageUrl = imageUploadService.uploadFile("media/images/category/", request.getImage());
        category.setImageUrl(imageUrl);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.mapToDto(presignImageUrls(savedCategory));
    }

    public CategoryDto updateServices(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.getId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Category with id %s not found", categoryDto.getId())));
        List<com.marcelo.reservation.model.Service> services = serviceRepository.findAllById(categoryDto.getServiceIds());
        category.setServices(services);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.mapToDto(savedCategory);
    }

    private List<Category> presignImageUrls(List<Category> categories){
        // getting presigned urls for categories images
        for(Category category: categories){
            if(!category.getImageUrl().isEmpty()){
                String imageUrl = s3Service.generatePresignedUrl(category.getImageUrl());
                category.setImageUrl(imageUrl);
            }
        }
        return categories;
    }

    private Category presignImageUrls(Category category){
        // getting presigned urls for category images
        if(!category.getImageUrl().isEmpty()){
            String imageUrl = s3Service.generatePresignedUrl(category.getImageUrl());
            category.setImageUrl(imageUrl);
        }
        return category;
    }
}
