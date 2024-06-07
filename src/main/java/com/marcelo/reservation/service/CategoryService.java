package com.marcelo.reservation.service;

import com.marcelo.reservation.dto.category.CategoryDto;
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
import org.springframework.web.multipart.MultipartFile;

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
    public CategoryDto createCategory(CategoryDto categoryDto, MultipartFile image) {
        String imageUrl = imageUploadService.uploadFile("media/images/", image);
        Category category = Category.builder()
                .name(categoryDto.getName())
                .services(new ArrayList<com.marcelo.reservation.model.Service>())
                .imageUrl(imageUrl)
                .build();
        return categoryMapper.mapToDto(categoryRepository.save(category));
    }

    public List<CategoryDto> getAllCategories() {
        logger.info("Getting all categories");
        List<Category> categories = categoryRepository.findAll();
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

    public CategoryDto updateCategoryName(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.getId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Category with id %s not found", categoryDto.getId())));
        category.setName(categoryDto.getName());
        return categoryMapper.mapToDto(categoryRepository.save(category));
    }

    @Transactional
    public CategoryDto updateCategoryImage(MultipartFile image, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.getId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Category with id %s not found", categoryDto.getId())));

        // delete old image
        imageUploadService.deleteFileFromS3Bucket(category.getImageUrl());

        String imageUrl = imageUploadService.uploadFile("media/images/", image);
        category.setImageUrl(imageUrl);

        return categoryMapper.mapToDto(categoryRepository.save(category));
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
}
