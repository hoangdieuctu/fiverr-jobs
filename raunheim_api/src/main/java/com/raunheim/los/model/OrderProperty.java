package com.raunheim.los.model;

import com.raunheim.los.config.OrderStatusConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "orders_properties")
public class OrderProperty {

    @Id
    private Long id;

    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus status;

    @Column(name = "pack_station")
    private String packStation;
}
