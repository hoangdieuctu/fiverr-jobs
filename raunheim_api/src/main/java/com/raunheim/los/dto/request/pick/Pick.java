package com.raunheim.los.dto.request.pick;

import com.raunheim.los.dto.request.Los;
import com.raunheim.los.util.DateTimeUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Getter
@Setter
@ToString
@XmlRootElement(name = "Pick")
@XmlAccessorType(XmlAccessType.FIELD)
public class Pick extends Los {

    @NotBlank
    private String TPKONZ;

    @NotBlank
    private String TPFIRM;

    @NotBlank
    private String TPPALN;

    @NotBlank
    private String TPAPN;

    @NotNull
    private Integer TPAPPO;

    @NotBlank
    private String TPANR1;
    private String TPNRKS;
    private String TPBENR;

    @NotBlank
    private String TPIDEN;

    @NotNull
    private Double TPKMEN;

    @NotBlank
    private String TPPPLZ;

    @NotBlank
    private String TPVORT;

    @NotBlank
    private String TPVBER;

    @NotBlank
    private String TPREG;

    @NotBlank
    private String TPHOR;

    @NotBlank
    private String TPVER;

    @NotBlank
    private String TPME;

    @NotBlank
    private String TPDTKS;

    @NotBlank
    private String TPTIKS;

    @NotBlank
    private String TPDTKE;

    @NotBlank
    private String TPTIKE;

    @NotBlank
    private String TPUSER;

    public String getLocationStartId() {
        return String.join("-", TPVBER, TPREG, TPHOR, TPVER);
    }

    public Date getTimeStart() {
        if (StringUtils.isBlank(TPDTKS) && StringUtils.isBlank(TPTIKS)) {
            return null;
        }
        var value = String.join("-", TPDTKS, TPTIKS);
        return DateTimeUtils.convertSimpleDateTime(value);
    }

    public Date getTimeEnd() {
        if (StringUtils.isBlank(TPDTKE) && StringUtils.isBlank(TPTIKE)) {
            return null;
        }
        var value = String.join("-", TPDTKE, TPTIKE);
        return DateTimeUtils.convertSimpleDateTime(value);
    }
}
