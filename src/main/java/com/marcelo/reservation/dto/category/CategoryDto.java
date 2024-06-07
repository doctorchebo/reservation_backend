package com.marcelo.reservation.dto.category;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private String imageUrl;
    private List<UUID> serviceIds;
}
