package com.raunheim.los.service;

import com.raunheim.los.dto.request.Los;
import com.raunheim.los.exception.LosValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public abstract class LosService<T extends Los> {

    private static final String FILE_EXTENSION = "xml";
    private static final String ERROR_SUFFIX_FILE_NAME = "error";

    private final Class<T> clazz;
    private final Validator validator;
    private final FileProcessingService fileProcessingService;
    private final FileStorageService fileStorageService;

    public abstract String getStorageLocation();

    private String getFilePath(Los los) {
        var fileName = String.join(".", String.valueOf(los.getID()), FILE_EXTENSION);
        return String.join("/", getStorageLocation(), fileName);
    }

    private String getErrorFilePath(Los los) {
        var fileName = String.format("%s_%s.%s", los.getID(), ERROR_SUFFIX_FILE_NAME, FILE_EXTENSION);
        return String.join("/", getStorageLocation(), fileName);
    }

    protected T storeToFile(MultipartFile file) {
        var los = fileProcessingService.read(file, clazz);
        validateRequireFields(los);

        fileStorageService.store(getFilePath(los), los.getContent());
        return los;
    }

    private void validateRequireFields(T los) {
        var result = validator.validate(los);
        if (!CollectionUtils.isEmpty(result)) {
            var errorMessages = result.stream().map(res -> {
                var message = res.getMessage();
                var path = res.getPropertyPath().toString();
                return String.format("%s %s", path, message);
            }).collect(Collectors.toSet());

            storeErrorFile(los, errorMessages);
            throw new LosValidationException(errorMessages);
        }
    }

    private void storeErrorFile(T los, Set<String> errors) {
        log.info("Store error file");

        var xmlErrors = buildXmlErrors(errors);
        var builder = new StringBuilder();
        builder.append(los.getContent()).append("\n");
        builder.append(xmlErrors);

        fileStorageService.store(getErrorFilePath(los), builder.toString());
    }

    protected String buildXmlErrors(Set<String> errors) {
        var builder = new StringBuilder();
        builder.append("<Errors>").append("\n");
        errors.forEach(e -> builder.append("\t").append(e).append("\n"));
        builder.append("</Errors>").append("\n");
        return builder.toString();
    }
}
