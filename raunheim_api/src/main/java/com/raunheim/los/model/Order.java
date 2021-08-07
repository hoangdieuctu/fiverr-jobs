package com.raunheim.los.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id")
    private Long client;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "client_order_id")
    private String clientOrderId;

    @Column(name = "known_date")
    private Date knownDate;

    @Column(name = "shipping_type")
    private Integer shippingType;

    private Integer type;

    @Column(name = "shipping_deadline")
    private Date shippingDeadline;

    private Integer priority;

    public static Order from(com.raunheim.los.dto.request.order.Order order) {
        var obj = new Order();
        obj.setOrderId(order.getAKAPN());
        obj.setClientOrderId(order.getAKANR1());
        obj.setKnownDate(order.getKnownDate());
        obj.setShippingType(order.getAKVART());
        obj.setShippingDeadline(order.getShippingDeadline());
        obj.setPriority(order.getAKMDE3());
        return obj;
    }

    public void copy(com.raunheim.los.dto.request.order.Order order) {
        if (order.getAKAPN() != null) {
            this.orderId = order.getAKAPN();
        }
        if (order.getAKANR1() != null) {
            this.clientOrderId = order.getAKANR1();
        }
        if (order.getAKMDE3() != null) {
            this.priority = order.getAKMDE3();
        }
        if (order.getAKVART() != null) {
            this.shippingType = order.getAKVART();
        }
        this.knownDate = order.getKnownDate();
        this.shippingDeadline = order.getShippingDeadline();
    }
}
