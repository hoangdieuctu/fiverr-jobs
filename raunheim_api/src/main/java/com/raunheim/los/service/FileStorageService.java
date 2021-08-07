package com.raunheim.los.service;

import com.raunheim.los.exception.LosException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class FileStorageService {

    public void store(String fileName, String content) {
        try {
            FileUtils.writeStringToFile(new File(fileName), content, "UTF-8");
        } catch (IOException e) {
            throw new LosException("Cannot save data to file.", e);
        }
    }

}
