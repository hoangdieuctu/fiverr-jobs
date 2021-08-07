package com.raunheim.los.dto.request.reservation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class OrderLineReservation {
    @NotNull
    private Double RERESM;

    @NotNull
    private Integer REAPPO;

    @NotBlank
    private String REORT;

    @NotBlank
    private String REBER;

    @NotBlank
    private String REREG;

    @NotBlank
    private String REHOR;

    @NotBlank
    private String REVER;

    public String getLocationId() {
        return String.join("-", REBER, REREG, REHOR, REVER);
    }
}
