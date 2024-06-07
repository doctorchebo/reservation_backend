package com.marcelo.reservation.mapper;

import com.marcelo.reservation.dto.image.ImageDto;
import com.marcelo.reservation.model.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel="spring")
public interface ImageMapper {
    @Mapping(target="businessId", source="business.id")
    ImageDto mapToDto(Image image);

    List<ImageDto> mapToDtoList(List<Image> images);
}
