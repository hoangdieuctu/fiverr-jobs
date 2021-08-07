package com.raunheim.los.service;

import com.raunheim.los.dto.request.Los;
import org.springframework.web.multipart.MultipartFile;

public interface FileProcessingService {
    <T extends Los> T read(MultipartFile file, Class<T> clazz);
}
