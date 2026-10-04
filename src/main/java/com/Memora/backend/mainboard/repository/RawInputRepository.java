package com.Memora.backend.mainboard.repository;

import com.Memora.backend.mainboard.entity.RawInput;
import com.Memora.backend.mainboard.enums.ProcessingStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawInputRepository extends JpaRepository<RawInput, UUID> {

    List<RawInput> findByProcessingStatusOrderByCreatedAtAsc(ProcessingStatus processingStatus);
}
