package com.raunheim.los.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.BooleanUtils;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "orderlines")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "article_id")
    private Long articleId;

    private Integer line;

    private Double quantity;

    @Column(name = "unit_id")
    private Long unit;

    @Column(name = "charge_id")
    private String chargeId;

    @Column(name = "serial_id")
    private boolean serialId;


    public static OrderLine from(com.raunheim.los.dto.request.order.OrderLine line) {
        var obj = new OrderLine();
        obj.setLine(line.getAPAPPO());
        obj.setQuantity(line.getAPBSTM());
        obj.setChargeId(line.getAPPRNR());
        obj.setSerialId(BooleanUtils.isTrue(line.getAPSRN()));
        return obj;
    }

    public void copy(com.raunheim.los.dto.request.order.OrderLine line) {
        if (line.getAPAPPO() != null) {
            this.line = line.getAPAPPO();
        }
        if (line.getAPBSTM() != null) {
            this.quantity = line.getAPBSTM();
        }
        if (line.getAPPRNR() != null) {
            this.chargeId = line.getAPPRNR();
        }
        if (line.getAPSRN() != null) {
            this.serialId = BooleanUtils.isTrue(line.getAPSRN());
        }
    }
}
