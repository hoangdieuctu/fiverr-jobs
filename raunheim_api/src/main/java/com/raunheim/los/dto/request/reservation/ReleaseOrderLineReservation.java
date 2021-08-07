package com.raunheim.los.dto.request.reservation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ReleaseOrderLineReservation {
    @NotBlank
    private String TPNRKS;

    @NotBlank
    private String TPIDEN;

    @NotNull
    private Integer TPAPPO;

    @NotNull
    private Double TPBMEN;

    @NotBlank
    private String TPME;

    @NotBlank
    private String TPVORT;

    @NotBlank
    private String TPVBER;

    @NotBlank
    private String TPVREG;

    @NotBlank
    private String TPVHOR;

    @NotBlank
    private String TPVVER;

    public String getLocationId() {
        return String.join("-", TPVBER, TPVREG, TPVHOR, TPVVER);
    }
}
