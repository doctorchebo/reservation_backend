package com.marcelo.reservation.dto.business;

import lombok.Data;

import java.util.List;

@Data
public class BusinessPatchCategoriesRequest {
    private Long businessId;
    private List<Long> categoryIds;
}
