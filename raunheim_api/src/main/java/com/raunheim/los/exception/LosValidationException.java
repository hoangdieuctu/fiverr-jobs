package com.raunheim.los.exception;

import lombok.Getter;

import java.util.Set;

@Getter
public class LosValidationException extends LosException {
    static final long serialVersionUID = 1L;

    private final Set<String> validatorMessages;

    public LosValidationException(Set<String> validatorMessages) {
        super("Validation error");
        this.validatorMessages = validatorMessages;
    }
}
