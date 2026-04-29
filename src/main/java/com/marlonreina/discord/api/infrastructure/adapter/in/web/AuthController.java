package com.marlonreina.discord.api.infrastructure.adapter.in.web;

import com.marlonreina.discord.api.domain.port.in.AuthUseCase;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private static final String DISCORD_AUTHORIZE_URL = "https://discord.com/api/oauth2/authorize";
    private static final String OAUTH_CODE_INVALID = "OAUTH_CODE_INVALID";
    private static final int COOKIE_MAX_AGE_SECONDS = 86400;

    private final AuthUseCase authUseCase;

    @Value("${spring.discord.redirect-uri}")
    private String redirectUri;

    @Value("${spring.discord.client-id}")
    private String clientId;

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
        String url = DISCORD_AUTHORIZE_URL
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

            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(frontendSetSecure);
            cookie.setPath("/");
            cookie.setMaxAge(COOKIE_MAX_AGE_SECONDS);

            response.addCookie(cookie);

            String redirectUrl = frontendRedirectUrl + token;
            response.sendRedirect(redirectUrl);

        } catch (RuntimeException e) {
            LOGGER.warn("OAuth callback failed", e);

            String error = e.getMessage();
            String redirectUrl;

            if (OAUTH_CODE_INVALID.equals(error)) {
                redirectUrl = "http://localhost:5137/auth/error?error=OAUTH_CODE_INVALID";
            } else {
                redirectUrl = "http://localhost:5137/auth/error?error=INTERNAL_ERROR";
            }

            response.sendRedirect(redirectUrl);
        }
    }

}
