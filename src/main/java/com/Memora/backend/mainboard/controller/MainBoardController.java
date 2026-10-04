package com.Memora.backend.mainboard.controller;

import com.Memora.backend.mainboard.dto.RawInputResponse;
import com.Memora.backend.mainboard.dto.TextInputRequest;
import com.Memora.backend.mainboard.service.MainBoardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/mainboard")
public class MainBoardController {

    private final MainBoardService mainBoardService;

    public MainBoardController(MainBoardService mainBoardService) {
        this.mainBoardService = mainBoardService;
    }

    @PostMapping("/text")
    @ResponseStatus(HttpStatus.CREATED)
    public RawInputResponse createText(@Valid @RequestBody TextInputRequest request) {
        return mainBoardService.createTextInput(request.text());
    }

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RawInputResponse createImage(@RequestPart("image") MultipartFile image) {
        return mainBoardService.createImageInput(image);
    }
}
