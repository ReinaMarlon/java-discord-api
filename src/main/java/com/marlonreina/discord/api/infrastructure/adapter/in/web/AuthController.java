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
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthUseCase authUseCase;

    @Value("${spring.discord.redirect-uri}")
    private String redirectUri;

    @Value("${spring.discord.client-id}")
    private String clientId;

    private static final String discordAuthorizeUrl = "https://discord.com/api/oauth2/authorize";

    @Value("${discord.oauth.scopes}")
    private String scope;

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        String encodedScope = URLEncoder.encode(scope, StandardCharsets.UTF_8);
        String url = discordAuthorizeUrl
                + "?client_id="
                + clientId
                + "&redirect_uri="
                + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)
                + "&response_type=code"
                + "&scope="
                + encodedScope;

        response.sendRedirect(url);
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam String code) {

        try {
            String token = authUseCase.authenticate(code);
            return ResponseEntity.ok(new AuthResponse(token));

        } catch (RuntimeException e) {
            e.printStackTrace();
            if (e.getMessage().equals("OAUTH_CODE_INVALID")) {
                return ResponseEntity
                        .badRequest()
                        .body(Map.of(
                                "error", "OAUTH_CODE_INVALID",
                                "message", "El login expiró o ya fue usado. Intenta de nuevo."
                        ));
            }

            return ResponseEntity
                    .status(500)
                    .body(Map.of(
                            "error", "INTERNAL_ERROR",
                            "message", e.getMessage()
                    ));
        }
    }
}