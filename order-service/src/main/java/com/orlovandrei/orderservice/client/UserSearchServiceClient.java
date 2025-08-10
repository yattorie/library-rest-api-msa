package com.orlovandrei.orderservice.client;

import com.orlovandrei.orderservice.dto.internal.InternalUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-search-service", url = "${application.config.users-url}")
public interface UserSearchServiceClient {

    @GetMapping("/internal/username/{username}")
    InternalUserDto getInternalByUsername(@PathVariable("username") String username);
}


