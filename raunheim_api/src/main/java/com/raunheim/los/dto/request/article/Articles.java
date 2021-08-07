package com.raunheim.los.dto.request.article;

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
@XmlRootElement(name = "Articles")
@XmlAccessorType(XmlAccessType.FIELD)
public class Articles extends Los {

    @Valid
    @NotEmpty
    private List<Article> Article;
}
