package com.marcelo.reservation.service;

import com.marcelo.reservation.dto.duration.DurationDto;
import com.marcelo.reservation.dto.duration.DurationRequest;
import com.marcelo.reservation.exception.NotFoundException;
import com.marcelo.reservation.mapper.DurationMapper;
import com.marcelo.reservation.model.Business;
import com.marcelo.reservation.model.Duration;
import com.marcelo.reservation.repository.BusinessRepository;
import com.marcelo.reservation.repository.DurationRepository;
import com.marcelo.reservation.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DurationService {
    private final ServiceRepository serviceRepository;
    private final BusinessRepository businessRepository;

    private static Logger logger = LoggerFactory.getLogger(DurationService.class);

    private final DurationRepository durationRepository;

    private final DurationMapper durationMapper;

    public List<DurationDto> getAllDurations() {
        return durationMapper.mapToDtoList(durationRepository.findAll());
    }
    public DurationDto getAllByServiceIdAndBusinessId(UUID serviceId, Long businessId) {
        Duration duration = durationRepository.findAllByServicesIdAndBusinessId(serviceId, businessId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Duration for service with id %s and business id %s not found", serviceId, businessId)));
        return durationMapper.mapToDto(duration);
    }

    public List<DurationDto> getAllDurationsByBusinessId(Long businessId) {
        List<Duration> durations = durationRepository.findAllByBusinessId(businessId);
        return durationMapper.mapToDtoList(durations);
    }

    public DurationDto createDuration(DurationRequest durationRequest) {
        Business business = businessRepository.findById(durationRequest.getBusinessId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Business with id %s not found", durationRequest.getBusinessId())));

        Duration duration = Duration.builder()
                .duration(durationRequest.getDuration())
                .business(business)
                .services(new ArrayList<com.marcelo.reservation.model.Service>())
                .created(Instant.now())
                .build();

        return durationMapper.mapToDto(durationRepository.save(duration));
    }

    public DurationDto deleteDuration(Long durationId) {
        Duration duration = durationRepository.findById(durationId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Duration with id %s not found", durationId)));

        durationRepository.delete(duration);

        return durationMapper.mapToDto(duration);
    }
}
