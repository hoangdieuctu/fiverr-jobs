package com.raunheim.los.dto.request.reservation;

import com.raunheim.los.dto.request.Los;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@ToString
@XmlRootElement(name = "Order")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeleteOrderReservation extends Los {

    @NotBlank
    private String ASKONZ;

    @NotBlank
    private String ASFIRM;

    @NotBlank
    private String ASAPN;

    @NotBlank
    private String ASANR1;

    @NotBlank
    private String ASAPS3;
}
