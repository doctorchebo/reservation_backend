package com.marcelo.reservation.service;

import com.marcelo.reservation.dto.service.*;
import com.marcelo.reservation.exception.NotFoundException;
import com.marcelo.reservation.mapper.ServiceMapper;
import com.marcelo.reservation.model.*;
import com.marcelo.reservation.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceService {
    private final PriceRepository priceRepository;
    private final AddressRepository addressRepository;
    private static Logger logger = LoggerFactory.getLogger(ServiceService.class);

    private final ServiceRepository serviceRepository;

    private final ServiceMapper serviceMapper;

    private final BusinessRepository businessRepository;

    private final DurationRepository durationRepository;

    private final CategoryRepository categoryRepository;

    private final PriceService priceService;

    public List<ServiceDto> getAllServices() {
        return serviceMapper.mapToDtoList(serviceRepository.findAll());
    }

    public ServiceDto getServiceById(UUID serviceId) {
        com.marcelo.reservation.model.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Service with id %s not found", serviceId)));
        return serviceMapper.mapToDto(service);
    }
    public List<ServiceDto> getAllByBusinessId(Long businessId) {
        List<com.marcelo.reservation.model.Service> services = serviceRepository.findAllByBusinessId(businessId);
        return serviceMapper.mapToDtoList(services);
    }

    public List<ServiceDto> getAllByCategoryId(Long categoryId) {
        List<com.marcelo.reservation.model.Service> services = serviceRepository.findByCategoriesId(categoryId);
        return serviceMapper.mapToDtoList(services);
    }

    @Transactional
    public ServiceDto createService(ServiceCreateRequest request) {
        Business business = businessRepository.findById(request.getBusinessId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Business with id %s not found", request.getBusinessId())));

        List<Address> addresses = addressRepository.findAllById(request.getAddressIds());
        List<Duration> durations = durationRepository.findAllById(request.getDurationIds());

        com.marcelo.reservation.model.Service service = com.marcelo.reservation.model.Service.builder()
                .name(request.getName())
                .business(business)
                .durations(durations)
                .addresses(addresses)
                .created(Instant.now())
                .build();

        // create and assign price for new service
        Price price = Price.builder()
                .price(request.getPrice())
                .service(service)
                .business(business)
                .created(Instant.now())
                .build();

        service.setPrices(Arrays.asList(price));

        return serviceMapper.mapToDto(serviceRepository.save(service));
    }

    public ServiceDto deleteService(UUID serviceId) {
        com.marcelo.reservation.model.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Business with id %s not found", serviceId)));
        List<Price> prices = service.getPrices();
        for(Price price: prices){
            price.setService(null);
        }
        priceRepository.saveAll(prices);
        serviceRepository.deleteById(service.getId());
        return serviceMapper.mapToDto(service);
    }

    public ServiceDto updateService(ServiceDto serviceDto) {
        Business business = businessRepository.findById(serviceDto.getBusinessId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Business with id %s not found", serviceDto.getBusinessId())));

        com.marcelo.reservation.model.Service service = serviceMapper.map(serviceDto, business);

        serviceRepository.deleteById(serviceDto.getId());

        return serviceMapper.mapToDto(serviceRepository.save(service));
    }

    public List<ServiceDto> getAvailableServicesByIdAndStartDate(UUID serviceId, Instant startDate) {
        com.marcelo.reservation.model.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Service with id %s not found", serviceId)));
        List<Duration> durations = service.getDurations();
        List<java.time.Duration> durationsObj = durations.stream().map(Duration::getDuration).collect(Collectors.toList());
        Instant endDate = startDate.plus(Collections.max(durationsObj));
        List<com.marcelo.reservation.model.Service> services = 
                serviceRepository.findAvailableServices(serviceId, endDate, startDate);
        return serviceMapper.mapToDtoList(services);
    }

    public List<ServiceDto> getAllServicesAvailableByBusinessId(Long businessId) {
        return serviceMapper.mapToDtoList(serviceRepository.findAllByBusinessId(businessId));
    }

    public ServiceDto patchServiceName(ServicePatchNameRequest request) {
        com.marcelo.reservation.model.Service service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Service with id %s not found", request.getServiceId())));
        service.setName(request.getName());
        return serviceMapper.mapToDto(serviceRepository.save(service));
    }

    public ServiceDto patchDurations(ServicePatchDurationsRequest request) {
        logger.info("Patching service durations");
        com.marcelo.reservation.model.Service service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Service with id %s not found", request.getServiceId())));

        List<Duration> durations = durationRepository.findAllById(request.getDurationIds());
        service.getDurations().clear();
        service.setDurations(durations);
        com.marcelo.reservation.model.Service savedService = serviceRepository.save(service);

        return serviceMapper.mapToDto(savedService);
    }

    public ServiceDto patchServiceAddresses(ServicePatchAddressesRequest request) {
        com.marcelo.reservation.model.Service service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Service with id %s not found", request.getServiceId())));

        List<Address> addresses = addressRepository.findAllById(request.getAddressIds());
        service.getAddresses().clear();
        service.setAddresses(addresses);
        com.marcelo.reservation.model.Service savedService = serviceRepository.save(service);

        return serviceMapper.mapToDto(savedService);
    }

    public ServiceDto patchCategories(ServiceDto serviceDto) {
        logger.info("Patching categories");
        com.marcelo.reservation.model.Service service = serviceRepository.findById(serviceDto.getId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Service with id %s not found", serviceDto.getId())));

        List<Category> categories = categoryRepository.findAllById(serviceDto.getCategoryIds());
        List<Category> existingCategories = service.getCategories();
        for(Category category : categories){
            if(!existingCategories.contains(category)){
                existingCategories.add(category);
            }
        }
        com.marcelo.reservation.model.Service savedService = serviceRepository.save(service);

        return serviceMapper.mapToDto(savedService);
    }


    public ServiceDto patchServicePrice(ServicePatchPriceRequest request) {
        com.marcelo.reservation.model.Service service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Service with id %s not found", request.getServiceId())));
        for(Price price : service.getPrices()){
            if(price.getBusiness().getId().equals(request.getBusinessId())){
                price.setPrice(request.getPrice());
            }
        }
        return serviceMapper.mapToDto(serviceRepository.save(service));
    }
}
