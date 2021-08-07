package com.raunheim.los.dto.request.stock;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class Stock {
    @NotBlank
    private String BMKONZ;

    @NotBlank
    private String BMFIRM;

    @NotBlank
    private String BMIDEN;

    @NotNull
    private Double BMBMEN;

    @NotBlank
    private String BMME;
    private String BMORT;

    @NotBlank
    private String BMBER;

    @NotBlank
    private String BMREG;

    @NotBlank
    private String BMHOR;

    @NotBlank
    private String BMVER;

    private Long BMSEGM;
    private String BMTLM;

    public String getStorageLocation() {
        return String.join("-", BMBER, BMREG, BMHOR, BMVER);
    }

    public String getClient() {
        return String.join("-", BMKONZ, BMFIRM);
    }
}
