package com.Memora.backend.mainboard.service;

import java.nio.file.Path;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mainboard.upload")
public record MainBoardProperties(
        Path directory,
        long maxFileSize
) {
}
