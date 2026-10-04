package com.Memora.backend.mainboard.entity;

import com.Memora.backend.mainboard.enums.ProcessingStatus;
import com.Memora.backend.mainboard.enums.RawInputType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "raw_inputs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RawInput {

    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(nullable = false, updatable = false, length = 36)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private RawInputType type;

    @Column(name = "text_content", columnDefinition = "TEXT")
    private String textContent;

    @Column(name = "image_path", columnDefinition = "TEXT")
    private String imagePath;

    @Convert(converter = InstantStringConverter.class)
    @Column(nullable = false, columnDefinition = "TEXT")
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private ProcessingStatus processingStatus;

    public RawInput(RawInputType type, String textContent, String imagePath) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.textContent = textContent;
        this.imagePath = imagePath;
        this.createdAt = Instant.now();
        this.processingStatus = ProcessingStatus.PENDING;
    }

    @PrePersist
    void initializeCreatedAt() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
