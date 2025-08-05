package com.orlovandrei.authservice.service.impl;

import com.orlovandrei.authservice.dto.auth.LoginRequest;
import com.orlovandrei.authservice.dto.auth.RefreshTokenRequest;
import com.orlovandrei.authservice.dto.auth.RegisterRequest;
import com.orlovandrei.authservice.dto.auth.TokenPair;
import com.orlovandrei.authservice.entity.User;
import com.orlovandrei.authservice.entity.enums.Role;
import com.orlovandrei.authservice.exception.EmailAlreadyExistsException;
import com.orlovandrei.authservice.exception.UserAlreadyExistsException;
import com.orlovandrei.authservice.repository.UserRepository;
import com.orlovandrei.authservice.security.JwtService;
import com.orlovandrei.authservice.service.AuthService;
import com.orlovandrei.authservice.util.Messages;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Transactional
    public void register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UserAlreadyExistsException(Messages.USERNAME.getMessage() + Messages.IS_ALREADY_TAKEN.getMessage());
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EmailAlreadyExistsException(Messages.EMAIL.getMessage() + Messages.IS_ALREADY_TAKEN.getMessage());
        }

        User user = User
                .builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);
    }

    public TokenPair login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtService.generateTokenPair(authentication);
    }

    public TokenPair refreshToken(@Valid
                                  RefreshTokenRequest request) {

        String refreshToken = request.getRefreshToken();
        if (!jwtService.isRefreshToken(refreshToken)) {
            throw new IllegalArgumentException(Messages.INVALID_REFRESH_TOKEN.getMessage());
        }

        String user = jwtService.extractUsernameFromToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user);

        if (userDetails == null) {
            throw new IllegalArgumentException(Messages.USER_NOT_FOUND.getMessage());
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        String accessToken = jwtService.generateAccessToken(authentication);
        return new TokenPair(accessToken, refreshToken);
    }
}