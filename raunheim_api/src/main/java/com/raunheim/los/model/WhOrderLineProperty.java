package com.raunheim.los.model;

import com.raunheim.los.dto.request.order.OrderLine;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "wh_orderlines_properties")
public class WhOrderLineProperty {

    @Id
    private Long id;

    @Column(name = "article_property")
    private String articleProperty;

    @Column(name = "quantity_requested")
    private Double quantityRequested;

    @Column(name = "article_inactivity")
    private String articleInactivity;


    public static WhOrderLineProperty from(OrderLine orderLine) {
        var obj = new WhOrderLineProperty();
        obj.setArticleProperty(orderLine.getAPWAKZ());
        obj.setQuantityRequested(orderLine.getAPANFM());
        obj.setArticleInactivity(orderLine.getAPIAKZ());
        return obj;
    }

    public void copy(OrderLine orderLine) {
        if (orderLine.getAPWAKZ() != null) {
            this.articleProperty = orderLine.getAPWAKZ();
        }
        if (orderLine.getAPANFM() != null) {
            this.quantityRequested = orderLine.getAPANFM();
        }
        if (orderLine.getAPIAKZ() != null) {
            this.articleInactivity = orderLine.getAPIAKZ();
        }
    }
}
