package com.orlovandrei.usersearchservice.controller;

import com.orlovandrei.usersearchservice.dto.UserDto;
import com.orlovandrei.usersearchservice.service.UserSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User Search Controller", description = "Operations for searching and retrieving users")
public class UserSearchController {

    private final UserSearchService userSearchService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userSearchService.getById(id));
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get user by username")
    public ResponseEntity<UserDto> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userSearchService.getByUsername(username));
    }

    @GetMapping("email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get user by email")
    public ResponseEntity<UserDto> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userSearchService.getByEmail(email));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users")
    public ResponseEntity<Page<UserDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(userSearchService.getAll(page, size));
    }
}