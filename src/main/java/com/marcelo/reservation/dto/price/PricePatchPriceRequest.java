package com.marcelo.reservation.dto.price;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PricePatchPriceRequest {
    private Long priceId;
    private BigDecimal price;
}
