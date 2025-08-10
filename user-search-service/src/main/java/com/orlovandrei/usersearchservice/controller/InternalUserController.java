package com.orlovandrei.usersearchservice.controller;

import com.orlovandrei.usersearchservice.dto.internal.InternalUserDto;
import com.orlovandrei.usersearchservice.service.UserSearchService;
import com.orlovandrei.usersearchservice.util.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class InternalUserController {

    private final UserSearchService userSearchService;

    @GetMapping("internal/username/{username}")
    public ResponseEntity<InternalUserDto> getInternalByUsername(
            @PathVariable String username,
            @RequestHeader(value = "X-Internal-Call", required = false) String internalCallHeader
    ) {
        if (!"order-service".equals(internalCallHeader)) {
            throw new AccessDeniedException(Messages.ACCESS_DENIED_INTERNAL_GET.getMessage());
        }
        return ResponseEntity.ok(userSearchService.getInternalByUsername(username));
    }
}