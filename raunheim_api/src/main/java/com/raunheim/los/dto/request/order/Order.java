package com.raunheim.los.dto.request.order;

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
public class Order extends Los {
    @NotBlank
    private String AKKONZ;

    @NotBlank
    private String AKFIRM;

    @NotBlank
    private String AKAPN;

    @NotBlank
    private String AKANR1;

    @NotBlank
    private String AKDTAE;

    @NotBlank
    private String AKTIAE;

    private String AKTOUR;

    @NotNull
    private Integer AKVART;
    private String AKAART;

    @NotBlank
    private String AKDTB;

    @NotBlank
    private String AKTIB;

    @NotNull
    private Integer AKMDE3;

    private String AKPRN;

    @Valid
    @NotNull
    @XmlElement(name = "Orderlines")
    private OrderLines Orderlines;

    public Date getKnownDate() {
        if (StringUtils.isBlank(AKDTAE) && StringUtils.isBlank(AKTIAE)) {
            return null;
        }
        var value = String.join("-", AKDTAE, AKTIAE);
        return DateTimeUtils.convertSimpleDateTime(value);
    }

    public Date getShippingDeadline() {
        if (StringUtils.isBlank(AKDTB) && StringUtils.isBlank(AKTIB)) {
            return null;
        }
        var value = String.join("-", AKDTB, AKTIB);
        return DateTimeUtils.convertSimpleDateTime(value);
    }

    public String getClient() {
        return String.join("-", AKKONZ, AKFIRM);
    }
}
