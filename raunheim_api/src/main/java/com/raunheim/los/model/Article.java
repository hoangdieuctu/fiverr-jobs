package com.raunheim.los.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.BooleanUtils;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article_id")
    private String articleId;
    private String description;

    @Column(name = "client_id")
    private Long client;

    @Column(name = "preferred_abc")
    private String preferredAbc;

    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "loading_equipment")
    private String loadingEquipment;

    @Column(name = "quantity_in_unit1")
    private Integer quantityInUnit1;

    @Column(name = "quantity_in_unit2")
    private Integer quantityInUnit2;

    @Column(name = "preferred_place")
    private String preferredPlace;

    @Column(name = "preferred_area")
    private String preferredArea;

    @Column(name = "preferred_zone")
    private String preferredZone;

    @Column(name = "forced_storaging")
    private Boolean forcedStoraging;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "last_change_date")
    private Date lastChangeDate;

    public static Article from(com.raunheim.los.dto.request.article.Article article) {
        var obj = new Article();
        obj.setArticleId(article.getTZIDEN());
        obj.setDescription(article.getTZBEZ1());
        obj.setPreferredAbc(article.getTZABC());
        obj.setLoadingEquipment(article.getTZMLM());
        obj.setQuantityInUnit1(article.getTZVPE());
        obj.setQuantityInUnit2(article.getTZVPE2());
        obj.setPreferredPlace(article.getTZORT());
        obj.setPreferredArea(article.getTZBER());
        obj.setPreferredZone(article.getTZLZON());
        obj.setForcedStoraging(BooleanUtils.isTrue(article.getTZELZW()));
        obj.setCreationDate(new Date());
        obj.setLastChangeDate(new Date());

        return obj;
    }

    public void copy(com.raunheim.los.dto.request.article.Article article) {
        if (article.getTZIDEN() != null) {
            this.articleId = article.getTZIDEN();
        }
        if (article.getTZBEZ1() != null) {
            this.description = article.getTZBEZ1();
        }
        if (article.getTZABC() != null) {
            this.preferredAbc = article.getTZABC();
        }
        if (article.getTZMLM() != null) {
            this.loadingEquipment = article.getTZMLM();
        }
        if (article.getTZVPE() != null) {
            this.quantityInUnit1 = article.getTZVPE();
        }
        if (article.getTZVPE2() != null) {
            this.quantityInUnit2 = article.getTZVPE2();
        }
        if (article.getTZORT() != null) {
            this.preferredPlace = article.getTZORT();
        }
        if (article.getTZLZON() != null) {
            this.preferredZone = article.getTZLZON();
        }
        if (article.getTZBER() != null) {
            this.preferredArea = article.getTZBER();
        }
        if (article.getTZELZW() != null) {
            this.forcedStoraging = BooleanUtils.isTrue(article.getTZELZW());
        }
        this.lastChangeDate = new Date();
    }
}
