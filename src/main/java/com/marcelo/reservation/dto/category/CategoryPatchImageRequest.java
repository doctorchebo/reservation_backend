package com.marcelo.reservation.dto.category;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryPatchImageRequest {
    private Long categoryId;
    private MultipartFile image;
}
