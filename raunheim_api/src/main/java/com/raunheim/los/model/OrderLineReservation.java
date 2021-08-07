package com.raunheim.los.model;

import com.raunheim.los.config.OrderStatusConverter;
import com.raunheim.los.dto.request.reservation.ReleaseOrderLineReservation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "orderline_reservations")
public class OrderLineReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String place;

    @Column(name = "pick_location")
    private Long pickLocation;

    private Double quantity;

    private Long unit;

    @Column(name = "orderline_id")
    private Long orderLineId;

    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus status;

    @Column(name = "transport_order_id")
    private String transportOrderId;

    @Column(name = "pick_list")
    private String pickList;

    @Column(name = "pick_type")
    private String pickType;

    public static OrderLineReservation from(com.raunheim.los.dto.request.reservation.OrderLineReservation line) {
        var obj = new OrderLineReservation();
        obj.setPlace(line.getREORT());
        obj.setQuantity(line.getRERESM());
        return obj;
    }

    public static OrderLineReservation from(ReleaseOrderLineReservation reservation) {
        var obj = new OrderLineReservation();
        obj.setPickList(reservation.getTPNRKS());
        obj.setPlace(reservation.getTPVORT());
        obj.setQuantity(reservation.getTPBMEN());
        return obj;
    }

    public void copy(com.raunheim.los.dto.request.reservation.OrderLineReservation line) {
        if (line.getREORT() != null) {
            this.place = line.getREORT();
        }
        if (line.getRERESM() != null) {
            this.quantity = line.getRERESM();
        }
    }
}
