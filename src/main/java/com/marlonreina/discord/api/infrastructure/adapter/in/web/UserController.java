package com.marlonreina.discord.api.infrastructure.adapter.in.web;

import com.marlonreina.discord.api.domain.port.in.UserUseCase;
import com.marlonreina.discord.api.shared.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(Authentication authentication) {

        String discordId = (String) authentication.getPrincipal();

        return ResponseEntity.ok(userUseCase.getUserData(discordId));
    }
}