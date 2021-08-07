package com.raunheim.los.service.impl;

import com.raunheim.los.dto.request.Los;
import com.raunheim.los.exception.LosException;
import com.raunheim.los.service.FileProcessingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class XmlProcessingService implements FileProcessingService {

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Los> T read(MultipartFile file, Class<T> clazz) {
        try {
            var content = new String(file.getBytes(), StandardCharsets.UTF_8);
            var reader = new StringReader(content);

            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            var obj = (Los) unmarshaller.unmarshal(reader);
            obj.setContent(content);
            return (T) obj;
        } catch (Exception ex) {
            throw new LosException("Cannot read xml file.", ex);
        }
    }

}
