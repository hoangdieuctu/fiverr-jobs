package com.raunheim.los.dto.request.bin;

import com.raunheim.los.dto.request.Los;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@ToString
@XmlRootElement(name = "Bins")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bins extends Los {

    @Valid
    @NotEmpty
    private List<Bin> Bin;
}
