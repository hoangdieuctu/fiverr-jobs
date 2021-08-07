package com.raunheim.los.dto.request.reservation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Getter
@Setter
@ToString
public class ReleaseOrderLinesReservation {

    @Valid
    @NotEmpty
    @XmlElement(name = "OrderlineReservation")
    private List<ReleaseOrderLineReservation> OrderlineReservation;
}
