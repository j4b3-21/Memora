package com.Memora.backend.mainboard.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.Memora.backend.mainboard.dto.RawInputResponse;
import com.Memora.backend.mainboard.enums.ProcessingStatus;
import com.Memora.backend.mainboard.enums.RawInputType;
import com.Memora.backend.mainboard.service.MainBoardService;
import com.Memora.backend.mainboard.service.InvalidRawInputException;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MainBoardController.class)
@Import(MainBoardExceptionHandler.class)
class MainBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MainBoardService mainBoardService;

    @Test
    void createsTextInputWithCreatedStatus() throws Exception {
        when(mainBoardService.createTextInput(any())).thenReturn(response(RawInputType.TEXT));

        mockMvc.perform(post("/api/mainboard/text")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\":\"Dinner tomorrow\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void rejectsEmptyText() throws Exception {
        mockMvc.perform(post("/api/mainboard/text")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\":\"  \"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createsImageInputWithCreatedStatus() throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "photo.png", "image/png", new byte[]{1});
        when(mainBoardService.createImageInput(any())).thenReturn(response(RawInputType.IMAGE));

        mockMvc.perform(multipart("/api/mainboard/image").file(image))
                .andExpect(status().isCreated());
    }

    @Test
    void rejectsNonImageUpload() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image", "notes.txt", "text/plain", new byte[]{1});
        when(mainBoardService.createImageInput(any())).thenThrow(new InvalidRawInputException("Uploaded file must be an image"));

        mockMvc.perform(multipart("/api/mainboard/image").file(file))
                .andExpect(status().isBadRequest());
    }

    private RawInputResponse response(RawInputType type) {
        return new RawInputResponse(UUID.randomUUID(), type, Instant.now(), ProcessingStatus.PENDING);
    }
}
