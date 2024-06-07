package com.marcelo.reservation.mapper;

import com.marcelo.reservation.dto.category.CategoryDto;
import com.marcelo.reservation.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target="serviceIds", expression = "java(getServiceIds(category))")
    CategoryDto mapToDto(Category category);
    List<CategoryDto> mapToDtoList(List<Category> categories);

    Category map(CategoryDto categoryDto);

    default List<UUID> getServiceIds(Category category){
        return category.getServices()
                .stream()
                .map(service -> service.getId())
                .collect(Collectors.toList());
    }
}
