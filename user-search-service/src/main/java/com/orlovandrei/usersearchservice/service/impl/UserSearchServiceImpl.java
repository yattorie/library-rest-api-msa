package com.orlovandrei.usersearchservice.service.impl;

import com.orlovandrei.usersearchservice.dto.user.UserDto;
import com.orlovandrei.usersearchservice.dto.internal.InternalUserDto;
import com.orlovandrei.usersearchservice.dto.mapper.UserMapper;
import com.orlovandrei.usersearchservice.entity.User;
import com.orlovandrei.usersearchservice.exception.UserNotFoundException;
import com.orlovandrei.usersearchservice.repository.UserRepository;
import com.orlovandrei.usersearchservice.service.UserSearchService;
import com.orlovandrei.usersearchservice.util.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSearchServiceImpl implements UserSearchService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> getAll(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size))
                .map(userMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(Messages.USER_NOT_FOUND_ID.getMessage() + id));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(Messages.USER_NOT_FOUND_USERNAME.getMessage() + username));
        return userMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    @Override
    public InternalUserDto getInternalByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(Messages.USER_NOT_FOUND_USERNAME.getMessage() + username));
        return new InternalUserDto(user.getId(), user.getUsername());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(Messages.USER_NOT_FOUND_EMAIL.getMessage() + email));
        return userMapper.toDto(user);
    }
}