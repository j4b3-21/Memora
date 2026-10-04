package com.Memora.backend.mainboard.dto;

import com.Memora.backend.mainboard.entity.RawInput;
import com.Memora.backend.mainboard.enums.ProcessingStatus;
import com.Memora.backend.mainboard.enums.RawInputType;
import java.time.Instant;
import java.util.UUID;

public record RawInputResponse(
        UUID id,
        RawInputType type,
        Instant createdAt,
        ProcessingStatus processingStatus
) {

    public static RawInputResponse from(RawInput rawInput) {
        return new RawInputResponse(
                rawInput.getId(),
                rawInput.getType(),
                rawInput.getCreatedAt(),
                rawInput.getProcessingStatus()
        );
    }
}
