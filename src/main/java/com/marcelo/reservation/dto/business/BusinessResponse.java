package com.marcelo.reservation.dto.business;

import com.marcelo.reservation.dto.address.AddressDto;
import com.marcelo.reservation.dto.category.CategoryDto;
import com.marcelo.reservation.dto.image.ImageDto;
import com.marcelo.reservation.dto.member.MemberDto;
import com.marcelo.reservation.dto.service.ServiceDto;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class BusinessResponse {
    private Long id;
    private String name;
    private Long ownerId;
    private List<CategoryDto> categories;
    private List<ImageDto> images;
    private List<AddressDto> addresses;
    private List<ServiceDto> services;
    private List<MemberDto> members;
    private Instant created;
}
