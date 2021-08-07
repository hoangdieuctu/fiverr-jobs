package com.raunheim.los.model;

import com.raunheim.los.config.OrderStatusConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "orderlines_properties")
public class OrderLineProperty {

    @Id
    private Long id;

    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus status;
}
