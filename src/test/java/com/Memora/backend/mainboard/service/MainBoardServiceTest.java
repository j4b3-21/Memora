package com.Memora.backend.mainboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.Memora.backend.mainboard.dto.RawInputResponse;
import com.Memora.backend.mainboard.entity.RawInput;
import com.Memora.backend.mainboard.enums.ProcessingStatus;
import com.Memora.backend.mainboard.enums.RawInputType;
import com.Memora.backend.mainboard.repository.RawInputRepository;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

class MainBoardServiceTest {

    @TempDir
    Path uploadDirectory;

    private final RawInputRepository repository = Mockito.mock(RawInputRepository.class);
    private MainBoardService service;

    @BeforeEach
    void setUp() {
        service = new MainBoardService(repository, new MainBoardProperties(uploadDirectory, 1024 * 1024));
    }

    @Test
    void createsPendingTextInputWithGeneratedIdAndTimestamp() {
        when(repository.save(any(RawInput.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RawInputResponse response = service.createTextInput("A private memory");

        assertThat(response.id()).isNotNull();
        assertThat(response.type()).isEqualTo(RawInputType.TEXT);
        assertThat(response.createdAt()).isNotNull();
        assertThat(response.processingStatus()).isEqualTo(ProcessingStatus.PENDING);
    }

    @Test
    void rejectsBlankImageContentType() {
        MockMultipartFile file = new MockMultipartFile("image", "not-image.txt", "text/plain", "data".getBytes());

        assertThatThrownBy(() -> service.createImageInput(file))
                .isInstanceOf(InvalidRawInputException.class);
    }

    @Test
    void rejectsBlankText() {
        assertThatThrownBy(() -> service.createTextInput("  "))
                .isInstanceOf(InvalidRawInputException.class);
    }

    @Test
    void storesImageWithGeneratedSafeFilename() throws Exception {
        when(repository.save(any(RawInput.class))).thenAnswer(invocation -> invocation.getArgument(0));
        MockMultipartFile file = new MockMultipartFile("image", "../../unsafe.png", "image/png", new byte[]{1, 2, 3});

        service.createImageInput(file);

        ArgumentCaptor<RawInput> captor = ArgumentCaptor.forClass(RawInput.class);
        Mockito.verify(repository).save(captor.capture());
        Path storedPath = Path.of(captor.getValue().getImagePath());
        assertThat(storedPath.getParent()).isEqualTo(uploadDirectory);
        assertThat(storedPath.getFileName().toString()).matches("[0-9a-f-]{36}\\.png");
        assertThat(Files.exists(storedPath)).isTrue();
    }
}
