package com.marcelo.reservation.dto.image;
import lombok.Data;

import java.time.Instant;

@Data
public class ImageDto {
    private Long id;
    private Long businessId;
    private String url;
    private Instant created;
    private Instant modified;
}
