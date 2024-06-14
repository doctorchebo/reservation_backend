package com.marcelo.reservation.controller;

import com.marcelo.reservation.dto.member.MemberDto;
import com.marcelo.reservation.dto.member.PatchMemberFirstNameRequest;
import com.marcelo.reservation.dto.member.PatchMemberLastNameRequest;
import com.marcelo.reservation.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("getAllByBusinessId/{businessId}")
    public ResponseEntity<List<MemberDto>> getAllByBusinessId(@PathVariable("businessId") Long businessId){
        return ResponseEntity.ok(memberService.getAllByBusinessId(businessId));
    }

    @GetMapping("getAllActiveByBusinessId/{businessId}")
    public ResponseEntity<List<MemberDto>> getAllActiveMembersByBusinessId(@PathVariable("businessId") Long businessId){
        return ResponseEntity.ok(memberService.getAllActiveMembersByBusinessId(businessId));
    }

    @GetMapping("getById/{memberId}")
    public ResponseEntity<MemberDto> getMemberById(@PathVariable() Long memberId){
        return ResponseEntity.ok(memberService.getMemberById(memberId));
    }

    @GetMapping("getByUserId/{userId}")
    public ResponseEntity<MemberDto> getMemberByUserId(@PathVariable() Long userId){
        return ResponseEntity.ok(memberService.getMemberByUserId(userId));
    }

    @PostMapping("create")
    public ResponseEntity<MemberDto> createMember(@RequestBody MemberDto memberDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(memberDto));
    }
    @PatchMapping("patchFirstName")
    public ResponseEntity<MemberDto> patchMemberFirstName(@RequestBody PatchMemberFirstNameRequest patchMemberFirstNameRequest){
        return ResponseEntity.ok(memberService.patchMemberFirstName(patchMemberFirstNameRequest));
    }
    @PatchMapping("patchLastName")
    public ResponseEntity<MemberDto> patchMemberLastName(@RequestBody PatchMemberLastNameRequest patchMemberLastNameRequest){
        return ResponseEntity.ok(memberService.patchMemberLastName(patchMemberLastNameRequest));
    }
}
