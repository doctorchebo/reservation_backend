package com.marcelo.reservation.mapper;

import com.marcelo.reservation.dto.user.UserDto;
import com.marcelo.reservation.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto mapToDto(User user);
    List<UserDto> mapToDtoList(List<User> users);

    User map(UserDto userDto);

}
