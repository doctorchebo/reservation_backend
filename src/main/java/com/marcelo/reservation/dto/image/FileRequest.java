package com.marcelo.reservation.dto.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileRequest {
    private Long id;
    private String url;
    private MultipartFile file;
}
