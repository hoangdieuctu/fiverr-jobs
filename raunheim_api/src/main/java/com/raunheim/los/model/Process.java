package com.raunheim.los.model;

import com.raunheim.los.dto.request.pack.Pack;
import com.raunheim.los.dto.request.pack.PackLine;
import com.raunheim.los.dto.request.pick.Pick;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "processes")
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loading_equipment_id_end")
    private String loadingEquipmentIdEnd;

    private Long article;
    private Double quantity;
    private Long unit;

    @Column(name = "location_end")
    private Long locationEnd;

    @Column(name = "location_start")
    private Long locationStart;

    @Column(name = "time_start")
    private Date timeStart;

    @Column(name = "time_end")
    private Date timeEnd;

    private Integer employee;

    @Column(name = "process_type")
    private Integer processType;

    @Column(name = "process_id")
    private String processId;

    public static Process from(Pick pick) {
        var obj = new Process();
        obj.setLoadingEquipmentIdEnd(pick.getTPBENR());
        obj.setQuantity(pick.getTPKMEN());
        obj.setTimeStart(pick.getTimeStart());
        obj.setTimeEnd(pick.getTimeEnd());
        return obj;
    }

    public static Process from(Pack pack, PackLine line) {
        var obj = new Process();
        obj.setLoadingEquipmentIdEnd(pack.getPKPKNR());
        obj.setQuantity(line.getPKPMEN());
        obj.setTimeStart(line.getTimeStart());
        obj.setTimeEnd(line.getTimeEnd());
        return obj;
    }
}
