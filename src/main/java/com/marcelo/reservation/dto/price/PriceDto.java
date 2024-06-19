package com.marcelo.reservation.dto.price;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
public class
PriceDto {
    private Long id;
    private UUID serviceId;
    private Long businessId;
    private BigDecimal price;
    private Instant created;
    private Instant modified;
}
