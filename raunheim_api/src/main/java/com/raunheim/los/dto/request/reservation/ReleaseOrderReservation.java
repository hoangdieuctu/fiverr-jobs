package com.raunheim.los.dto.request.reservation;

import com.raunheim.los.dto.request.Los;
import com.raunheim.los.util.DateTimeUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Getter
@Setter
@ToString
@XmlRootElement(name = "Order")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReleaseOrderReservation extends Los {

    @NotBlank
    private String TPPALN;

    @NotBlank
    private String TPTTYP;

    @NotBlank
    private String TPAPN;

    @NotBlank
    private String AKDTB;

    @NotBlank
    private String AKTIB;

    @NotNull
    private Integer TPMDE3;

    @Valid
    @NotNull
    @XmlElement(name = "OrderlineReservations")
    private ReleaseOrderLinesReservation OrderlineReservations;

    public Date getShippingDeadline() {
        if (StringUtils.isBlank(AKDTB) && StringUtils.isBlank(AKTIB)) {
            return null;
        }
        var value = String.join("-", AKDTB, AKTIB);
        return DateTimeUtils.convertSimpleDateTime(value);
    }
}
