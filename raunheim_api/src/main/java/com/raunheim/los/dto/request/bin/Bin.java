package com.raunheim.los.dto.request.bin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class Bin {
    private String CLSTAT;

    @NotBlank
    private String LPKONZ;

    @NotBlank
    private String LPFIRM;

    @NotBlank
    private String LPORT;

    @NotBlank
    private String LPBER;

    @NotBlank
    private String LPREG;

    @NotBlank
    private String LPHOR;

    @NotBlank
    private String LPVER;

    @NotBlank
    private String LPABC;

    @NotBlank
    private String LPLZON;

    public String getLocationId() {
        return String.join("-", LPBER, LPREG, LPHOR, LPVER);
    }

    public String getClient() {
        return String.join("-", LPKONZ, LPFIRM);
    }
}
