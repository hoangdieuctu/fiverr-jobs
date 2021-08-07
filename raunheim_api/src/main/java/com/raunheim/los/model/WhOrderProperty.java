package com.raunheim.los.model;

import com.raunheim.los.dto.request.order.Order;
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
@Table(name = "wh_orders_properties")
public class WhOrderProperty {

    @Id
    private Long id;

    @Column(name = "project_number")
    private String projectNumber;


    public static WhOrderProperty from(Order order) {
        var obj = new WhOrderProperty();
        obj.setProjectNumber(order.getAKPRN());
        return obj;
    }

    public void copy(Order order) {
        if (order.getAKPRN() != null) {
            this.projectNumber = order.getAKPRN();
        }
    }
}
