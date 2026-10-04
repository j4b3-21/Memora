package com.Memora.backend.mainboard.service;

import com.Memora.backend.mainboard.dto.RawInputResponse;
import com.Memora.backend.mainboard.entity.RawInput;
import com.Memora.backend.mainboard.enums.RawInputType;
import com.Memora.backend.mainboard.repository.RawInputRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MainBoardService {

    private final RawInputRepository rawInputRepository;
    private final MainBoardProperties properties;

    public MainBoardService(RawInputRepository rawInputRepository, MainBoardProperties properties) {
        this.rawInputRepository = rawInputRepository;
        this.properties = properties;
    }

    @Transactional
    public RawInputResponse createTextInput(String text) {
        if (text == null || text.isBlank() || text.length() > 10000) {
            throw new InvalidRawInputException("Text must contain between 1 and 10000 characters");
        }
        RawInput rawInput = new RawInput(RawInputType.TEXT, text, null);
        return RawInputResponse.from(rawInputRepository.save(rawInput));
    }

    @Transactional
    public RawInputResponse createImageInput(MultipartFile image) {
        validateImage(image);

        Path storedFile = storeImage(image);
        try {
            RawInput rawInput = new RawInput(RawInputType.IMAGE, null, storedFile.toString());
            return RawInputResponse.from(rawInputRepository.save(rawInput));
        } catch (RuntimeException exception) {
            deleteStoredFile(storedFile);
            throw exception;
        }
    }

    private void validateImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new InvalidRawInputException("Image file is required");
        }
        if (image.getContentType() == null || !image.getContentType().toLowerCase(Locale.ROOT).startsWith("image/")) {
            throw new InvalidRawInputException("Uploaded file must be an image");
        }
        if (image.getSize() > properties.maxFileSize()) {
            throw new InvalidRawInputException("Image exceeds the configured size limit");
        }
    }

    private Path storeImage(MultipartFile image) {
        String extension = extensionFor(image.getContentType());
        Path storedFile = properties.directory().resolve(UUID.randomUUID() + extension).normalize();
        if (!storedFile.startsWith(properties.directory().normalize())) {
            throw new InvalidRawInputException("Invalid upload path");
        }
        try {
            Files.createDirectories(properties.directory());
            image.transferTo(storedFile);
            return storedFile;
        } catch (IOException exception) {
            throw new InvalidRawInputException("Unable to store image", exception);
        }
    }

    private String extensionFor(String contentType) {
        return switch (contentType.toLowerCase(Locale.ROOT)) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/gif" -> ".gif";
            case "image/webp" -> ".webp";
            default -> ".img";
        };
    }

    private void deleteStoredFile(Path storedFile) {
        try {
            Files.deleteIfExists(storedFile);
        } catch (IOException exception) {
            throw new InvalidRawInputException("Unable to clean up stored image", exception);
        }
    }
}
