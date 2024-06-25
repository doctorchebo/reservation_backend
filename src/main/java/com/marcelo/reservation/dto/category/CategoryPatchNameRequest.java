package com.marcelo.reservation.dto.category;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryPatchNameRequest {
    private Long categoryId;
    private String name;
}
