package com.raunheim.los.dto.request.reservation;

import com.raunheim.los.dto.request.Los;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@ToString
@XmlRootElement(name = "Order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderReservation extends Los {

    @NotBlank
    private String REAPN;

    @NotNull
    private Integer REMDE3;

    @Valid
    @NotNull
    @XmlElement(name = "Orderlines")
    private OrderLinesReservation Orderlines;

}
