package com.marcelo.reservation.service;

import com.marcelo.reservation.dto.service.ServiceDto;
import com.marcelo.reservation.exception.NotFoundException;
import com.marcelo.reservation.mapper.ServiceMapper;
import com.marcelo.reservation.model.Business;
import com.marcelo.reservation.model.Category;
import com.marcelo.reservation.model.Duration;
import com.marcelo.reservation.repository.BusinessRepository;
import com.marcelo.reservation.repository.CategoryRepository;
import com.marcelo.reservation.repository.DurationRepository;
import com.marcelo.reservation.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceService {
    private static Logger logger = LoggerFactory.getLogger(ServiceService.class);

    private final ServiceRepository serviceRepository;

    private final ServiceMapper serviceMapper;

    private final BusinessRepository businessRepository;

    private final DurationRepository durationRepository;

    private final CategoryRepository categoryRepository;

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
        List<com.marcelo.reservation.model.Service> services = serviceRepository.findAllByBusinessesId(businessId);
        return serviceMapper.mapToDtoList(services);
    }

    public List<ServiceDto> getAllByCategoryId(Long categoryId) {
        List<com.marcelo.reservation.model.Service> services = serviceRepository.findByCategoriesId(categoryId);
        return serviceMapper.mapToDtoList(services);
    }

    public ServiceDto createService(ServiceDto serviceDto) {
        List<Business> businesses = businessRepository.findAllById(serviceDto.getBusinessIds());

        //List<Category> categories = categoryRepository.findAllById(serviceDto.getCategoryIds());

        com.marcelo.reservation.model.Service service = com.marcelo.reservation.model.Service.builder()
                .name(serviceDto.getName())
                .businesses(businesses)
                .durations(new ArrayList<Duration>())
                .created(Instant.now())
                .build();

        return serviceMapper.mapToDto(serviceRepository.save(service));
    }

    public ServiceDto deleteService(UUID serviceId) {
        com.marcelo.reservation.model.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Business with id %s not found", serviceId)));
        serviceRepository.delete(service);
        return serviceMapper.mapToDto(service);
    }

    public ServiceDto updateService(ServiceDto serviceDto) {
        List<Business> businesses = businessRepository.findAllById(serviceDto.getBusinessIds());

        com.marcelo.reservation.model.Service service = serviceMapper.map(serviceDto, businesses);

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

    public ServiceDto patchDuration(ServiceDto serviceDto) {
        com.marcelo.reservation.model.Service service = serviceRepository.findById(serviceDto.getId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Service with id %s not found", serviceDto.getId())));

        List<Duration> durations = durationRepository.findAllById(serviceDto.getDurationIds());
        List<Duration> existingDurations = service.getDurations();
        for(Duration duration : durations){
            if(!existingDurations.contains(duration)){
                existingDurations.add(duration);
            }
        }
        com.marcelo.reservation.model.Service savedService = serviceRepository.save(service);

        return serviceMapper.mapToDto(savedService);
    }

    public ServiceDto patchCategories(ServiceDto serviceDto) {
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
}
