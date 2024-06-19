package com.marcelo.reservation.mapper;

import com.marcelo.reservation.dto.member.MemberDto;
import com.marcelo.reservation.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    @Mapping(target="businessId", source="business.id")
    @Mapping(target="userId", source="user.id")
    @Mapping(target="addressId", source="address.id")
    MemberDto mapToDto(Member member);

    Member map(MemberDto memberDto);

    List<MemberDto> mapToDtoList(List<Member> members);
}
