package com.marcelo.reservation.service;

import com.marcelo.reservation.dto.member.*;
import com.marcelo.reservation.exception.NotFoundException;
import com.marcelo.reservation.mapper.MemberMapper;
import com.marcelo.reservation.model.Address;
import com.marcelo.reservation.model.Business;
import com.marcelo.reservation.model.Member;
import com.marcelo.reservation.model.User;
import com.marcelo.reservation.repository.AddressRepository;
import com.marcelo.reservation.repository.BusinessRepository;
import com.marcelo.reservation.repository.MemberRepository;
import com.marcelo.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    private static Logger logger = LoggerFactory.getLogger(MemberService.class);
    private final MemberRepository memberRepository;

    private final BusinessRepository businessRepository;
    private final MemberMapper memberMapper;
    public List<MemberDto> getAllByBusinessId(Long businessId) {
        logger.info("Getting all members for business with id {}", businessId);
        List<Member> members = memberRepository.findByBusinessId(businessId);
        return memberMapper.mapToDtoList(members);
    }

    public List<MemberDto> getAllActiveMembersByBusinessId(Long businessId) {
        logger.info("Getting all active members for business with id {}", businessId);
        List<Member> members = memberRepository.findByBusinessIdAndIsActiveTrue(businessId);
        return memberMapper.mapToDtoList(members);
    }

    public MemberDto createMember(MemberDto memberDto) {
        User user = userRepository.findById(memberDto.getUserId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with id %s not found", memberDto.getUserId())));

        Business business = businessRepository.findById(memberDto.getBusinessId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Business with id %s not found", memberDto.getBusinessId())));

        Address address = addressRepository.findById(memberDto.getAddressId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Address with id %s not found", memberDto.getAddressId())));

        Member member = Member.builder()
                .title(memberDto.getTitle())
                .firstName(memberDto.getFirstName())
                .lastName(memberDto.getLastName())
                .phoneNumber(memberDto.getPhoneNumber())
                .isActive(memberDto.isActive())
                .user(user)
                .business(business)
                .address(address)
                .created(Instant.now())
                .build();

        return memberMapper.mapToDto(memberRepository.save(member));
    }

    public MemberDto getMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Member with id %s not found", memberId)));
        return memberMapper.mapToDto(member);
    }

    public MemberDto getMemberByUserId(Long userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Member with user id %s not found", userId)));
        return memberMapper.mapToDto(member);
    }

    public MemberDto patchMemberFirstName(MemberPatchFirstNameRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Member with id %s not found", request.getMemberId())));
        member.setFirstName(request.getFirstName());
        return memberMapper.mapToDto(memberRepository.save(member));
    }

    public MemberDto patchMemberLastName(MemberPatchLastNameRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Member with id %s not found", request.getMemberId())));
        member.setLastName(request.getLastName());
        return memberMapper.mapToDto(memberRepository.save(member));
    }

    public MemberDto patchMemberPhoneNumber(MemberPatchPhoneNumberRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Member with id %s not found", request.getMemberId())));
        member.setPhoneNumber(request.getPhoneNumber());
        return memberMapper.mapToDto(memberRepository.save(member));
    }

    public MemberDto patchMemberTitle(MemberPatchTitleRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Member with id %s not found", request.getMemberId())));
        member.setTitle(request.getTitle());
        return memberMapper.mapToDto(memberRepository.save(member));
    }

    public MemberDto patchMemberAddress(MemberPatchAddressRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Member with id %s not found", request.getMemberId())));
        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Address with id %s not found", request.getAddressId())));
        member.setAddress(address);
        return memberMapper.mapToDto(memberRepository.save(member));
    }
}
