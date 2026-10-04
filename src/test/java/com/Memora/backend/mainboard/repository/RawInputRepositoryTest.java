package com.Memora.backend.mainboard.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.Memora.backend.mainboard.entity.RawInput;
import com.Memora.backend.mainboard.enums.ProcessingStatus;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class RawInputRepositoryTest {

    @Autowired
    private RawInputRepository repository;

    @Test
    void retrievesPendingInputsInCreationOrder() throws InterruptedException {
        RawInput first = repository.saveAndFlush(new RawInput(
                com.Memora.backend.mainboard.enums.RawInputType.TEXT, "first", null));
        Thread.sleep(2);
        RawInput second = repository.saveAndFlush(new RawInput(
                com.Memora.backend.mainboard.enums.RawInputType.TEXT, "second", null));

        List<RawInput> pending = repository.findByProcessingStatusOrderByCreatedAtAsc(ProcessingStatus.PENDING);

        assertThat(pending).containsSubsequence(first, second);
    }
}
