package com.raunheim.los.dto.request.article;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class Article {
    @NotBlank
    private String TZKONZ;

    @NotBlank
    private String TZFIRM;

    @NotBlank
    private String TZIDEN;
    private String TZBEZ1;
    private String TZABC;

    @NotBlank
    private String TZME;
    private Integer TZBRUT;
    private Integer TZVLL;
    private Integer TZVLB;
    private Integer TZVLH;

    @NotBlank
    private String TZME1;
    private Integer TZ1BRUT;
    private Integer TZ1VLL;
    private Integer TZ1VLB;
    private Integer TZ1VLH;

    @NotBlank
    private String TZME2;
    private Integer TZ2BRUT;
    private Integer TZ2VLL;
    private Integer TZ2VLB;
    private Integer TZ2VLH;
    private String TZMLM;
    private Integer TZVPE;
    private Integer TZVPE2;
    private String TZORT;
    private String TZBER;
    private String TZLZON;
    private Boolean TZELZW;

    public String getUnitId(int unit) {
        if (unit == 0) {
            return TZME;
        } else if (unit == 1) {
            return TZME1;
        } else {
            return TZME2;
        }
    }

    public String getClient() {
        return String.join("-", TZKONZ, TZFIRM);
    }
}
