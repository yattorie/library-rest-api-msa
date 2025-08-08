package com.orlovandrei.usersearchservice.service;

import com.orlovandrei.usersearchservice.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface UserSearchService {
    @Transactional(readOnly = true)
    Page<UserDto> getAll(int page, int size);

    @Transactional(readOnly = true)
    UserDto getById(Long id);

    @Transactional(readOnly = true)
    UserDto getByUsername(String username);

    @Transactional(readOnly = true)
    UserDto getByEmail(String email);
}