package com.raunheim.los.dto.request.pack;

import com.raunheim.los.util.DateTimeUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
public class PackLine {

    @NotNull
    private Integer PKAPPO;

    @NotBlank
    private String PKIDEN;

    @NotNull
    private Double PKPMEN;

    @NotBlank
    private String PKME;

    @NotBlank
    private String PKDTPS;

    @NotBlank
    private String PKTIPS;

    @NotBlank
    private String PKDTPE;

    @NotBlank
    private String PKTIPE;

    @NotBlank
    private String PKLFDN;

    @NotBlank
    private String PKUSER;

    public Date getTimeStart() {
        if (StringUtils.isBlank(PKDTPS) && StringUtils.isBlank(PKTIPS)) {
            return null;
        }
        var value = String.join("-", PKDTPS, PKTIPS);
        return DateTimeUtils.convertSimpleDateTime(value);
    }

    public Date getTimeEnd() {
        if (StringUtils.isBlank(PKDTPE) && StringUtils.isBlank(PKTIPE)) {
            return null;
        }
        var value = String.join("-", PKDTPE, PKTIPE);
        return DateTimeUtils.convertSimpleDateTime(value);
    }
}