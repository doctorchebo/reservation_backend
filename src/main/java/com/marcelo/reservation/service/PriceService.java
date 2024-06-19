package com.marcelo.reservation.service;

import com.marcelo.reservation.dto.price.PriceDto;
import com.marcelo.reservation.dto.price.PricePatchPriceRequest;
import com.marcelo.reservation.exception.NotFoundException;
import com.marcelo.reservation.mapper.PriceMapper;
import com.marcelo.reservation.model.Business;
import com.marcelo.reservation.model.Price;
import com.marcelo.reservation.repository.BusinessRepository;
import com.marcelo.reservation.repository.PriceRepository;
import com.marcelo.reservation.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceService {
    private static Logger logger = LoggerFactory.getLogger(PriceService.class);
    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    private final ServiceRepository serviceRepository;
    private final BusinessRepository businessRepository;
    public List<PriceDto> getAllPrices() {
        return priceMapper.mapToDtoList(priceRepository.findAll());
    }

    public List<PriceDto> getAllPriceByBusinessId(Long businessId) {
        return priceMapper.mapToDtoList(priceRepository.findAllByBusinessId(businessId));
    }

    public PriceDto getPriceByServiceIdAndBusinessId(UUID serviceId, Long businessId) {
        Price price = priceRepository.findByServiceIdAndBusinessId(serviceId, businessId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Price with service id %s and business id %s not found", serviceId, businessId)));
        return priceMapper.mapToDto(price);
    }
    public PriceDto createPrice(PriceDto priceDto) {
        com.marcelo.reservation.model.Service service = serviceRepository.findById(priceDto.getServiceId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Service with id %s not found", priceDto.getServiceId())));
        Business business = businessRepository.findById(priceDto.getBusinessId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Business with id %s not found", priceDto.getBusinessId())));

        Price price = Price.builder()
                .business(business)
                .service(service)
                .price(priceDto.getPrice())
                .created(Instant.now())
                .build();

        return priceMapper.mapToDto(priceRepository.save(price));
    }

    public PriceDto deletePriceById(Long priceId) {
        Price price = priceRepository.findById(priceId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Price with id %s not found", priceId)));
        priceRepository.delete(price);
        return priceMapper.mapToDto(price);
    }

    public PriceDto patchPricePrice(PricePatchPriceRequest request) {
        Price price = priceRepository.findById(request.getPriceId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Price with id %s not found", request.getPriceId())));
        price.setPrice(request.getPrice());
        price.setModified(Instant.now());
        return priceMapper.mapToDto(priceRepository.save(price));
    }
}
