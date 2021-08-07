package com.raunheim.los.model;

import com.raunheim.los.dto.request.bin.Bin;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location_id")
    private String locationId;

    @Column(name = "client_id")
    private Long client;

    private String status;

    @Column(name = "preferred_place")
    private String preferredPlace;

    @Column(name = "preferred_abc")
    private String preferredAbc;

    @Column(name = "preferred_zone")
    private String preferredZone;

    public static Location from(Bin bin) {
        var location = new Location();
        location.setStatus(bin.getCLSTAT());
        location.setPreferredPlace(bin.getLPORT());
        location.setLocationId(bin.getLocationId());
        location.setPreferredAbc(bin.getLPABC());
        location.setPreferredZone(bin.getLPLZON());

        return location;
    }

    public void copy(Bin bin) {
        if (bin.getCLSTAT() != null) {
            this.status = bin.getCLSTAT();
        }
        if (bin.getLPORT() != null) {
            this.preferredPlace = bin.getLPORT();
        }
        if (bin.getLPABC() != null) {
            this.preferredAbc = bin.getLPABC();
        }
        if (bin.getLPLZON() != null) {
            this.preferredZone = bin.getLPLZON();
        }
    }
}
