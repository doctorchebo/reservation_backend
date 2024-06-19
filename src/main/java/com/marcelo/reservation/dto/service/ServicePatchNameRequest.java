package com.marcelo.reservation.dto.service;

import lombok.Data;

import java.util.UUID;

@Data
public class ServicePatchNameRequest {
    private UUID serviceId;
    private String name;
}
