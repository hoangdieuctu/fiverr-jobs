package com.raunheim.los.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private boolean success;
    private String message;
    private Object body;

    public static ResponseDto success() {
        return new ResponseDto(true, "Ok", null);
    }

    public static ResponseDto error(String message) {
        return new ResponseDto(false, message, null);
    }

    public static ResponseDto validateError(String message, Set<String> errors) {
        return new ResponseDto(false, message, errors);
    }
}
