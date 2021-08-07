package com.raunheim.los.repository;

import com.raunheim.los.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Inventory findByStorageLocation(Long storageLocation);

}
