package com.marlonreina.discord.api.infrastructure.config;

import com.marlonreina.discord.api.domain.port.out.JwtPort;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String JWT_COOKIE_NAME = "jwt";
    private static final String WELCOME_IMAGE_PATH_SUFFIX = "/config/welcome/image";

    private final JwtPort jwtPort;

    public JwtFilter(JwtPort jwtPort) {
        super();
        this.jwtPort = jwtPort;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = resolveToken(request);
        boolean welcomeImageUpload = isWelcomeImageUpload(request);

        if (welcomeImageUpload && token == null) {
            LOGGER.warn(
                    "Welcome image upload has no JWT. authorizationHeaderPresent={}, cookiesPresent={}, contentType={}",
                    request.getHeader("Authorization") != null,
                    request.getCookies() != null,
                    request.getContentType()
            );
        }

        if (token != null) {
            try {
                String userId = jwtPort.extractUserId(token);
                authenticate(userId, welcomeImageUpload);

            } catch (RuntimeException e) {
                if (welcomeImageUpload) {
                    LOGGER.warn("Welcome image upload received an invalid JWT: {}", e.getMessage());
                } else {
                    LOGGER.debug("Invalid JWT received", e);
                }
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length());
        }

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (JWT_COOKIE_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

    private void authenticate(String userId, boolean welcomeImageUpload) {
        if (userId == null) {
            return;
        }

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        List.of()
                );

        SecurityContextHolder.getContext().setAuthentication(auth);
        if (welcomeImageUpload) {
            LOGGER.debug("Welcome image upload authenticated for userId={}", userId);
        }
    }

    private boolean isWelcomeImageUpload(HttpServletRequest request) {
        return "PUT".equalsIgnoreCase(request.getMethod())
                && request.getRequestURI().endsWith(WELCOME_IMAGE_PATH_SUFFIX);
    }
}
