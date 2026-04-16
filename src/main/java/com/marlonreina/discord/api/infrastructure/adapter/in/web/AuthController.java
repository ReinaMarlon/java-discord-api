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

    @Value("${spring.frontend.redirect-url}")
    private String frontendRedirectUrl;

    @Value("${spring.frontend.set-secure}")
    private Boolean frontendSetSecure;

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
    public void callback(@RequestParam String code, HttpServletResponse response) throws IOException {

        try {
            String token = authUseCase.authenticate(code);

            jakarta.servlet.http.Cookie cookie = new jakarta.servlet.http.Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(frontendSetSecure);
            cookie.setPath("/");
            cookie.setMaxAge(86400);

            response.addCookie(cookie);

            String redirectUrl = frontendRedirectUrl + token;
            response.sendRedirect(redirectUrl);

        } catch (RuntimeException e) {
            e.printStackTrace();

            String error = e.getMessage();
            String redirectUrl;

            if ("OAUTH_CODE_INVALID".equals(error)) {
                redirectUrl = "http://localhost:5137/auth/error?error=OAUTH_CODE_INVALID";
            } else {
                redirectUrl = "http://localhost:5137/auth/error?error=INTERNAL_ERROR";
            }

            response.sendRedirect(redirectUrl);
        }
    }

}