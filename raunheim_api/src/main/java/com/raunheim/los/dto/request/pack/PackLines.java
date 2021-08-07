package com.raunheim.los.dto.request.pack;

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
public class PackLines {

    @Valid
    @NotEmpty
    @XmlElement(name = "PackageLine")
    private List<PackLine> PackageLine;
}
