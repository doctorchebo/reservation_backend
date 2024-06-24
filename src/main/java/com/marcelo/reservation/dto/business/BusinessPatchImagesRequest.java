package com.marcelo.reservation.dto.business;

import com.marcelo.reservation.dto.image.FileRequest;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BusinessPatchImagesRequest {
    private Long businessId;
    private List<FileRequest> files;
}
