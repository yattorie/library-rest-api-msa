package com.orlovandrei.usersearchservice.dto.mapper;

import com.orlovandrei.usersearchservice.dto.user.UserDto;
import com.orlovandrei.usersearchservice.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}