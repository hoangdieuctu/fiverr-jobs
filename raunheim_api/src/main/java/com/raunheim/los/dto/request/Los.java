package com.raunheim.los.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public abstract class Los {
    @NotNull
    private Long ID;
    private String content;
}
