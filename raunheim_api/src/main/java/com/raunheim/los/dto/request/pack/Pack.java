package com.raunheim.los.dto.request.pack;

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
@XmlRootElement(name = "Package")
@XmlAccessorType(XmlAccessType.FIELD)
public class Pack extends Los {
    @NotBlank
    private String PKKONZ;

    @NotBlank
    private String PKFIRM;

    @NotBlank
    private String PKAPN;

    @NotBlank
    private String PKANR1;

    @NotBlank
    private String PKPALN;

    @NotBlank
    private String PKPKNR;

    @NotBlank
    private String PKPPLA;

    @Valid
    @NotNull
    @XmlElement(name = "PackageLines")
    private PackLines PackageLines;
}
