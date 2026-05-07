package com.marlonreina.discord.api.application.service;

import com.marlonreina.discord.api.domain.model.WelcomeImageUpdate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Locale;

@Service
public class WelcomeImageStorageService {

    private static final String SHA_256 = "SHA-256";

    private final String welcomeImagesStoragePath;
    private final String welcomeImageUrlTemplate;

    public WelcomeImageStorageService(
            @Value("${welcome.images.storage-path:uploads/welcome-images}") String welcomeImagesStoragePath,
            @Value("${spring.frontend.welcome-url-template}") String welcomeImageUrlTemplate
    ) {
        this.welcomeImagesStoragePath = welcomeImagesStoragePath;
        this.welcomeImageUrlTemplate = welcomeImageUrlTemplate;
    }

    public WelcomeImageUpdate store(String guildId, MultipartFile image) {
        validateImageFile(image);

        try {
            byte[] bytes = image.getBytes();
            BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
            if (bufferedImage == null) {
                throw new IllegalArgumentException("WELCOME_IMAGE_INVALID");
            }

            String mimeType = image.getContentType();
            String imageHash = hash(bytes);
            Path imagePath = imagePath(guildId, imageHash, extensionFor(mimeType));
            Path imageDirectory = imagePath.getParent();
            if (imageDirectory == null) {
                throw new IllegalStateException("WELCOME_IMAGE_PATH_INVALID");
            }

            Files.createDirectories(imageDirectory);
            Files.write(imagePath, bytes);

            return new WelcomeImageUpdate(
                    guildId,
                    welcomeImageUrlTemplate.formatted(guildId),
                    image.getOriginalFilename(),
                    bytes,
                    imageHash,
                    mimeType,
                    bufferedImage.getWidth(),
                    bufferedImage.getHeight()
            );
        } catch (IOException e) {
            throw new RuntimeException("WELCOME_IMAGE_SAVE_FAILED", e);
        }
    }

    private void validateImageFile(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("WELCOME_IMAGE_REQUIRED");
        }
        String contentType = image.getContentType();
        if (contentType == null || !contentType.toLowerCase(Locale.ROOT).startsWith("image/")) {
            throw new IllegalArgumentException("WELCOME_IMAGE_MIME_INVALID");
        }
    }

    private String hash(byte[] bytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance(SHA_256);
            return HexFormat.of().formatHex(digest.digest(bytes));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA_256_UNAVAILABLE", e);
        }
    }

    private String extensionFor(String mimeType) {
        if ("image/jpeg".equalsIgnoreCase(mimeType)) {
            return "jpg";
        }
        if ("image/webp".equalsIgnoreCase(mimeType)) {
            return "webp";
        }
        if ("image/gif".equalsIgnoreCase(mimeType)) {
            return "gif";
        }

        return "png";
    }

    private Path imagePath(String guildId, String imageHash, String extension) {
        String safeGuildId = guildId.replaceAll("[^a-zA-Z0-9_-]", "_");
        String fileName = imageHash + "." + extension;

        return Path.of(welcomeImagesStoragePath, safeGuildId, fileName);
    }
}
