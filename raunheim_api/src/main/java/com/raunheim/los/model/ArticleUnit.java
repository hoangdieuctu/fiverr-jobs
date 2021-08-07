package com.raunheim.los.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "article_units")
public class ArticleUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article_id")
    private Long articleId;
    private String unit;
    private Integer weight;
    private Integer length;
    private Integer width;
    private Integer height;

    public static ArticleUnit from(com.raunheim.los.dto.request.article.Article article, int unit) {
        var obj = new ArticleUnit();
        if (unit == 0) {
            obj.setUnit(article.getTZME());
            obj.setWeight(article.getTZBRUT());
            obj.setLength(article.getTZVLL());
            obj.setWidth(article.getTZVLB());
            obj.setHeight(article.getTZVLH());
        } else if (unit == 1) {
            obj.setUnit(article.getTZME1());
            obj.setWeight(article.getTZ1BRUT());
            obj.setLength(article.getTZ1VLL());
            obj.setWidth(article.getTZ1VLB());
            obj.setHeight(article.getTZ1VLH());
        } else if (unit == 2) {
            obj.setUnit(article.getTZME2());
            obj.setWeight(article.getTZ2BRUT());
            obj.setLength(article.getTZ2VLL());
            obj.setWidth(article.getTZ2VLB());
            obj.setHeight(article.getTZ2VLH());
        }
        return obj;
    }

    public void copy(com.raunheim.los.dto.request.article.Article article, int unit) {
        if (unit == 0) {
            if (article.getTZME() != null) {
                this.unit = article.getTZME();
            }
            if (article.getTZBRUT() != null) {
                this.weight = article.getTZBRUT();
            }
            if (article.getTZVLL() != null) {
                this.length = article.getTZVLL();
            }
            if (article.getTZVLB() != null) {
                this.width = article.getTZVLB();
            }
            if (article.getTZVLH() != null) {
                this.height = article.getTZVLH();
            }
        } else if (unit == 1) {
            if (article.getTZME1() != null) {
                this.unit = article.getTZME1();
            }
            if (article.getTZ1BRUT() != null) {
                this.weight = article.getTZ1BRUT();
            }
            if (article.getTZ1VLL() != null) {
                this.length = article.getTZ1VLL();
            }
            if (article.getTZ1VLB() != null) {
                this.width = article.getTZ1VLB();
            }
            if (article.getTZ1VLH() != null) {
                this.height = article.getTZ1VLH();
            }
        } else if (unit == 2) {
            if (article.getTZME2() != null) {
                this.unit = article.getTZME2();
            }
            if (article.getTZ2BRUT() != null) {
                this.weight = article.getTZ2BRUT();
            }
            if (article.getTZ2VLL() != null) {
                this.length = article.getTZ2VLL();
            }
            if (article.getTZ2VLB() != null) {
                this.width = article.getTZ2VLB();
            }
            if (article.getTZ2VLH() != null) {
                this.height = article.getTZ2VLH();
            }
        }
    }
}
