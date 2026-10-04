package com.Memora.backend.mainboard.service;

import com.Memora.backend.mainboard.entity.RawInput;
import org.springframework.stereotype.Service;

@Service
public class PlaceholderMemoryProcessingService implements MemoryProcessingService {

    @Override
    public void process(RawInput rawInput) {
        throw new UnsupportedOperationException("Memory processing is not configured yet");
    }
}
