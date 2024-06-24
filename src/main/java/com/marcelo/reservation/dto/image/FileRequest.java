package com.marcelo.reservation.dto.image;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileRequest {
    private Long id;
    private String url;
    private MultipartFile file;
}
