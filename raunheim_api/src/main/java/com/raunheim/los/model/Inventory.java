package com.raunheim.los.model;

import com.raunheim.los.dto.request.stock.Stock;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long article;
    private Double quantity;
    private Long unit;
    private String place;

    @Column(name = "client_id")
    private Long client;

    @Column(name = "storage_location")
    private Long storageLocation;

    @Column(name = "loading_equipment_id")
    private String loadingEquipmentId;

    @Column(name = "inventory_date")
    private Date inventoryDate;

    public static Inventory from(Stock stock) {
        var inventory = new Inventory();
        inventory.setQuantity(stock.getBMBMEN());
        inventory.setPlace(stock.getBMORT());
        inventory.setLoadingEquipmentId(stock.getBMTLM());
        inventory.setInventoryDate(new Date());

        return inventory;
    }

    public void copy(Stock stock) {
        if (stock.getBMBMEN() != null) {
            this.quantity = stock.getBMBMEN();
        }
        if (stock.getBMORT() != null) {
            this.place = stock.getBMORT();
        }
        if (stock.getBMTLM() != null) {
            this.loadingEquipmentId = stock.getBMTLM();
        }
    }
}
