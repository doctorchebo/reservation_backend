package com.marcelo.reservation.controller;

import com.marcelo.reservation.dto.member.*;
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


    @GetMapping("getAll")
    public ResponseEntity<List<MemberDto>> getAllMembers(){
        return ResponseEntity.ok(memberService.getAllMembers());
    }
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
    public ResponseEntity<MemberDto> createMember(@RequestBody MemberCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(request));
    }
    @DeleteMapping("delete/{memberId}")
    public ResponseEntity<MemberDto> deleteMemberById(@PathVariable Long memberId){
        return ResponseEntity.ok(memberService.deleteMemberById(memberId));
    }
    @PatchMapping("patchFirstName")
    public ResponseEntity<MemberDto> patchMemberFirstName(@RequestBody MemberPatchFirstNameRequest memberPatchFirstNameRequest){
        return ResponseEntity.ok(memberService.patchMemberFirstName(memberPatchFirstNameRequest));
    }
    @PatchMapping("patchLastName")
    public ResponseEntity<MemberDto> patchMemberLastName(@RequestBody MemberPatchLastNameRequest memberPatchLastNameRequest){
        return ResponseEntity.ok(memberService.patchMemberLastName(memberPatchLastNameRequest));
    }

    @PatchMapping("patchPhoneNumber")
    public ResponseEntity<MemberDto> patchMemberPhoneNumber(@RequestBody MemberPatchPhoneNumberRequest request){
        return ResponseEntity.ok(memberService.patchMemberPhoneNumber(request));
    }

    @PatchMapping("patchTitle")
    public ResponseEntity<MemberDto> patchMemberTitle(@RequestBody MemberPatchTitleRequest request){
        return ResponseEntity.ok(memberService.patchMemberTitle(request));
    }

    @PatchMapping("patchAddress")
    public ResponseEntity<MemberDto> patchMemberAddress(@RequestBody MemberPatchAddressRequest request){
        return ResponseEntity.ok(memberService.patchMemberAddress(request));
    }
    @PatchMapping("patchIsActive")
    public ResponseEntity<MemberDto> patchMemberIsActive(@RequestBody MemberPatchIsActiveRequest request){
        return ResponseEntity.ok(memberService.patchMemberIsActive(request));
    }
}
