package com.marcelo.reservation.dto.category;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryCreateRequest {
    private String name;
    private MultipartFile image;
}
