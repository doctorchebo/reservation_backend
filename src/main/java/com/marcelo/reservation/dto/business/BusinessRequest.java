package com.marcelo.reservation.dto.business;

import lombok.Data;

import java.util.List;

@Data
public class BusinessRequest {
    private String name;
    private Long ownerId;
    private List<Long> categoryIds;
}
