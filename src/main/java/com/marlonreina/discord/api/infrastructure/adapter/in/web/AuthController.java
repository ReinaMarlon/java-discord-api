package com.marlonreina.discord.api.infrastructure.adapter.in.web;

import com.marlonreina.discord.api.domain.port.in.AuthUseCase;
import com.marlonreina.discord.api.shared.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthUseCase authUseCase;

    @Value("${spring.discord.redirect-uri}")
    private String redirectUri;

    @Value("${spring.discord.client-id}")
    private String clientId;

    private static final String discordAuthorizeUrl = "https://discord.com/api/oauth2/authorize";

    private static final String scope = "identify%20email%20guilds";

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        String url = discordAuthorizeUrl
                + "?client_id="
                + clientId
                + "&redirect_uri="
                + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)
                + "&response_type=code"
                + "&scope="
                + scope;

        response.sendRedirect(url);
    }

    @GetMapping("/callback")
    public ResponseEntity<AuthResponse> callback(@RequestParam String code) {
        return ResponseEntity.ok(authUseCase.authenticate(code));
    }
}