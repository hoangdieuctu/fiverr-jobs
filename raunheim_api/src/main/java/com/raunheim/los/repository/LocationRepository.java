package com.raunheim.los.repository;

import com.raunheim.los.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByLocationId(String locationId);

}
