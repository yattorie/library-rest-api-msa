package com.orlovandrei.usersearchservice.dto.mapper;

import com.orlovandrei.usersearchservice.dto.UserDto;
import com.orlovandrei.usersearchservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}