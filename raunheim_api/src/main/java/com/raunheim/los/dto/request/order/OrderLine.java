package com.raunheim.los.dto.request.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class OrderLine {
    @NotBlank
    private String APIDEN;

    @NotNull
    private Integer APAPPO;

    @NotNull
    private Double APBSTM;

    @NotBlank
    private String APME;
    private String APPRNR;
    private Boolean APSRN;
    private String APWAKZ;

    @NotNull
    private Double APANFM;
    private String APIAKZ;
}
