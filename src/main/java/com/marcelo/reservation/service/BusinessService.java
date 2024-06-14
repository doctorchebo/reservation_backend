package com.marcelo.reservation.service;

import com.marcelo.reservation.dto.business.*;
import com.marcelo.reservation.exception.NotFoundException;
import com.marcelo.reservation.mapper.BusinessMapper;
import com.marcelo.reservation.model.*;
import com.marcelo.reservation.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BusinessService {
    private final MemberRepository memberRepository;
    private static Logger logger = LoggerFactory.getLogger(BusinessService.class);
    private final BusinessRepository businessRepository;
    private final BusinessMapper businessMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    private final DurationRepository durationRepository;

    private final S3Service s3Service;

    private final ReservationRepository reservationRepository;

    private final ImageUploadService imageUploadService;

    private final ServiceRepository serviceRepository;
    public List<BusinessResponse> getAllBusinesses(){
        logger.info("Getting all businesses");
        List<Business> businesses = businessRepository.findAll();
        return businessMapper.mapToResponseList(presignImageUrls(businesses));
    }

    public BusinessResponse getBusinessById(Long businessId) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Business with id %s not found", businessId)));
        return businessMapper.mapToResponse(presignImageUrls(business));
    }
    @Transactional
    public BusinessResponse createBusiness(BusinessRequest businessRequest) {
        User user = userRepository.findById(businessRequest.getOwnerId())
                .orElseThrow(() ->
                        new NotFoundException(String.format("User with id %s not found", businessRequest.getOwnerId())));

        List<Category> categories = categoryRepository.findAllById(businessRequest.getCategoryIds());

        Business business = Business.builder()
                .name(businessRequest.getName())
                .owner(user)
                .members(new ArrayList<Member>())
                .addresses(new ArrayList<Address>())
                .categories(categories)
                .services(new ArrayList<com.marcelo.reservation.model.Service>())
                .images(new ArrayList<Image>())
                .created(Instant.now())
                .build();

        Business savedBusiness = businessRepository.save(business);
        return businessMapper.mapToResponse(savedBusiness);
    }

    public BusinessResponse updateBusiness(BusinessDto businessDto) {
        List<Category> categories = categoryRepository.findAllById(businessDto.getCategoryIds());
        Business business = businessMapper.map(businessDto);
        business.setCategories(categories);
        return businessMapper.mapToResponse(businessRepository.save(business));
    }

    public BusinessResponse patchBusinessName(BusinessPatchNameRequest businessPatchNameRequest) {
        Business business = businessRepository.findById(businessPatchNameRequest.getBusinessId())
                .orElseThrow(() ->
                        new NotFoundException(String.format("Business with id %s not found", businessPatchNameRequest.getBusinessId())));
        business.setName(businessPatchNameRequest.getName());
        businessRepository.save(business);
        return businessMapper.mapToResponse(business);
    }

    @Transactional
    public BusinessResponse patchBusinessActiveMembers(BusinessPatchMembersRequest request) {
        Business business = businessRepository.findById(request.getBusinessId())
                .orElseThrow(() ->
                        new NotFoundException(String.format("Business with id %s not found", request.getBusinessId())));
        List<Member> activeMembers = memberRepository.findAllById(request.getMemberIds());
        // Fetch current members to handle reservations
        List<Member> currentMembers = business.getMembers();
        for(Member member : currentMembers){
            if(!activeMembers.contains(member)){
                member.setActive(false);
            } else {
                member.setActive(true);
            }
            memberRepository.save(member);
        }

        business.getServices().clear();
        business.getMembers().addAll(currentMembers);
        business.setModified(Instant.now());
        businessRepository.save(business);
        return businessMapper.mapToResponse(business);
    }

    public List<BusinessResponse> getAllBusinessesByCategoryId(Long categoryId) {
        List<Business> businesses = businessRepository.findByCategoriesId(categoryId);
        return businessMapper.mapToResponseList(presignImageUrls(businesses));
    }
    public List<BusinessResponse> getAllBusinessesByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with owner id %s not found", userId)));

        List<Business> businesses = businessRepository.findByOwnerId(user.getId());
        return businessMapper.mapToResponseList(businesses);
    }

    public BusinessResponse patchBusinessCategories(BusinessPatchCategoriesRequest request) {
        Business business = businessRepository.findById(request.getBusinessId())
                .orElseThrow(() ->
                        new NotFoundException(String.format("Business with id %s not found", request.getBusinessId())));

        List<Category> newCategories = categoryRepository.findAllById(request.getCategoryIds());
        business.getCategories().clear();
        business.getCategories().addAll(newCategories);
        Business saveBusiness = businessRepository.save(business);
        return businessMapper.mapToResponse(saveBusiness);
    }


    @Transactional
    public BusinessResponse updateBusinessImages(Long businessId, List<MultipartFile> images) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Business with id %s not found", businessId)));
        // upload images to AWS S3
        List<Image> newImages = new ArrayList<>(images.size());
        for(MultipartFile image : images){
            String imageUrl = imageUploadService.uploadFile(String.format("media/images/business/%s/", businessId), image);
            Image img = Image.builder()
                    .url(imageUrl)
                    .business(business)
                    .created(Instant.now())
                    .build();
            newImages.add(img);
        }
        // delete old images
        imageUploadService.deleteFileFromS3Bucket(String.format("media/images/business/%s/", businessId));

        business.getImages().clear();
        business.getImages().addAll(newImages);
        Business savedBusiness = businessRepository.save(business);
        return businessMapper.mapToResponse(savedBusiness);
    }

    private List<Business> presignImageUrls(List<Business> businesses){
        // getting presigned urls for businesses images
        for(Business business: businesses){
            if(!business.getImages().isEmpty()){
                for(Image image: business.getImages()){
                    String imageUrl = s3Service.generatePresignedUrl(image.getUrl());
                    image.setUrl(imageUrl);
                }
            }
        }
        return businesses;
    }

    private Business presignImageUrls(Business business){
        // getting presigned urls for business images
        if(!business.getImages().isEmpty()){
            for(Image image: business.getImages()){
                String imageUrl = s3Service.generatePresignedUrl(image.getUrl());
                image.setUrl(imageUrl);
            }
        }
        return business;
    }

    public List<BusinessResponse> getAllAvailableByServiceId(AvailableRequest availableRequest) {
        // get service duration
//        Duration duration = durationRepository.findByServicesId()
//                .orElseThrow(() ->
//                        new NotFoundException(String.format("User with id %s not found", availableRequest.getServiceId())));
        return businessMapper.mapToResponseList(businessRepository.findAll());
    }

    public List<BusinessResponse> getAllByServiceIdAndStartDate(UUID serviceId, Instant startDate) {
        com.marcelo.reservation.model.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Service with id %s not found", serviceId)));
        List<Duration> durations = service.getDurations();
        if(durations.isEmpty()){
            throw new NotFoundException(String.format("Service with id %s doesn't have durations", serviceId));
        }
        List<java.time.Duration> durationsObj = durations.stream().map(Duration::getDuration).collect(Collectors.toList());
        java.time.Duration maxDuration = Collections.max(durationsObj);
        Instant endDate = startDate.plus(maxDuration);

        List<Business> businesses = businessRepository.findAllByServiceIdAndStartDate(serviceId, startDate, endDate);
        logger.info("Found {} available businesses", businesses.size());
        return businessMapper.mapToResponseList(presignImageUrls(businesses));
    }

    @Transactional
    public BusinessResponse updateServices(BusinessPatchRequest businessPatchRequest) {
        Business business = businessRepository.findById(businessPatchRequest.getBusinessId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Business with id %s not found", businessPatchRequest.getBusinessId())));

        List<com.marcelo.reservation.model.Service> existingServices = business.getServices();

        List<com.marcelo.reservation.model.Service> services = serviceRepository.findAllById(businessPatchRequest.getServiceIds());
        for(com.marcelo.reservation.model.Service service : services){
            if(!existingServices.contains(service)){
                existingServices.add(service);
            }
        }
        Business savedBusiness = businessRepository.save(business);
        return businessMapper.mapToResponse(savedBusiness);
    }

    public BusinessResponse deleteBusiness(Long businessId) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Business with id %s not found", businessId)));

        businessRepository.delete(business);

        return businessMapper.mapToResponse(business);
    }
}
