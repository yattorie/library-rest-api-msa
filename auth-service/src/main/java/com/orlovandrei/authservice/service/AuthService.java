package com.orlovandrei.authservice.service;

import com.orlovandrei.authservice.dto.auth.LoginRequest;
import com.orlovandrei.authservice.dto.auth.RefreshTokenRequest;
import com.orlovandrei.authservice.dto.auth.RegisterRequest;
import com.orlovandrei.authservice.dto.auth.TokenPair;
import jakarta.transaction.Transactional;

public interface AuthService {
    @Transactional
    void register(RegisterRequest registerRequest);

    TokenPair login(LoginRequest loginRequest);

    TokenPair refreshToken(RefreshTokenRequest request);
}
